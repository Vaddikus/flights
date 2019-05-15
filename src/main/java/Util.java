import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Util {

    public static Map<String, Integer> totalArivedPlans(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
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

    public static Map<String, Integer> differenceOfArriveAndLeft(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        final Map<String, List<Integer>> airports = new HashMap<String, List<Integer>>();

        String line = "";
        while ((line = reader.readLine()) != null) {
            List<Integer> flights;
            if (line.startsWith("\"YEAR")) continue;
            String[] lines = line.split(",");
            if (!airports.containsKey(lines[6])) {
                airports.put(lines[6], new ArrayList<Integer>() {{
                    add(0, 1);
                    add(1, 0);
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

        Map<String, Integer> difference = new HashMap<>();
        for (Map.Entry<String, List<Integer>> pair : airports.entrySet()) {
            int diff = pair.getValue().get(1) - pair.getValue().get(0);
            if (diff != 0) {
                difference.put(pair.getKey(), diff);
            }
        }
        return difference;
    }

       /*      2014,1, 1,1,3, 2014-01-01,"JFK","LAX",
                2014,1, 1,5,7, 2014-01-05,"JFK","KBP",
                2014,1, 1,6,1, 2014-01-06,"KBP","LAX",

                2014,1, 1,8,3, 2014-01-08,"JFK","LAX",
                2014,1, 1,12,7, 2014-01-12,"JFK","KBP",
                2014,1, 1,13,1, 2014-01-13,"KBP","LAX",
                2014,1, 3,13,1, 2014-01-13,"KBP","LAX",
       */

    public static List<Map<String, Integer>> arivedPlansPerWeek(String file){

        List<Map<String, Integer>> airports = new ArrayList<>();
        String line = "";
        int dayOfWeekBefore = 0;
        int weekIndex = 0;
        LocalDate dayBefore = LocalDate.of(1990,1,1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try( BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("\"YEAR")) continue;
                String[] parts = line.split(",");
                int dayOfWeek = Integer.parseInt(parts[4]);
                LocalDate day = LocalDate.parse(parts[5], formatter);
                if (day.getYear() - dayBefore.getYear() > 20) {
                    airports.add(new HashMap<>());
                }
                if (dayOfWeek > dayOfWeekBefore && day.minusDays(7).isBefore(dayBefore)) {
                    if (airports.get(weekIndex).containsKey(parts[6])) {

                        airports.get(weekIndex).put(parts[6], 0);
                    }
                    if (!airports.get(weekIndex).containsKey(parts[7])) {
                        airports.add(new HashMap<>());
                        airports.get(weekIndex).put(parts[7], 1);
                    } else {
                        airports.get(weekIndex).put(parts[7], airports.get(weekIndex).get(parts[7]) + 1);
                    }
                } else {
                    weekIndex++;
                    airports.add(new HashMap<>());
                    if (airports.get(weekIndex).containsKey(parts[6])) {

                        airports.get(weekIndex).put(parts[6], 0);
                    }
                    if (!airports.get(weekIndex).containsKey(parts[7])) {
                        airports.get(weekIndex).put(parts[7], 1);
                    } else {
                        airports.get(weekIndex).put(parts[7], airports.get(weekIndex).get(parts[7]) + 1);
                    }
                }
                dayBefore = day;
                dayOfWeekBefore = dayOfWeek;
            }
        }
        catch (IOException e) {
            System.out.println("Input file couldn't be read!" + e.getMessage());
            e.printStackTrace();
        }
        catch (StringIndexOutOfBoundsException e) {
            System.out.println("I\"Data in file has incorrect format" + e.getMessage());
            e.printStackTrace();
        }
        catch (Exception e) {
            System.out.println("Something went wrong!");
            e.printStackTrace();
        }
        return airports;
    }

}
