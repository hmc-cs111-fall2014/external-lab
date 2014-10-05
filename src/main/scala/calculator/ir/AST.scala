package calculator.ir

/**
 * -----------
 * Grammar
 * -----------
 * 
 *                   n ∈ 𝒵 
 *
 *       e ∈ Expr ::= e + t | e - t | t | c
 *       t ∈ Term ::= t * f | t / f | f
 *       f ∈ Fact ::= n | ( e )
 *       c ∈ Comp ::= e < e | e > e | e == e
 *  
 */

sealed abstract class AST
sealed abstract class Expr extends AST

case class Num(n: Int) extends Expr
case class Plus(left: Expr, right: Expr) extends Expr
case class Minus(left: Expr, right: Expr) extends Expr
case class Times(left: Expr, right: Expr) extends Expr
case class Divide(left: Expr, right: Expr) extends Expr
case class Paren(expr: Expr) extends Expr
case class LessThan(left: Expr, right: Expr) extends Expr
case class GreaterThan(left: Expr, right: Expr) extends Expr
case class Equals(left: Expr, right: Expr) extends Expr
