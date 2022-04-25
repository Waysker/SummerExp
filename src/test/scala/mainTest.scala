import org.apache.spark.sql.Row
import org.scalatest.funsuite.AnyFunSuite

import java.io.File
import java.sql.Date



class mainTest extends AnyFunSuite{
  test("mainTest.dummyRun"){
    val testData = "testData"
    val testOutput = "testOutput"
    val ghArchiveProcessor = new GhArchiveProcessor()


    val ds = ghArchiveProcessor.readRecursivelyJson(testData)
    val eventDF = ghArchiveProcessor.getWatchAndPullRequestsCount(ds)
    ghArchiveProcessor.writeToPartitionedJsonByDate(eventDF, testOutput)

    val output = ghArchiveProcessor.spark.read.json(testOutput).collect()
    assert(output === Array(
      Row(0, 4, "php/php-src", Date.valueOf("2015-01-01")),
      Row(4, 4, "uber/statsrelay", Date.valueOf("2015-01-01"))
    ))

    new File(testOutput).delete()
  }

}
