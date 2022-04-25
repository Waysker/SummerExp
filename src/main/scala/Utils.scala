import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Utils {

  def parseTimestampToDay(timestamp: String): String ={
    import java.time.LocalDateTime
    import java.time.format.DateTimeFormatter
    val pattern = "yyyy-MM-dd'T'HH:mm:ssz"
    val formatter = DateTimeFormatter.ofPattern(pattern)
    LocalDateTime.from(formatter.parse(timestamp)).toLocalDate.toString
  }

  def parseMonthArgToLocalDate(month: String): LocalDate ={
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    LocalDate.parse(month + "-01", formatter)
  }

}
