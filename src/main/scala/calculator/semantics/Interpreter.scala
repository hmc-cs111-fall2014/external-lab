package calculator

/*
 * Modified by Sarah Gilkinson
 */

import calculator.ir._

package object semantics {
  def eval(ast: AST): Int = ast match {
    case Num(i) => i
    case Plus(left, right) => eval(left) + eval(right)
    case Minus(left, right) => eval(left) - eval(right)
    case Divides(left, right) =>
      if (eval(right) == 0) 0 else eval(left) / eval(right)
    case Times(left: Expr, right: Expr) => eval(left) * eval(right)
    case Parens(exp: Expr) => eval(exp)
    case lt(left, right) => if(eval(left) < eval(right)) 1 else 0
    case gt(left, right) => if(eval(left) > eval(right)) 1 else 0
    case equality(left, right) => if(eval(left) == eval(right)) 1 else 0
    case inequality(left, right) => if(eval(left) == eval(right)) 0 else 1
    case power(left, right) => (math.pow(eval(left), eval(right))).toInt
  }
}