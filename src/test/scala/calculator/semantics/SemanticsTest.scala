package calculator.semantics

import org.scalatest._

import calculator.ir._
import calculator.parser._
import calculator.semantics._
import edu.hmc.langtools._

class NumSemanticsTests extends FunSpec
    with LangInterpretMatchers[AST, Int] {

  override val parser = CalcParser.apply _
  override val interpreter = eval _

  describe("A number") {

    it("should evaluate to an integer") {
      program("1") should compute (1)
      program("10") should compute (10)
      program("121") should compute (121)
      program("-10") should compute (-10)
    }

  }

  describe("Addition") {

    it("can add two numbers") {
      program("1+1") should compute (2)
    }

    it("can be chained (and is left-associative)") {
      program("1 + 2 + 100") should compute (103)
    }

    it("can handle negative numbers") {
      program("1 + -1") should compute (0)
    }

  }

  describe("Minus") {

    it("can subtract two numbers") {
      program("1-1") should compute (0)
    }

    it("can be chained (and is left-associative)") {
      program("1 - 2 - 100") should compute (-101)
    }

    it("can handle negative numbers") {
      program("-1 - -1") should compute (0)
    }

  }

  describe("Times") {

    it("can multiply two numbers") {
      program("21 * 2") should compute (42)
    }

    it("can be chained (and is left-associative)") {
      program("21 * 2 * 3") should compute (126)
    }

    it("can handle negative numbers") {
      program("-1 * 1") should compute (-1)
    }

  }

  describe("Divide") {

    it("can divide two numbers") {
      program("20 / 2") should compute (10)
    }

    it("can be chained (and is left-associative)") {
      program("20 / 2 / 5") should compute (2)
    }

    it("can handle negative numbers") {
      program("-1 / 1") should compute (-1)
    }

  }

  describe("Parens") {

    it("can create precedence for two numbers") {
      program("(20 / 2)") should compute (10)
    }

    it("can be used with division and override left-associativity") {
      program("20 / (5 / 5)") should compute (20)
    }

    it("can be used with addition and override precedence") {
      program("3 * (2 + 5)") should compute (21)
    }

  }

  describe("Comparators") {

    it("can use less than to return true") {
      program("2 < 3") should compute (1)
    }

    it("can use less than to return false") {
      program("3 < 2") should compute (0)
    }

    it("can use greater than to return true") {
      program("3 > 2") should compute (1)
    }

    it("can use greater than to return false") {
      program("2 > 3") should compute (0)
    }

    it("can use equality to return true") {
      program("2 = 2") should compute (1)
    }

    it("can use equality to return false") {
      program("2 = 3") should compute (0)
    }

  }

}
