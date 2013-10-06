package gd.wa

import org.scalatest.FunSuite
import scala.util.matching.Regex

class NamedRegexMacroTest extends FunSuite {

  import NamedRegexMacro._

  test ("creates methods for the regex matching groups") {
    val regex = namedRegex("Torgeir, 29", "([a-zA-Z]+), ([1-9](:?[0-9]+)?)",
      "name",
      "age")

    val expectedName = regex.name.getOrElse("matching name group failed")
    val expectedAge  = regex.age.getOrElse("matching age group failed")

    assert(expectedName == "Torgeir")
    assert(expectedAge  == "29")
  }
}
