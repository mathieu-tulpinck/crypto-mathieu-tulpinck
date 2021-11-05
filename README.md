# crypto-mathieu-tulpinck
crypto-mathieu-tulpinck created by GitHub Classroom

## Installation

This project was developed using JDK 8 (LTS). Requirements to run this project:

- The program is dependent on the libsodium C library, which - for the purposes of this project - has been compiled to dll and dylib formats. Both are made available as part of a [zip archive](https://github.com/EHB-TI/crypto-mathieu-tulpinck/blob/main/crypto-mathieu-tulpinck.zip) downloadable from this github repo. The built executable (jar file) is runnable on Windows and OSX systems.
- If you don't have a Java runtime environment on your system, install JRE 8 (e.g. from this [webpage](https://www.java.com/en/download/)).
- Download and unzip the compressed archive described above. Open a terminal window and change directory into the extracted folder called `crypto-mathieu-tulpinck`.
- Run the following command: `java -jar crypto-mathieu-tulpinck-0.0.1-SNAPSHOT.jar`.

## Usage and explanation

The program retrieves a challenge from the API, decrypts the challenge using the key and the nonce and sends the decrypted ciphertext (called plaintext in the log) back to the API in a delete request.
