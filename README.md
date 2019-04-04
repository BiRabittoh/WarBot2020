# WarBot2020
Bootleg version of "WorldWarBot 2020" done in Java and without a map.

```
Usage: <main class> [-hv] [-m=<max_population>] [-n=<n_wars>] [-s=<source_file>]
  -h, --help             display a help message
  -m=<max_population>    filter cities for max population (default 50000)
  -n=<n_wars>            sets number of wars to simulate (default 500)
  -s=<source_file>       load data from a specific file (default data.txt)
  -v                     only simulate one war (default false)
```

Watch out for the -v flag! If you don't include it, the program will simulate multiple wars and give you win statistics, so use that if you only want to see a single outcome.

# Data collection
The program reads coordinates and populations from data.txt in this format:
```
country-name(String with no spaces) population(int) latitude(double) longitude(double)
```
For example:
```
Barcellona-Pozzo-di-Gotto 41293 38.1487398 15.2113962
```

I will soon be working on a version without a population filter.
