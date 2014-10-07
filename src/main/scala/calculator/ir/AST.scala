package calculator.ir

/**
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
sealed abstract class Term extends Expr
sealed abstract class Fact extends Term

case class Num(n: Int) extends Fact
case class Parens(e: Expr) extends Fact
case class Plus(left: Expr, right: Term) extends Expr
case class Minus(left: Expr, right: Term) extends Expr
case class Mult(left: Term, right: Fact) extends Term
case class Div(left: Term, right: Fact) extends Term
