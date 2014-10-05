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
        | expr~"<"~expr ^^ {case l~"<"~r => lt(l,r)}
        | expr~">"~expr ^^ {case l~">"~r => gt(l,r)}
        | expr~"="~expr ^^ {case l~"="~r => l |=| r}
        | expr~"≠"~expr ^^ {case l~"≠"~r => l |≠| r}
        | term)

    // terms
    lazy val term: PackratParser[Expr] =
      (term~"*"~fact ^^ {case l~"*"~r => Times(l,r)}
        | term~"/"~fact ^^ {case l~"/"~r => Divides(l,r)}
        | fact )

    // factors
    lazy val fact: PackratParser[Expr] =
      (fact~"^"~fact ^^ {case l~"^"~r => power(l,r)}
        | "("~expr~")" ^^ {case "("~e~")" => Parens(e)}
        | number ^^ {case n => n})

    // numbers
    def number: Parser[Num] = wholeNumber ^^ {s => Num(s.toInt)}
    
 }
