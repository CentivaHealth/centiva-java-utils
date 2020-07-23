package org.hit.utils;

import java.io.File;
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
        this.data = data;
    }

    public static class HisSignature extends Signature {
        private static final String PROJECT_FILE_PATH = new File("").getAbsolutePath();

        private long timestamp;
        private File nemSdk;

        public HisSignature(String privateKey) {
            super(privateKey);
            System.out.println("PROJECT_FILE_PATH: " + PROJECT_FILE_PATH);
            Date today  = new Date();
            this.timestamp = today.getTime();
            String timestampStr = String.valueOf(this.timestamp);
            String data = "{\"timestamp\":\"" + timestampStr + "\"}";
            super.setData(data);

            this.nemSdk = this.searchFile(new File(PROJECT_FILE_PATH), "nem-sdk.js");
        }

        private File searchFile(File file, String search) {
            if (file.isDirectory()) {
                File[] arr = file.listFiles();
                for (File f : arr) {
                    File found = searchFile(f, search);
                    if (found != null)
                        return found;
                }
            } else {
                if (file.getName().equals(search)) {
                    return file;
                }
            }
            return null;
        }

        public String getSignature() throws IOException {
            ProcessBuilder pb = new ProcessBuilder(
                    "node",
                    this.nemSdk.getAbsolutePath(),
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

