# Flights Statistics
## Task 1
List of all airports with total number of planes for the whole period that arrived to each airport
```
Output file: airport_log.csv
```

## Task 2
Non-Zero difference in total number of planes that arrived to and left from the airport
```
Output file: difference_log.csv
```

## Task 3
Similar to  task № 1,  but number of planes which arrived to airports are summed separately per each week
```
Output file: perweek_log.csv
```
_Your variant of output isn't correct. It looks as if you calculate that January, 1st - is Monday.
 So from 1 till 7 of January is Week 1 and from 8-15 - Week 2 and so on._
 ### Your variant of  third task:
 ```
 • W1
   LAX 2
   KBP 1
   JFK 0
 • W2
   LAX 2
   KBP 1
   JFK 0
   ```
_But it is not correct.
Because we see in this file (of course it is just an example) that January, 1st is a 3 day of week (Wednesday)
So, e.g. January, 6 - is Week 2 (because it is Monday) So January 13 is a Week 3.
That's why I decided to write code depending on number of day of week (not as in your example above)_
