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
  
  describe("Subtraction") {
    
    it("can subtract two numbers") {
      program("2-1") should parseAs ( 2 |-| 1 )
    }
    
    it("can be chained (and is left associative)") {
      program("3-2-1") should parseAs ( (3 |-| 2) |-| 1 )
    }
    
  }
  
  describe("Multiplication") {
    
    it("can multiply two numbers") {
      program("6*7") should parseAs ( 6 |*| 7 )
    }
    
    it("can be chained (and is left-associative)") {
      program("3*4*5") should parseAs ( (3 |*| 4) |*| 5 )
    }
    
  }
  
  describe("Division") {
    
    it("can divide two numbers") {
      program("64/4") should parseAs ( 64 |/| 4)
    }
    
    it ("can be chained (and is left-associative)") {
      program("64/4/8") should parseAs ( ( 64 |/| 4 ) |/| 8 )
    }
    
  }
  
}
