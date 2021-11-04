package com.mathieutulpinck.crypto;

import java.util.Base64;

public class Challenge {
    private String kid;
    private byte[] key;
    private byte[] cipherText;
    private byte[] nonce;

    public Challenge(ApiResponse apiResponse) {
        this.kid = apiResponse.getKid();
        this.key = Base64.getDecoder().decode(apiResponse.getKey());
        this.cipherText = Base64.getDecoder().decode(apiResponse.getChallenge());
        this.nonce = Base64.getDecoder().decode(apiResponse.getNonce());
    }

    public String getKid() {
        return kid;
    }

    public byte[] getKey() {
        return key;
    }

    public byte[] getCipherText() {
        return cipherText;
    }

    public byte[] getNonce() {
        return nonce;
    }
}
