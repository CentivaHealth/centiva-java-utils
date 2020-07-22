import java.util.Date;

public class Main {
    public static void main(String[] args) throws Exception {

        String privateKey = "b0915c62ab6c21b7bf2f440573d2a81c0406915bc52c4a05f52b124eaea84164";

        Date today  = new Date();
        String timestamp = String.valueOf(today.getTime());
        String data = "{\"timestamp\":\"" + timestamp + "\"}";

        ProcessBuilder pb = new ProcessBuilder(
                "node",
                "/home/son-fds/Downloads/comfdssoft/src/main/java/nem-sdk.js",
                privateKey,
                data
        );
        Process p = pb.start();

        StreamGobbler input = new StreamGobbler(p.getInputStream(), "OUTPUT");

        String signature= input.getResult();
        System.out.println("Signature is: " + signature);

        String uri = "https://us-central1-nlh-qr-registration.cloudfunctions.net/getRegistry?Timestamp=" + timestamp
                +"&Signature=" + signature;
        System.out.println(uri);
    }
}