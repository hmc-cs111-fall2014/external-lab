package calculator.ir

/**
 *
 * Modified by Sarah Gilkinson
 *
 * -----------
 * Grammar
 * -----------
 * 
 *                   n ∈ 𝒵 
 * 
 *       e ∈ Expr ::= e + t | e - t | t
 *       t ∈ Term ::= t * f | t / f | f
 *       f ∈ Fact ::= n | ( e )
 *  
 */

sealed abstract class AST
sealed abstract class Expr extends AST

case class Plus(left: Expr, right: Expr) extends Expr
case class Minus(left: Expr, right: Expr) extends Expr
case class Times(left: Expr, right: Expr) extends Expr
case class Divides(left: Expr, right: Expr) extends Expr
case class Num(n: Int) extends Expr
case class Parens(exp: Expr) extends Expr
case class lt(left: Expr, right: Expr) extends Expr
case class gt(left: Expr, right: Expr) extends Expr
