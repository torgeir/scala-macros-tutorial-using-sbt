package gd.wa

import org.scalatest.FunSuite

object MacroTest extends FunSuite {

  test ("verifies regexes compile time") {
    Macros.regex("[a-z")
  }
}
