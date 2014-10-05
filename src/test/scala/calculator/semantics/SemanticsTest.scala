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
      program("1 - 1") should compute (0)
    }

    it("can be chained (and is left-associative)") {
      program("1 - 2 - 100") should compute (-101)
    }

    it("can handle negative numbers") {
      program("1 - -1") should compute (2)
    }
    
    it("Can be mixed with addition") {
      program("1+2 - 4") should compute(-1)
    }
  }
  describe("multiplication") {
     it("can subtract two numbers") {
      program("1 * 1") should compute (1)
    }

    it("can be chained (and is left-associative)") {
      program("2 * 2 * 100") should compute (400)
    }

    it("can handle negative numbers") {
      program("1 * -1") should compute (-1)
    }
    
    it("Can be mixed with addition") {
      program("2 + 2 * 100") should compute(202)
    }
  }
  
   describe("division") {
     it("can divide two numbers") {
      program("1 / 1") should compute (1)
    }

    it("can be chained (and is left-associative)") {
      program("2 / 2 / 1") should compute (1)
    }

    it("can handle negative numbers") {
      program("1 / -1") should compute (-1)
    }
    
    it("Can be mixed with addition") {
      program("2 + 6 / 3") should compute(4)
    }
  }
   
    describe("parens") {
     it("does not effect operations on two numbers") {
      program("(1 / 1)") should compute (1)
    }

    it("does not effect operations on one number") {
      program("(1)") should compute (1)
    }

    it("can group operations (when it isn't important)") {
      program("(1+1) + 1") should compute (3)
    }
    
    it("can group operations when it is important") {
      program("4/(2*2)") should compute(1)
    }
  }
}
