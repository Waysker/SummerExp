import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}
import org.apache.spark.sql.functions.{col, count, when}


class GhArchiveProcessor {
  val spark = SparkSession
    .builder()
    .appName("GhArchiveProcessor")
    .config("spark.master", "local")
    .getOrCreate()

  import spark.implicits._


  def readRecursivelyJson(folderPath: String): Dataset[GitArchiveDto] ={
    spark.read
      .option("recursiveFileLookup", "true")
      .json(folderPath).as[GitArchiveDto]
  }

  def getWatchAndPullRequestsCount(df: Dataset[GitArchiveDto]): DataFrame ={
    df.filter($"Type" === "WatchEvent" || $"Type" === "PullRequestEvent")
      .map(m => {
        (new Utils().parseTimestampToDay(m.created_at),
          m.repo.name,
          m.Type)
      }).toDF("date", "name", "event")
      .groupBy("date", "name")
      .agg(
        count(when(col("event") === "WatchEvent", col("event"))).as("Stars"),
        count(when(col("event") === "PullRequestEvent", col("event"))).as("Pull Requests")
      )
      .orderBy("date", "name")
  }

  def writeToPartitionedJsonByDate(df: DataFrame): Unit ={
    df.write
      .partitionBy("date")
      .mode("overwrite")
      .json("Output/data/")
  }

  def writeToPartitionedJsonByDate(df: DataFrame, outputPath: String): Unit ={
    df.write
      .partitionBy("date")
      .mode("overwrite")
      .json(outputPath)
  }




}
