import org.apache.spark.sql.Row
import org.scalatest.funsuite.AnyFunSuite

class GhArchiveProcessorTest extends AnyFunSuite {
  //TODO add better tests
  val pathToTestFolder = "testData"
  val ghArchiveProcessor = new GhArchiveProcessor()
  test("GhArchiveProcessor.readRecursivelyJson"){


    assert(!ghArchiveProcessor.readRecursivelyJson(pathToTestFolder).isEmpty)
  }

  test("GhArchiveProcessor.getWatchAndPullRequestsCountWithRead"){
    val df = ghArchiveProcessor.readRecursivelyJson(pathToTestFolder)
    val value = ghArchiveProcessor.getWatchAndPullRequestsCount(df).collect()
    assert(value(1)(3) == 4)
    assert(value(1)(2) == 4)

  }

  test("GhArchiveProcessor.getWatchAndPullRequestsCount"){
    val data = Seq(
      ("2015-01-01T16:00:00Z", RepoDto("dev/test"), "WatchEvent"),
      ("2015-01-01T16:00:00Z", RepoDto("dev/test"), "WatchEvent"),
      ("2015-01-01T16:00:00Z", RepoDto("dev/test"), "PullRequestEvent"),
      ("2015-01-01T16:00:00Z", RepoDto("dev/bomb"), "PullRequestEvent"),
      ("2015-01-01T16:00:00Z", RepoDto("dev/bomb"), "PullRequestEvent"),
      ("2015-01-01T16:00:00Z", RepoDto("dev/bomb"), "WatchEvent"))
    import ghArchiveProcessor.spark.implicits._
    val df = data.toDF("created_at", "repo", "Type").as[GitArchiveDto]
    val value = ghArchiveProcessor.getWatchAndPullRequestsCount(df).collect()
    assert(value(0) == Row("2015-01-01","dev/bomb",1,2))
    assert(value(1) == Row("2015-01-01", "dev/test",2,1))
  }


}
