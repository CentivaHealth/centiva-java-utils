package org.hit.utils;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;

public abstract class Signature {
    private String privateKey;
    private String data;


    public Signature(String privateKey) {
        this.privateKey = privateKey;
    }

    public abstract String getSignature() throws IOException;

    public void setData(String data) {
        JSONObject jsonObject = new JSONObject(data);
        String dataSign = jsonObject.toString();
        this.data = dataSign;
    }

    public static class HisSignature extends Signature {
        private static final String PROJECT_FILE_PATH = System.getProperty("user.dir");
        private static final String SDK_FILE_PATH = "/src/main/java/org/hit/utils/nem/nem-sdk.js";

        private long timestamp;

        public HisSignature(String privateKey) {
            super(privateKey);
            Date today  = new Date();
            this.timestamp = today.getTime();
            String timestampStr = String.valueOf(this.timestamp);
            String data = "{\"timestamp\":\"" + timestampStr + "\"}";
            super.setData(data);
        }

        public String getSignature() throws IOException {
            System.out.println("SDK_FILE_PATH: " + PROJECT_FILE_PATH + SDK_FILE_PATH);
            ProcessBuilder pb = new ProcessBuilder(
                    "node",
                    PROJECT_FILE_PATH + SDK_FILE_PATH,
                    super.privateKey,
                    super.data
            );
            Process p = pb.start();
            StreamGobbler input = new StreamGobbler(p.getInputStream(), "OUTPUT");
            String signature= input.getResult();
            System.out.println("Signature is: " + signature);
            return signature;
        }

        public long getTimestamp() {
            return timestamp;
        }
    }
}

