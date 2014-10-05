package calculator.parser

import scala.util.parsing.combinator._
import calculator.ir._

object CalcParser extends JavaTokenParsers with PackratParsers {

    // parsing interface
    def apply(s: String): ParseResult[AST] = parseAll(expr, s)

    // expressions
    lazy val expr: PackratParser[Expr] =
      (expr~"+"~expr ^^ {case l~"+"~r => Plus(l,r)}
        | expr~"-"~expr ^^ {case l~"-"~r => Minus(l,r)}
        | expr~"*"~expr ^^ {case l~"*"~r => Times(l,r)}
        | expr~"/"~expr ^^ {case l~"/"~r => Divides(l,r)}
        | "("~expr~")" ^^ {case "("~e~")" => Parens(e)}
        | number ^^ {case n => n})

    // numbers
    def number: Parser[Num] = wholeNumber ^^ {s => Num(s.toInt)}
    
 }
