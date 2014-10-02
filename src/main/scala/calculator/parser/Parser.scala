package calculator.parser

import scala.util.parsing.combinator._
import calculator.ir._

object CalcParser extends JavaTokenParsers with PackratParsers {

    // parsing interface
    def apply(s: String): ParseResult[AST] = parseAll(comp, s)

    // comparisons
    lazy val comp: PackratParser[Expr] = 
      (   expr~"<"~expr ^^ {case l~"<"~r => l |<| r}
        | expr~">"~expr ^^ {case l~">"~r => l |>| r}
        | expr~"="~expr ^^ {case l~"="~r => l |=| r}
        | expr )

    // expressions
    lazy val expr: PackratParser[Expr] = 
      (   expr~"+"~term ^^ {case l~"+"~r => l |+| r}
        | expr~"-"~term ^^ {case l~"-"~r => l |-| r}
        | term )

    // terms
    lazy val term: PackratParser[Expr] = 
      (   term~"*"~fact ^^ {case l~"*"~r => l |*| r}
        | term~"/"~fact ^^ {case l~"/"~r => l |/| r}
        | fact )

    // factors
    lazy val fact: PackratParser[Expr] =
      (   "("~expr~")" ^^ {case "("~e~")" => Paren(e)}
        | number )

      
    // numbers
    def number: Parser[Num] = wholeNumber ^^ {s => Num(s.toInt)}
    
 }
