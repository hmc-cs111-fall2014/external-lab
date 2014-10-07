package calculator

import scala.language.implicitConversions

// internal DSL for creating ASTs
package object ir {

  // to use a number on its own
  implicit def int2Number(i: Int): Num = Num(i)
  
  // to use a number as part of a binary operation in any of the
  // possible locations.
  implicit def int2ExprBuilder(n: Int) = new ExprBuilder(Num(n))
  implicit def int2TermBuilder(n: Int) = new TermBuilder(Num(n))
  implicit def int2FactBuilder(n: Int) = new FactBuilder(Num(n))

  // to build up operations using infix notation from left to right...
  // ExprBuilder saves the left operand and defines methods that 
  //   take the right operand and returns the appropriate Expr 
  implicit class ExprBuilder(val left: Expr) {
    def |+|(right: Term) = Plus(left, right)
    def |-|(right: Term) = Minus(left, right)
  }
  // TermBuilder saves the left operand, a term, and defines methods that 
  //   take the right operand, a fact, and returns the appropriate 'Term' 
  implicit class TermBuilder(val left: Term) {
    def |*|(right: Fact) = Mult(left, right)
    def |/|(right: Fact) = Div(left, right)
  }
  // The same for a Fact.
  implicit class FactBuilder(val num: Fact) 

}