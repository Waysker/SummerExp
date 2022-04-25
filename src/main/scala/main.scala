
object main {

  def main(args: Array[String]): Unit = {

    val ghArchiveProcessor = new GhArchiveProcessor()

    val monthDate = new Utils().parseMonthArgToLocalDate(args(0))

    new GhArchiveProvider().getMonthFromGHArchive(monthDate)

    val ds = ghArchiveProcessor.readRecursivelyJson(s"${monthDate.getYear.toString}/${monthDate.getMonth.toString}")
    val eventDF = ghArchiveProcessor.getWatchAndPullRequestsCount(ds)
    ghArchiveProcessor.writeToPartitionedJsonByDate(eventDF)
  }
}
