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
      program("1 - 2 - 100") should compute (-101)
    }

    it("can handle negative numbers") {
      program("2 - -1") should compute (3)
    }

  }
    
  describe("Multiplication") {

    it("can multiply two numbers") {
      program("2*2") should compute (4)
    }

    it("can be chained (and is left-associative with PEMDAS)") {
      program("1 - 2 * 100") should compute (-199)
    }

    it("can handle negative numbers") {
      program("2 * -1") should compute (-2)
    }

  }
  
  describe("Division") {

    it("can divide two numbers") {
      program("2/2") should compute (1)
    }

    it("can be chained (and is left-associative with PEMDAS)") {
      program("100 - 100 / 2") should compute (50)
    }

    it("can handle negative numbers") {
      program("2 / -1") should compute (-2)
    }

  }
  
  describe("Parentheses") {

    it("can surround an expression") {
      program("(2/2)") should compute (1)
    }
    
    it("can surround a single number"){
      program("(42)") should compute ( 42 )
    }
   
    it("works with PEMDAS") {
      program("100 / (4 - 2)") should compute (50)
    }

    it("can handle negative numbers") {
      program("-(2 / -1)") should compute (2)
    }

  }

}
