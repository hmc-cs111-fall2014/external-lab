package calculator

import scala.language.implicitConversions

// internal DSL for creating ASTs
package object ir {

  // to use a number on its own
  implicit def int2Number(i: Int): Num = Num(i)

  // to use a number as part of a binary operation...
  implicit def intToFactBuilder(n: Int) = new FactBuilder(Num(n))
  implicit def intToTermBuilder(n: Int) = new TermBuilder(Num(n))
  implicit def intToExprBuilder(n: Int) = new ExprBuilder(Num(n))

  // to build up operations using infix notation from left to right...
  // ExprBuilder saves the left operand and defines methods that
  //   take the right operand and returns the appropriate Expr
  implicit class ExprBuilder(val left: Expr) {
    def |+|(right: Term) = Plus(left, right)
    def |-|(right: Term) = Minus(left, right)
  }

  // Similar for terms.
  implicit class TermBuilder(val left: Term) {
    def |*|(right: Fact) = Mult(left, right)
    def |/|(right: Fact) = Div(left, right)
  }

  implicit class FactBuilder(val me: Num) {
  }

}
