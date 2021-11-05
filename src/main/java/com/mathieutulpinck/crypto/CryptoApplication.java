package com.mathieutulpinck.crypto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.muquit.libsodiumjna.SodiumLibrary;
import com.muquit.libsodiumjna.exceptions.SodiumLibraryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import java.util.Base64;

@SpringBootApplication
public class CryptoApplication {

    private static final Logger log = LoggerFactory.getLogger(CryptoApplication.class);
    private static final String os = System.getProperty("os.name");
    private static final String libraryPath = selectLibrary(os);
    public static String selectLibrary(String os) {
        if(os.contains("Windows")) {
            return "lib\\libsodium.dll";
        } else {
            return "lib/libsodium.23.dylib";
        }
    }

    private static final String baseUrl = "https://79vo67ipp9.execute-api.eu-west-1.amazonaws.com/Prod/decrypt/challenges/";


    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(CryptoApplication.class, args);
        ctx.close();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
        return args -> {
            log.info("loading libsodium library...");
            SodiumLibrary.setLibraryPath(libraryPath);
            String version = SodiumLibrary.libsodiumVersionString();
            log.info("libsodium version: " + version);

            ApiResponse apiResponse = restTemplate.postForObject(baseUrl, null, ApiResponse.class);
            log.info(apiResponse.toString());

            Challenge challenge = new Challenge(apiResponse);
            String plaintext = decrypt(challenge);
            log.info(plaintext);

            DecryptedChallenge decryptedChallenge = new DecryptedChallenge(plaintext);
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(decryptedChallenge);
            log.info(json);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity<>(json, headers);
            ResponseEntity<Void> response = restTemplate.exchange(baseUrl + apiResponse.getKid(), HttpMethod.DELETE, request, Void.class);
            if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
                log.info("The challenge was decrypted correctly.");
            } else {
                log.info("The plaintext is not correct.");
            }
        };
    }



    private String decrypt(Challenge challenge) {
        byte[] decryptedBytes = new byte[0];
        try {
            decryptedBytes = SodiumLibrary.cryptoSecretBoxOpenEasy(challenge.getCipherText(), challenge.getNonce(), challenge.getKey());
        } catch (SodiumLibraryException e) {
            e.printStackTrace();
        }

        return Base64.getEncoder().encodeToString(decryptedBytes);
    }


}
