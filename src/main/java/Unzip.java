import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

public class Unzip {
    private static final String INPUT_GZIP_FILE = "./src/main/resources/planes_log.csv.gz";
    private static final String OUTPUT_FILE = "./src/main/resources/planes_log.csv";




    public void unzip() {

        byte[] buffer = new byte[1024];
        try {
            GZIPInputStream inputStream =
                    new GZIPInputStream(new FileInputStream(INPUT_GZIP_FILE));

            FileOutputStream outputStream =
                    new FileOutputStream(OUTPUT_FILE);

            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            inputStream.close();
            outputStream.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}