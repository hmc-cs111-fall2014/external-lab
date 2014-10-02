package calculator.semantics

import org.scalatest._

import calculator.ir._
import calculator.parser._
import calculator.semantics._
import edu.hmc.langtools._

class NumSemanticsTests extends FunSpec
    with LangInterpretMatchers[AST, Any] {

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
      program("3-1") should compute (2)
    }

    it("can be chained (and is left-associative)") {
      program("3 - 2 - 1") should compute (0)
    }

    it("can handle negative numbers") {
      program("-1 - -1") should compute (0)
    }
  }

  describe("Multiplication") {
    it("can multiply two numbers") {
      program("3 * 1") should compute (3)
    }

    it("can be chained (and is left-associative)") {
      program("3 * 2 * 1") should compute (6)
    }

    it("can handle negative numbers") {
      program("-1 * -1") should compute (1)
    }
  }

  describe("Division") {
    it("can divide two numbers") {
      program("4 / 2") should compute (2)
    }

    it("can be chained (and is left-associative)") {
      program("4 / 2 / 2") should compute (1)
    }

    it("can handle negative numbers") {
      program("10 / -5") should compute (-2)
    }
  }

  describe("Parenthetical Expressions") {
    it("can be a singleton") {
      program("(1)")   should compute(1)
      program("(1+1)") should compute(2)
      program("(1-1)") should compute(0)
      program("(1*1)") should compute(1)
      program("(1/1)") should compute(1)
    }

    it("can override priority") {
      program("(1 + 2) * (2 + 1)") should compute (9)
      program("(2 + 2) / (2 + 2)") should compute (1)
    }
  }

  describe("Less Than Comparison") {
    it("can compare two numbers") {
      program("2 < 1") should compute (false)
      program("1 < 2") should compute (true)
    }

    it("can compare to expressions") {
      program("3 + 2 < 2 * 3") should compute (true)
      program("3 * 2 < 2 + 3") should compute (false)
    }
  }

  describe("Equality") {
    it("can compare two numbers") {
      program("2 = 1") should compute (false)
      program("1 = 1") should compute (true)
    }

    it("can compare to expressions") {
      program("3 + 2 + 1 = 2 * 3") should compute (true)
      program("3 * 2 = 2 + 3") should compute (false)
    }
  }

  describe("Greater Than Comparison") {
    it("can compare two numbers") {
      program("2 > 1") should compute (true)
      program("1 > 2") should compute (false)
    }

    it("can compare to expressions") {
      program("3 + 2 > 2 * 3") should compute (false)
      program("3 * 2 > 2 + 3") should compute (true)
    }
  }

  describe("Priority") {
    it("can multiply and add in correct priority order") {
      program("1 + 2 * 3") should compute (7)
    }

    it("can multiply and subtract in correct priority order") {
      program("1 - 2 * 3") should compute (-5)
    }

    it("can multiply, add, subtract in correct priority order") {
      program("1 + 2 - 2 * 3") should compute (-3)
    }

    it("can divide and add in correct priority order") {
      program("3 + 6 / 3") should compute (5)
    }

    it("can divide and subtract in correct priority order") {
      program("2 - 6 / 3") should compute (0)
    }

    it("can divide, add, subtract in correct priority order") {
      program("1 + 2 - 9 / 3") should compute (0)
    }

    it("can multiply, divide, add, subtract in correct priority order") {
      program("1 + 2 - 9 / 3 * 3") should compute (-6)
    }

  }
}
