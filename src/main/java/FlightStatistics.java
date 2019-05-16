import java.io.*;
import java.util.Map;

public class FlightStatistics {

    private static final String INPUT_FILE = "./src/main/resources/planes_log.csv";
    private static final String AIRPORTS_FILE = "./src/main/resources/airports_log.csv";
    private static final String DIFFERENCE_FILE = "./src/main/resources/difference_log.csv";
    private static final String PER_WEEK_FILE = "./src/main/resources/perweek_log.csv";


    public FlightStatistics() {
        //Unzip input file
        new Unzip().unzip();
    }


    /**
     * Task 1
     * List of all airports with total number of planes for the whole period that arrived to each airport
     */
    public void logTotalNumberOfArrivedPlans(String... file) throws IOException, CorruptedException {
        FileWriter outputStream = new FileWriter(new File(AIRPORTS_FILE));
        Util.totalArivedPlans(file == null ? INPUT_FILE : file[0]).entrySet()
                .stream()
                .sorted((x, y) -> y.getValue().compareTo(x.getValue()))
                .forEach((k) -> {
                    try {
                        outputStream.write(k.getKey() + ":" + k.getValue() + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        outputStream.close();
    }

    /**
     * Task 2
     * Non-Zero difference in total number of planes that arrived to and left from the airport
     */
    public void logDifferenceOfArivedAndLeftPlanes(String ... file) throws IOException {
        FileWriter os = new FileWriter(new File(DIFFERENCE_FILE));
        for (Map.Entry<String, Integer> pair : Util.differenceOfArrivedAndLeft(file == null ? INPUT_FILE : file[0]).entrySet()) {
            os.write(String.format("%s: %d\n", pair.getKey(), pair.getValue()));
        }
        os.close();
    }

    /**
     * Task 2
     *   Similar to  task № 1,  but number of planes which arrived to airports are summed separately per each week
     */

    public void logArivedPlanesPerWeek() throws IOException {

        FileWriter writer = new FileWriter(new File(PER_WEEK_FILE));
        int weekIndex = 1;
        for (
                Map<String, Integer> weeks : Util.arrivedPlansPerWeek(INPUT_FILE)) {
            writer.write("W" + weekIndex++ + ":\n");
            for (Map.Entry<String, Integer> week : weeks.entrySet()) {
                writer.write(String.format("%s: %d\n", week.getKey(), week.getValue()));
            }

        }
        writer.close();
    }

    public static String getInputFile() {
        return INPUT_FILE;
    }

    public static String getAirportsFile() {
        return AIRPORTS_FILE;
    }

    public static String getDifferenceFile() {
        return DIFFERENCE_FILE;
    }

    public static String getPerWeekFile() {
        return PER_WEEK_FILE;
    }
}










