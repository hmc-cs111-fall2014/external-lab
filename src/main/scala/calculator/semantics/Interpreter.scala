package calculator

import calculator.ir._

package object semantics {
  def eval(ast: AST): Any = ast match {
    case Num(i) => i
    case Plus(left, right) => eval(left).asInstanceOf[Int] + eval(right).asInstanceOf[Int]
    case Minus(left, right) => eval(left).asInstanceOf[Int] - eval(right).asInstanceOf[Int]
    case Times(left, right) => eval(left).asInstanceOf[Int] * eval(right).asInstanceOf[Int]
    case Divide(left, right) => eval(left).asInstanceOf[Int] / eval(right).asInstanceOf[Int]
    case Paren(e) => eval(e).asInstanceOf[Int]
    case LessThan(left, right) => eval(left).asInstanceOf[Int] < eval(right).asInstanceOf[Int]
    case GreaterThan(left, right) => eval(left).asInstanceOf[Int] > eval(right).asInstanceOf[Int]
    case Equals(left, right) => eval(left).asInstanceOf[Int] == eval(right).asInstanceOf[Int]
  }
}