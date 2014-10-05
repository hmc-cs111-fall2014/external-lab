package calculator.parser

/*
 * Modified by Sarah Gilkinson
 */

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
      program("1 + 1") should parseAs ( 1 |+| 1 )
    }

    
    it("can be chained (and is left-associative)") {
      program("1 + 2 + 100") should parseAs ((1 |+| 2) |+| 100 )
    }

  }

  describe("Substraction") {

    it("can subtract two numbers") {
      program("3-1") should parseAs (3 |-| 1)
    }

    it("can be chained (and is left-associative)") {
      program("3-1-1") should parseAs ((3 |-| 1) |-| 1)
    }

  }

  describe("Multiplication") {

    it("can multiply two numbers") {
      program("3*2") should parseAs (3 |*| 2)
    }

    it("can multiply with a negative") {
      program("-2*3") should parseAs (-2 |*| 3)
    }

    it("can multiply with two negatives") {
      program("-2*-3") should parseAs (-2 |*| -3)
    }

    it("can be chained (and is left-associative)") {
      program("2*3*4") should parseAs ((2 |*| 3) |*| 4)
    }
  }

  describe("Division") {

    it("can divide two numbers") {
      program("4/2") should parseAs (4 |/| 2)
    }

    it("can divide with a negative") {
      program("-4/2") should parseAs (-4 |/| 2)
    }

    it("can divide with two negatives") {
      program("-4/-2") should parseAs (-4 |/| -2)
    }

    it("can be chained (and is left-associative)") {
      program("8/2/2") should parseAs ((8 |/| 2) |/| 2)
    }
  }

  describe("Parentheses") {

    it("can surround math") {
      program("(1+2)") should parseAs (Parens(1 |+| 2))
    }

    it("can provide associativity") {
      program("(1+2)-(1+1)") should parseAs (Parens(1 |+| 2) |-| Parens(1 |+| 1))
    }

  }

  describe("Full equations") {

    it("can include multiple operations") {
      program("1+2-3*2") should parseAs ((1 |+| 2) |-| (3 |*| 2))
    }

    it("can include parentheses") {
      program("1+(2-3)*2") should parseAs (1 |+| (Parens(2 |-| 3) |*| 2))
    }

    it("can include negative numbers") {
      program("-1+(-3+1)+-2") should parseAs ((-1 |+| Parens(-3 |+| 1)) |+| -2)
    }
  }
}
