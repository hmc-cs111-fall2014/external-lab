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
      program("2-1") should compute (1)
    }
    
    it ("can be chained (and is left-associative)") {
      program("3-2-1") should compute (0)
    }
    
    it ("can handle negative values and answers") {
      program("-1-4") should compute(-5)
    }
    
  }
  
  describe("Multiplication") {
    
    it("can multiply two numbers") {
      program("6*7") should compute (42)
    }
    
    it("can be chained (and is left-associative)") {
      program("3*4*5") should compute (60)
    }
    
    it ("can handle negative values and answers") {
      program("2*-3*7") should compute (-42)
    }
    
  }
  
  describe("Division") {
    
    it ("can divide two numbers that divide each other") {
      program("64/4") should compute (16)
    }
    
    it ("will round down when the numbers do not divide evenly") {
      program("169/4") should compute (42)
    }
    
    it ("can be chained (and is left-associative)") {
      program("64/4/8") should compute (2)
    }
    
    it ("can handle negative values and answers") {
      program("-64/8") should compute (-8)
    }
    
  }

}
