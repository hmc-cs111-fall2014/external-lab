package calculator.parser

/*
 * Modified by Sarah Gilkinson
 */

import scala.util.parsing.combinator._
import calculator.ir._

object CalcParser extends JavaTokenParsers with PackratParsers {

    // parsing interface
    def apply(s: String): ParseResult[AST] = parseAll(expr, s)

    // expressions
    lazy val expr: PackratParser[Expr] =
      (expr~"+"~term ^^ {case l~"+"~r => Plus(l,r)}
        | expr~"-"~term ^^ {case l~"-"~r => Minus(l,r)}
        | term)

    lazy val term: PackratParser[Expr] =
      (term~"*"~fact ^^ {case l~"*"~r => Times(l,r)}
        | term~"/"~fact ^^ {case l~"/"~r => Divides(l,r)}
        | fact )

    lazy val fact: PackratParser[Expr] =
      ("("~expr~")" ^^ {case "("~e~")" => Parens(e)}
        | number ^^ {case n => n})

    // numbers
    def number: Parser[Num] = wholeNumber ^^ {s => Num(s.toInt)}
    
 }
