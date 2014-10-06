package calculator.parser

import org.scalatest._

import calculator.ir._
import calculator.parser._
import edu.hmc.langtools._

// Modified by Jean Sung 
// CS 111, External Lab 
class CalcParserTests extends FunSpec with LangParseMatchers[AST] {

  override val parser = CalcParser.apply _
  
  
  // number tests 
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
  
  // addition tests 
  describe("Addition") {

    it("can add two numbers") {
      program("1+1") should parseAs ( 1 |+| 1 )
    }
    
    it("can be chained (and is left-associative)") {
      program("1 + 2 + 100") should parseAs ( (1 |+| 2) |+| 100 )
    }

  }
  
  // subtraction tests 
  describe("Subtraction") {
    it ("can subtract a small number from a big number") {
      program("5-2") should parseAs (5 |-| 2)
    }
    
    it ("can subtract a big number from a small number") {
      program("2-5") should parseAs(2 |-| 5)
    }
    
    it ("can be chained (and is left associative") {
      program("1 - 2 - 100") should parseAs ( (1 |-| 2) |-| 100 )

    }
  }
  
    // multiplication tests 
  describe("Multiplication") {
    it ("should be able to multiple 2 numbers together") {
      program("5*2") should parseAs (5 |*| 2)
    }
    
    it ("can be chained (and is left associative") {
      program("1 * 2 * 100") should parseAs ( (1 |*| 2) |*| 100 )

    }
  }
  
    // division tests 
  describe("Division") {
    it ("can divide a small number by a big number") {
      program("2/5") should parseAs (2 |/| 5)
    }
    
    it ("can divide a big number by a small number") {
      program("5/2") should parseAs(5 |/| 2)
    }
    
    it ("can be chained (and is left associative") {
      program("1 / 2 / 100") should parseAs ( (1 |/| 2) |/| 100 )

    }
    
    // divide by zero should parse 
    it ("can handle division by zero parse wise") {
      program("5 / 0") should parseAs(5 |/| 0)
    }
    
  }
  
  // mixed operations test
  
  describe("order of operations") {
    it ("can respect that mulitplication takes precedece over addition") {
      program("3 + 5 * 5") should parseAs(3 |+| (5 |*| 5))
    }
    
    it ("can respect that divison takes precedence over subtraction") {
       program("3 - 5 / 2") should parseAs(3 |-| (5 |/| 2))
    }
    
    it ("gives no precedence with regards to multiplication and division, defaulting to left associativity") {
       program("3 * 5 / 5") should parseAs((3 |*| 5) |/| 5)
    }
    
    it ("gives no precedence to addition and subtraction, defaulting to left associativity") {
      program("1 + 2 - 5") should parseAs((1 |+| 2) |-| 5)
    }
  }
  
}
