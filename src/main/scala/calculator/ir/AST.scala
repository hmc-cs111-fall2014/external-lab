package calculator.ir

/**
 * -----------
 * Grammar
 * -----------
 * 
 *                   n ∈ 𝒵 
 *       c ∈ Comp ::= e < e | e > e | e = e | e
 *       e ∈ Expr ::= e + t | e - t | t
 *       t ∈ Term ::= t * f | t / f | f
 *       f ∈ Fact ::= n | ( e )
 *  
 */

sealed abstract class AST
sealed abstract class Expr extends AST
// sealed abstract class Term extends AST
// sealed abstract class Fact extends AST


case class Num(n: Int) extends Expr
case class Plus(left: Expr, right: Expr) extends Expr
case class Minus(left: Expr, right: Expr) extends Expr

case class Times(left: Expr, right: Expr) extends Expr
case class Divide(left: Expr, right: Expr) extends Expr

case class Paren(e: Expr) extends Expr

case class LessThan(left: Expr, right: Expr) extends Expr
case class GreaterThan(left: Expr, right: Expr) extends Expr
case class Equals(left: Expr, right: Expr) extends Expr

