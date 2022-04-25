import org.apache.commons.io.FileUtils

import java.io.File
import java.net.URL
import java.time.LocalDate

class GhArchiveProvider {
  def getDayFromGHArchive(day: LocalDate): Unit ={
    val hours = 23
    for (n <- 0 to hours){
      val path = s"${day.getYear}/${day.getMonth}/$day/$day-$n.json.gz"
      if (!new File(path).exists()){
        println(s"Getting: $path")
        FileUtils.copyURLToFile(
          new URL(s"https://data.gharchive.org/$day-$n.json.gz"),
          new File(path));
      }

    }
  }
  def getMonthFromGHArchive(month: LocalDate): Unit ={
    val days = month.lengthOfMonth()
    for (n <- 1 to days){
      getDayFromGHArchive(LocalDate.of(month.getYear, month.getMonth, n))
    }
  }
}
