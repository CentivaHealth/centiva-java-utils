package org.hit.utils;

import java.util.Date;

public class Main {
    public static void main(String[] args) throws Exception {
        final String PRIVATE_KEY = "b0915c62ab6c21b7bf2f440573d2a81c0406915bc52c4a05f52b124eaea84164";

        Signature.HisSignature signatureObj = new Signature.HisSignature(PRIVATE_KEY);
        String signature = signatureObj.getSignature();

        String uri = "https://us-central1-nlh-qr-registration.cloudfunctions.net/getRegistry"
                + "?Timestamp=" + signatureObj.getTimestamp()
                + "&Signature=" + signature;
        System.out.println(uri);
    }
}