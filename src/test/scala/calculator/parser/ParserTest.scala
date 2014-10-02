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
      program("3 - 1") should parseAs ( 3 |-| 1 )
    }

    it("can be chained (and is left-associative)") {
      program("3 - 2 - 1") should parseAs( ( 3 |-| 2) |-| 1 )
    }
  }

  describe("Multiplication") {
    it("can multiply two numbers") {
      program("3 * 2") should parseAs (3 |*| 2)
    }

    it("can be chained (and is left-associative)") {
      program("3 * 2 * 1") should parseAs ( (3 |*| 2) |*| 1)
    }

  }

  describe("Division") {
    it("can divide two numbers") {
      program("4 / 2") should parseAs (4 |/| 2)
    }

    it("can be chained (and is left-associative)") {
      program("4 / 2 / 2") should parseAs ( (4 |/| 2) |/| 2)
    }

  }

  describe("Parenthetical Expressions") {
    it("can be a singleton") {
      program("(1)") should parseAs(Paren(1))
      program("(1+1)") should parseAs(Paren(1 |+| 1))
      program("(1-1)") should parseAs(Paren(1 |-| 1))
      program("(1*1)") should parseAs(Paren(1 |*| 1))
      program("(1/1)") should parseAs(Paren(1 |/| 1))
    }

    it("can override priority") {
      program("(1 + 2) * (2 + 1)") should parseAs (Paren(1 |+| 2) |*| Paren(2 |+| 1) )
      program("(2 + 2) / (2 + 2)") should parseAs (Paren(2 |+| 2) |/| Paren(2 |+| 2) )
    }
  }

  describe("Less Than Comparison") {
    it("can compare two numbers") {
      program("2 < 1") should parseAs ( 2 |<| 1 )
    }

    it("can compare to expressions") {
      program("3 + 2 < 2 * 3") should parseAs ( (3 |+| 2) |<| (2 |*| 3) )
    }
  }

  describe("Greater Than Comparison") {
    it("can compare two numbers") {
      program("2 > 1") should parseAs ( 2 |>| 1 )
    }

    it("can compare to expressions") {
      program("3 + 2 > 2 * 3") should parseAs ( (3 |+| 2) |>| (2 |*| 3) )
    }
  }

  describe("Equality") {
    it("can compare two numbers") {
      program("2 = 1") should parseAs ( 2 |=| 1 )
    }

    it("can compare to expressions") {
      program("3 + 2 = 2 * 3") should parseAs ( (3 |+| 2) |=| (2 |*| 3) )
    }
  }

  describe("Priority") {
    it("can mix addition and multiplication properly") {
      program("1 + 2 * 3") should parseAs ( 1 |+| (2 |*| 3) )
      program("2 * 3 + 1") should parseAs ( (2 |*| 3) |+| 1 )
    }

    it("can mix addition and division properly") {
      program("1 + 2 / 3") should parseAs ( 1 |+| (2 |/| 3) )
      program("2 / 3 + 1") should parseAs ( (2 |/| 3) |+| 1 )
    }

    it("can mix subtraction and multiplication properly") {
      program("1 - 2 * 3") should parseAs ( 1 |-| (2 |*| 3) )
      program("2 * 3 - 1") should parseAs ( (2 |*| 3) |-| 1 )
    }

    it("can mix subtraction and division properly") {
      program("1 - 2 / 3") should parseAs ( 1 |-| (2 |/| 3) )
      program("2 / 3 - 1") should parseAs ( (2 |/| 3) |-| 1 )
    }

    it("can mix subtraction, addition and multiplication properly") {
      program("1 - 2 * 3 + 2") should parseAs ( (1 |-| (2 |*| 3)) |+| 2)
      program("2 * 3 - 1 + 1") should parseAs ( ((2 |*| 3) |-| 1) |+| 1)
      program("1 - 2 + 2 * 3") should parseAs ( (1 |-| 2) |+| (2 |*| 3) )
    }

    it("can mix subtraction, addition and division properly") {
      program("1 - 2 / 3 + 2") should parseAs ( (1 |-| (2 |/| 3)) |+| 2)
      program("2 / 3 - 1 + 1") should parseAs ( ((2 |/| 3) |-| 1) |+| 1)
      program("1 - 2 + 2 / 3") should parseAs ( (1 |-| 2) |+| (2 |/| 3) )
    }

    it("can mix subtraction, addition, division and multiplication properly") {
      program("1 * 2 - 2 / 3 + 2") should parseAs ( (1 |*| 2) |-| (2 |/| 3) |+| 2)
      program("2 / 3 * 2 - 1 + 1") should parseAs ( (((2 |/| 3) |*| 2) |-| 1) |+| 1 )
      program("1 - 2 + 2 * 3 / 3") should parseAs ( (1 |-| 2) |+| ((2 |*| 3) |/| 3) )
    }

  }
}
