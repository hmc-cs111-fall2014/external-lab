package calculator

import calculator.ir._

package object semantics {
  def eval(ast: AST): Int = ast match {
    case Num(i) => i
    case Plus(left, right) => eval(left) + eval(right)
    case Minus(left, right) => eval(left) - eval(right)
    case Divides(left, right) => eval(left) / eval(right)
    case SingleTerm(term: Expr) => eval(term)
    case Times(left: Expr, right: Expr) => eval(left) * eval(right)
    case SingleFact(fact: Expr) => eval(fact)
    case Parens(exp: Expr) => eval(exp)
  }
}