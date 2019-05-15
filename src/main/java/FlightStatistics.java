import java.io.*;
import java.util.List;
import java.util.Map;

public class FlightStatistics {

    private static final String INPUT_FILE = "./src/main/resources/planes_log.csv";
    private static final String AIRPORTS_FILE = "./src/main/resources/airports_log.csv";
    private static final String DIFFERENCE_FILE = "./src/main/resources/difference_log.csv";
    private static final String PER_WEEK_FILE = "./src/main/resources/perweek_log.csv";

    public static void main(String[] args) throws IOException {
        //Unzip input file
        new Unzip().unzip();

        //Task 1
        //List of all airports with total number of planes for the whole period that arrived to each airport

        FileWriter outputStream = new FileWriter(new File(AIRPORTS_FILE));
        Util.totalArivedPlans(INPUT_FILE).entrySet()
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


        //Task 2
       //Non-Zero difference in total number of planes that arrived to and left from the airport

        FileWriter os = new FileWriter(new File(DIFFERENCE_FILE));
        for (Map.Entry<String, Integer> pair : Util.differenceOfArriveAndLeft(INPUT_FILE).entrySet()) {
            os.write(String.format("%s: %d\n", pair.getKey(), pair.getValue()));
        }
        os.close();


        //Task 3
        //Task 1 but number of planes are summed separately per each week
        FileWriter writer = new FileWriter(new File(PER_WEEK_FILE));
        int weekIndex = 1;
        for (Map<String, Integer> weeks : Util.arivedPlansPerWeek(INPUT_FILE)) {
            writer.write("W" + weekIndex++ + ": ");
            for (Map.Entry<String, Integer> week: weeks.entrySet())
                  {
                      writer.write(String.format("%s: %d\n", week.getKey(), week.getValue()));
            }

        }
        writer.close();



    }


}

//        You'll need to extract several statistics out of the data file:
//             • List of all airports with total number of planes for the whole period that arrived to each airport
//              • Non-Zero difference in total number of planes that arrived to and left from the airport
//              • Do the point 1 but sum number of planes separately per each week
//              • Write some tests for the implemented functions
//        Each point 1-3 should produce a separate output file.

//        "YEAR","QUARTER","MONTH","DAY_OF_MONTH","DAY_OF_WEEK","FL_DATE","ORIGIN","DEST",

//              • Third task:
//                            • W1
//                                          • LAX 2
//                                          • KBP 1
//                                          • JFK 0
//                            • W2
//                                          • LAX 2
//                                          • KBP 1
//                                          • JFK 0






