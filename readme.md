# SummerExp

A program for outputting amount of stars and pull request for each github repo.

For given month we get daily amount of stars and pull requests for every repo

Repos with zero events are filtered out

Data originates from https://www.gharchive.org/ 

## Installation

You need to have:
- sbt build tool
- spark



https://www.scala-sbt.org/download.html

## Usage

Open terminal and go into project directory

```cmd
C:\Project\Directory\>sbt
.
.
sbt:SummerExp>
```
Compile project using ``compile`` command
```cmd 
sbt:SummerExp>compile
[info] compiling 5 Scala sources to C:\Project\Directory\target\scala-2.13\classes ...
[success] Total time: 9 s, completed 25 Apr 2022, 14:06:44
```
Run project using ``run`` command while providing month with format YYYY-mm as a parameter

```cmd
sbt:SummerExp> run 2015-01
```
Alternatively you can open project in IntellIJ with sbt plugin installed, and compile and run it from there.
Add parameter by modyfing run configuration

You should also be able to create assembly JAR and run using ``spark submit`` using available spark instance, for this spark configuration in separate file would be recommended

## Behaviour

For given month, the program will get whole month from gharchive and store it inside project directory as such:
``/Project/Directory/YEAR/MONTH/YYYY-mm-dd/YYYY-mm-dd-HH.json.gz``
If data is already present this step is skipped

The data will be read by spark instance and then transformed.

``|Date|Name|Stars|Pull Requests|``

where Name is repository name

And then saved to ``/Output/Data/`` folder as a json and partitoned by date

## Tests

To run simple tests use ``test`` sbt command

## Improvements & Comments

- prepare more test data for  tests
- make end-to-end tests, and add corner cases tests
- reading from such amount of files takes much time, some local parallelism would be welcome, if not production spark with many executors would help
- tests of not "business" logic, deleting testOutput folder recursively after tests
- If I misinterpreted how data should be transformed and presented, at this point any change is easily implementable
- Date is stored as a folder name
- Given amount of time, project was not tested on unix systems, run & installation process could differ
