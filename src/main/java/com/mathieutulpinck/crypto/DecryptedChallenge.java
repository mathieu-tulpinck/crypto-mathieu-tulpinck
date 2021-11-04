package com.mathieutulpinck.crypto;

public class DecryptedChallenge {
    String plaintext;

    public DecryptedChallenge(String plaintext) {
        this.plaintext = plaintext;
    }

    public String getPlaintext() {
        return plaintext;
    }

    public void setPlaintext(String plaintext) {
        this.plaintext = plaintext;
    }
}
