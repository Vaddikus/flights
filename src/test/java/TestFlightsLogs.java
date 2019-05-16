import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class TestFlightsLogs {

    private FlightStatistics flightStatistics = new FlightStatistics();
    private final String INPUT_GZIP_FILE = "./src/main/resources/planes_log.csv.gz";
    private final String CORRUPTED_FILE = "./src/main/resources/corrupted_planes_log.csv";

    @Test
    void testCsvFileIsUnzipped() {
        File f = new File(FlightStatistics.getInputFile());
        assertTrue(f.exists(), "Input csv file with logs is not found.");
    }

    @Test
    void testZipFileisPresent() {
        File f = new File(Unzip.getInputGzipFile());
        assertTrue(f.exists(), "Input gz-archive file is not found.");
    }

    @Test
    void testCorruptedFile() {
        try {
            flightStatistics.logTotalNumberOfArrivedPlans(CORRUPTED_FILE);
            fail("Method logTotalNumberOfArrivedPlans didn't handle corrupted file");
        }  catch (CorruptedException e) {
            assertTrue(true, "ArrayIndexOfBoundsException is handled");
        }catch (IOException e) {
            e.printStackTrace();
        }

    }


}
