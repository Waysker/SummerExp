import java.time.format.DateTimeFormatter
import java.time.LocalDate


object main {

  def main(args: Array[String]): Unit = {

    val ghArchiveProcessor = new GhArchiveProcessor()

    val monthDate = new Utils().parseMonthArgToLocalDate(args(0))

    new GhArchiveProvider().getMonthFromGHArchive(monthDate)

    val ds = ghArchiveProcessor.readRecursivelyJson(s"${monthDate.getYear.toString}/${monthDate.getMonth.toString}")
    val eventDF = ghArchiveProcessor.getWatchAndPullRequestsCount(ds)
    ghArchiveProcessor.writeToPartitionedJsonByDate(eventDF)
    //To w readme napisz ze w produkcyjnym uruchomieniu konfig sparka mialbys w pliku

    //read gz with spark ? or decompress first and then read ?
    //reading from gz is using only one core, so no parallelism ?

//      .json(s"${testDay.getYear}/${testDay.getMonth}").as[GitArchiveDto]



    //      .groupBy("date","name", "event")
    //      .count()
    //      .withColumn("WatchEvent", when(col("event") === "WatchEvent", col("count")))
    //      .withColumn("PullRequestEvent", when(col("event") === "PullRequestEvent", col("count")))
    //      .groupBy("date", "name")
    //      .sum("WatchEvent", "PullRequestEvent")

//    df.select("type","repo.name", "created_at")
//      .groupBy("name")
//      .agg(
//        count(when(col("type") === "WatchEvent", col("type"))).as("Stars"),
//        count(when(col("type") === "PullRequestEvent", col("type"))).as("Pull Requests")
//      )
//      .filter(col("Stars") =!= 0 && col("Pull Requests") =!= 0)
//      .orderBy(desc("Stars"), desc("Pull Requests"))
//      .show()
  }
}
