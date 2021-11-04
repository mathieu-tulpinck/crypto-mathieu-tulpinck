package com.mathieutulpinck.crypto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResponse {
    private String kid;
    private String key;
    private String challenge;
    private String nonce;

    public ApiResponse() {
    }

    public String getKid() {
        return kid;
    }

    public String getKey() {
        return key;
    }

    public String getChallenge() {
        return challenge;
    }

    public String getNonce() {
        return nonce;
    }

    @Override
    public String toString() {
        return "Challenge{" +
                "kid='" + kid + '\'' +
                ", key='" + key + '\'' +
                ", challenge='" + challenge + '\'' +
                ", nonce='" + nonce + '\'' +
                '}';
    }
}
