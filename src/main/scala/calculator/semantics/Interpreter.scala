package calculator

import calculator.ir._

package object semantics {
  def eval(ast: AST): Int = ast match {
    case Num(i) ⇒ i
    case Plus(left, right) => eval(left) + eval(right)
    case Minus(left, right) => eval(left) - eval(right)
    case Times(left, right) => eval(left) * eval(right)
    case Divide(left, right) => eval(left) / eval(right)
    case Paren(expr) => eval(expr)
    case LessThan(left, right) => if (eval(left) < eval(right)) 1 else 0
    case GreaterThan(left, right) => if (eval(left) > eval(right)) 1 else 0
    case Equals(left, right) => if (eval(left) == eval(right)) 1 else 0
  }
}