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

    it("can subtract two numbers") {
      program("1-1") should compute (0)
    }

    it("can be chained (and is left-associative)") {
      program("100 - 2 - 1") should compute (97)
    }

    it("can handle negative numbers") {
      program("1 - -1") should compute (2)
    }

  }
  
    describe("Multiplication") {

    it("can add multiply numbers") {
      program("3*5") should compute (15)
    }

    it("can be chained (and is left-associative)") {
      program("1 * 3 * 150") should compute (450)
    }

    it("can handle negative numbers") {
      program("6 * -1") should compute (-6)
    }

  }
      describe("Division") {

    it("can add divide numbers") {
      program("100/10") should compute (10)
    }

    it("can be chained (and is left-associative)") {
      program("100/10/2") should compute (5)
    }

    it("can handle negative numbers") {
      program("60/-5") should compute (-12)
    }

  }
}
