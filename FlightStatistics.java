import java.io.*;
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
        for (Map.Entry<String, Integer> pair : Util.differenceOfArrivedAndLeft(INPUT_FILE).entrySet()) {
            os.write(String.format("%s: %d\n", pair.getKey(), pair.getValue()));
        }
        os.close();


      /*Task 3
         Similar to  task № 1,  but number of planes which arrived to airports are summed separately per each week
         Your variant of input isn't correct. It looks as if you calculate that January, 1st - is Monday.
         So from 1-7 is Week1 and from 8-15 - Week2 and so on.
                    • Third task:
                                    • W1
                                                  • LAX 2
                                                  • KBP 1
                                                  • JFK 0
                                    • W2
                                                  • LAX 2
                                                  • KBP 1
                                                  • JFK 0
         But it is not correct. Because we see in this file (of course it is just an example) that January, 1st is a 3 day of week (Wednesday)
         So, e.g. January, 6 - is Week 2 (because it is Monday) So January 13 is a Week 3
         I decided to write code depending on number of day of week (not as in your example above)     */


        FileWriter writer = new FileWriter(new File(PER_WEEK_FILE));
        int weekIndex = 1;
        for (Map<String, Integer> weeks : Util.arrivedPlansPerWeek(INPUT_FILE)) {
            writer.write("W" + weekIndex++ + ":\n");
            for (Map.Entry<String, Integer> week: weeks.entrySet())
                  {
                      writer.write(String.format("%s: %d\n", week.getKey(), week.getValue()));
            }

        }
        writer.close();



    }


}







