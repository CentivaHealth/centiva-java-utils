import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StreamGobbler {
    InputStream is;
    String type;
    String result;

    public StreamGobbler(InputStream is, String type) {
        this.is = is;
        this.type = type;
    }

    public String getResult() {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                if (this.type.compareTo("ERROR") == 0) {
                    System.out.println(line);
                } else {
                    stringBuilder.append(line);
                }
            }
            this.result = stringBuilder.toString();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return this.result;
    }
}
