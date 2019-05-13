import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlightStatistics {

    private static final String INPUT_FILE = "./src/main/resources/planes_log.csv";
    private static final String AIRPORTS_FILE = "./src/main/resources/airports_log.csv";
    private static final String DIFFERENCE_FILE = "./src/main/resources/difference_log.csv";

    public static void main(String[] args) throws IOException {
        //Unzip input file
        new Unzip().unzip();

        //Task 1
        FileWriter outputStream = new FileWriter(new File(AIRPORTS_FILE));
        for (Map.Entry<String, Integer> pair : totalArivedPlans().entrySet()) {
            outputStream.write(String.format("%s: %d\n", pair.getKey(), pair.getValue()));
        }
        outputStream.close();


        //Task 2
        FileWriter os = new FileWriter(new File(DIFFERENCE_FILE));
        for (Map.Entry<String, Integer> pair : differenceOfArriveAndLeft().entrySet()) {
            os.write(String.format("%s: %d\n", pair.getKey(), pair.getValue()));
        }
        os.close();
    }

    private static Map<String, Integer> totalArivedPlans() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE));
        Map<String, Integer> airports = new HashMap<String, Integer>();
        String line = "";
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("\"YEAR")) continue;

            String[] lines = line.split(",");
            if (!airports.containsKey(lines[6])) {
                airports.put(lines[6], 0);
            }
            if (!airports.containsKey(lines[7])) {
                airports.put(lines[7], 1);
            } else {
                airports.put(lines[7], airports.get(lines[7]) + 1);
            }

        }
        reader.close();
        return airports;
    }

    private static Map<String, Integer> differenceOfArriveAndLeft() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE));
        final Map<String, List<Integer>> airports = new HashMap<String, List<Integer>>();

        String line = "";
        while ((line = reader.readLine()) != null) {
            List<Integer> flights = new ArrayList<Integer>();
            if (line.startsWith("\"YEAR")) continue;
            String[] lines = line.split(",");
            if (!airports.containsKey(lines[6])) {
                airports.put(lines[6], new ArrayList<Integer>() {{
                    add(0, 1);
                    add(0, 0);
                }});
            } else {
                flights = airports.get(lines[6]);
                flights.set(0, airports.get(lines[6]).get(0) + 1);
                airports.put(lines[6], flights);


            }
            if (!airports.containsKey(lines[7])) {
                airports.put(lines[7], new ArrayList<Integer>() {{
                    add(0, 0);
                    add(1, 1);
                }});
            } else {
                flights = airports.get(lines[7]);
                flights.set(1, airports.get(lines[7]).get(1) + 1);
                airports.put(lines[7], flights);
            }
        }
        reader.close();
        Map<String, Integer> difference = new HashMap<String, Integer>();
        for (Map.Entry<String, List<Integer>> pair : airports.entrySet()) {
            int diff = pair.getValue().get(1) - pair.getValue().get(0);
            if (diff != 0) {
                difference.put(pair.getKey(), diff);
            }
        }
        return difference;
    }
}

//        You'll need to extract several statistics out of the data file:
//             • List of all airports with total number of planes for the whole period that arrived to each airport
//              • Non-Zero difference in total number of planes that arrived to and left from the airport
//              • Do the point 1 but sum number of planes separately per each week
//              • Write some tests for the implemented functions
//        Each point 1-3 should produce a separate output file.

//        "YEAR","QUARTER","MONTH","DAY_OF_MONTH","DAY_OF_WEEK","FL_DATE","ORIGIN","DEST",
//                2014,1,1,1,3,2014-01-01,"JFK","LAX",
//                2014,1,1,5,7,2014-01-05,"JFK","KBP",
//                2014,1,1,6,1,2014-01-06,"KBP","LAX",
//                2014,1,1,8,3,2014-01-08,"JFK","LAX",
//                2014,1,1,12,7,2014-01-12,"JFK","KBP",
//                2014,1,1,13,1,2014-01-13,"KBP","LAX",
//
//                The output will be:
//              • First task
//                            • LAX 4
//                            • KBP 2
//                            • JFK 0
//              • Second Task
//
//                            • JFK -4
//                            • LAX +4
//
//              • Third task:
//                            • W1
//                                          • LAX 2
//                                          • KBP 1
//                                          • JFK 0
//                            • W2
//                                          • LAX 2
//                                          • KBP 1
//                                          • JFK 0






