package calculator.parser

import org.scalatest._

import calculator.ir._
import calculator.parser._
import edu.hmc.langtools._

class CalcParserTests extends FunSpec with LangParseMatchers[AST] {

  override val parser = CalcParser.apply _

  describe("A number") {

    it("can be a single digit") {
      program("1") should parseAs ( 1 )
    }

    it ("can be multiple digits") {
      program("10") should parseAs ( 10 )
      program("121") should parseAs ( 121 )
    }

    it ("can be a negative number") {
      program("-10") should parseAs ( -10 )
    }

    it ("cannot be floating-point number") {
      program("1.1") should not (parse)
      program(" .3") should not (parse)
    }

  }

  describe("Addition") {

    it("can add two numbers") {
      program("1+1") should parseAs ( 1 |+| 1 )
    }

    it("can be chained (and is left-associative)") {
      program("1 + 2 + 100") should parseAs ( (1 |+| 2) |+| 100 )
    }

  }

  describe("Subtraction") {

    it("can subtract two numbers") {
      program("1-1") should parseAs ( 1 |-| 1 )
      program("10-2") should parseAs (10 |-| 2)
    }

    it("can be chained (and is left-associative)") {
      program("1 + 2 + 100") should parseAs ( (1 |+| 2) |+| 100 )
      program("10 - 2 - 1") should parseAs ( (10 |-| 2) |-| 1 )
    }

    it("can handle negative numbers") {
      program("1 - 10") should parseAs (1 |-| 10)
    }

  }

  describe("Multiplication") {

    it("can multiply two numbers") {
      program("1*1") should parseAs ( 1 |*| 1 )
      program("10*2") should parseAs (10 |*| 2)
    }

    it("can be chained (and is left-associative)") {
      program("1 * 2 * 100") should parseAs ( (1 |*| 2) |*| 100 )
      program("10 * 2 * 1") should parseAs ( (10 |*| 2) |*| 1 )
    }

    it("can handle negative numbers") {
      program("1 * -10") should parseAs (1 |*| -10)
    }
  }

  describe("Divistion") {

    it("can divide two numbers") {
      program("1/1") should parseAs ( 1 |/| 1 )
      program("10/2") should parseAs (10 |/| 2)
    }

    it("can be chained (and is left-associative)") {
      program("100 / 5 / 10") should parseAs ( (100 |/| 5) |/| 10 )
      program("10 / 2 / 1") should parseAs ( (10 |/| 2) |/| 1 )
    }

    it("can handle negative numbers") {
      program("10 / -1") should parseAs (10 |/| -1)
    }

  }

  describe("Parenthesisation") {

    it("can handle parenthesised numbers") {
      program("100 + (2)") should parseAs (100 |+| Parens(2))
      program("(3) + 27") should parseAs (Parens(3) |+| 27)
    }

    it("can be chained (and is associative)") {
      program("100 + (2 + (3 + 5))") should parseAs (100 |+| Parens(2 |+| Parens(3 |+| 5)))
      program("((10 + 5) + 5) + 5") should parseAs (Parens(Parens(10 |+| 5) |+| 5) |+| 5)
    }
  }
}
