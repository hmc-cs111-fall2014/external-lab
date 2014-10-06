package calculator

import calculator.ir._
// Modified by Jean Sung 
// CS 111, External Lab 
package object semantics {
  def eval(ast: AST): Int = ast match {
    case Num(i) ⇒ i
    case Plus(left, right) ⇒ eval(left) + eval(right)
    case Minus(left, right) ⇒ eval(left) - eval(right)
    case Mult(left, right) ⇒ eval(left) * eval(right)
    case Div(left, right) ⇒ eval(left) / eval (right)
  }
}