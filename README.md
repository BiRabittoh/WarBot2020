# WarBot2020
Bootleg version of "WorldWarBot 2020" done in Java and without a map.

Usage:
```
executable.jar soglia_popolazione verbose n_guerre
```
- soglia_popolazione is an integer (default 12000)
- verbose is a boolean. true gives you the detailed output of a single war, false gives you generic statistics on n_guerre wars (default true)
- n_guerre is the number of wars to simulate (default 500)

The program will load the default values if you don't always put all three parameters.

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
