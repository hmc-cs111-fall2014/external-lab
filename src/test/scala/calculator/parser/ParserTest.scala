package calculator.parser

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
      program("1+1") should parseAs ( 1 |+| 1 )
    }
    
    it("can be chained (and is left-associative)") {
      program("1 + 2 + 100") should parseAs ( (1 |+| 2) |+| 100 )
    }

  }

  describe("Minus") {

    it("can subtract two numbers") {
      program("1-1") should parseAs ( 1 |-| 1 )
    }

    it("can be chained (and is left-associative)") {
      program("1 - 2 - 100") should parseAs ( (1 |-| 2) |-| 100 )
    }

  }

  describe("Times") {

    it("can multiply two numbers") {
      program("21 * 2") should parseAs ( 21 |*| 2 )
    }

    it("can be chained (and is left-associative)") {
      program("21 * 2 * 3") should parseAs ( (21 |*| 2) |*| 3 )
    }

  }

  describe("Divide") {

    it("can divide two numbers") {
      program("20 / 2") should parseAs ( 20 |/| 2 )
    }

    it("can be chained (and is left-associative)") {
      program("20 / 2 / 5") should parseAs ( (20 |/| 2) |/| 5 )
    }

  }

  describe("Parens") {

    it("can create precedence for two numbers") {
      program("(20 / 2)") should parseAs ( Paren(20 |/| 2) )
    }

    it("can be used with division and override left-associativity") {
      program("20 / (5 / 5)") should parseAs ( 20 |/| Paren(5 |/| 5) )
    }

    it("can be used with addition and override precedence") {
      program("3 * (2 + 5)") should parseAs ( 3 |*| Paren(2 |+| 5) )
    }

  }
}
