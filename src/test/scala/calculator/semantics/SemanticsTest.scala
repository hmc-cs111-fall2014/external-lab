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

  describe("Subtraction") {

    it("can add two numbers") {
      program("1-1") should compute (0)
    }

    it("can be chained (and is left-associative)") {
      program("1 - 2 - 100") should compute (-101)
    }

    it("can handle negative numbers") {
      program("1 - -1") should compute (2)
    }

  }

  describe("Multiplication") {

    it("can multipy two numbers") {
      program("1*2") should compute (2)
    }

    it("can be chained (and is left-associative)") {
      program("1 * 2 * 100") should compute (200)
    }

    it("can handle negative numbers") {
      program("2 * -1") should compute (-2)
      program("-2 * -1") should compute (2)
    }

  }

  describe("Divisation") {

    it("can divide two numbers") {
      program("1 / 2") should compute (0)
      program("10 / 5") should compute (2)
    }

    it("can be chained (and is left-associative)") {
      program("100 / 5 / 10") should compute (2)
    }

    it("can handle negative numbers") {
      program("20 / -10") should compute (-2)
      program("-30 / -10") should compute (3)
    }

  }

  describe("Parensizizization") {

    it("can parenthesize two numbers") {
      program("(1) + 2") should compute (3)
      program("2 + (4 + 5)") should compute (11)
    }

    it("can be chained (and is associative)") {
      program("1 + (2 + (3 + 4))") should compute (10)
      program("((1 + 2) + 3) + 4") should compute (10)
    }

  }

}
