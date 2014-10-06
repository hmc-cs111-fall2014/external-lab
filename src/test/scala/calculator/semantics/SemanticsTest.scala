package calculator.semantics

import org.scalatest._

import calculator.ir._
import calculator.parser._
import calculator.semantics._
import edu.hmc.langtools._

// Modified by Jean Sung 
// CS 111, External Lab 
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

    it("can subract a small number from a big number") {
      program("5-2") should compute (3)
    }

    it("can subtract a big number from a small number") {
      program("2-5") should compute (-3)
    }

    it("can handle negative numbers") {
      program("1 - -1") should compute (2)
    }

  }
  
      // multiplication tests 
  describe("Multiplication") {
    it ("should be able to multiple 2 numbers together") {
      program("5*2") should compute (10)
    }
    
    it ("can be chained (and is left associative") {
      program("1 * 2 * 100") should compute (200)

    }
  }
  
    // division tests 
  describe("Division") {
    it ("can divide a small number by a big number as per integer division") {
      program("2/5") should compute (0)
    }
    
    it ("can divide a big number by a small number") {
      program("5/2") should compute(2)
    }
    
    it ("can be chained (and is left associative") {
      program("100 / 2 / 1") should compute (50)

    }
    
    // division by zero 
    program("100 / 0 ") should raiseError[ArithmeticException]
  }
  
  // mixed operations tests
    describe("order of operations") {
    it ("can respect that mulitplication takes precedece over addition") {
      program("3 + 5 * 5") should compute (28)
    }
    
    it ("can respect that divison takes precedence over subtraction") {
       program("3 - 5 / 2") should compute (1)
    }
    
    it ("gives no precedence with regards to multiplication and division, defaulting to left associativity") {
       program("3 * 5 / 5") should compute (3)
    }
    
    it ("gives no precedence to addition and subtraction, defaulting to left associativity") {
      program("1 + 2 - 5") should compute(-2)
    }
  }


}
