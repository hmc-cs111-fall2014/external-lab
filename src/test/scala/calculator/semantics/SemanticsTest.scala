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

    it("can subtract one number from another") {
      program("1-1") should compute (0)
    }

    it("can be chained (and is left-associative)") {
      program("100 - 20 - 10") should compute (70)
    }

    it("can handle negative numbers") {
      program("1 - -1") should compute (2)
    }

  }
  
  describe("Multiplication") {
    it("can multiply two number") {
      program("1*1") should compute(1)
      program("1*0") should compute(0)
      program("0*1") should compute (0)
      program("2*2") should compute (4)
    }
    
    it("can be chained (and is left-associative)") {
      program("1 * 2 * 3") should compute (6)
    }
    
    it("can handle negative numbers") {
      program("1 * -1") should compute (-1)
      program("-1 * 1") should compute (-1)
      program("-1 * -1") should compute (1)
    }
  }
  
  describe("Division") {
    it("can divide one number by another") {
      program("1/1") should compute(1)
      program("0/1") should compute (0)
      program("4/2") should compute (2)
    }
    
    it("can be chained (and is left-associative)") {
      program("10 / 5 / 2") should compute (1)
    }
    
    it("can handle negative numbers") {
      program("1 / -1") should compute (-1)
      program("-1 / 1") should compute (-1)
      program("-1  / -1") should compute (1)
    }
  }
  
  describe("Parentheses") {
    it("should force association") {
      program("(10*2) / 4") should compute (5)
      program("10 / (4 - 2)") should compute (5)
    }
  }
    
}
