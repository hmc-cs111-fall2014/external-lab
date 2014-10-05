package calculator.parser

import scala.util.parsing.combinator._
import calculator.ir._

object CalcParser extends JavaTokenParsers with PackratParsers {

    // parsing interface
    def apply(s: String): ParseResult[AST] = parseAll(expr, s)

    // expressions
    lazy val expr: PackratParser[Expr] = 
      (   expr~"+"~term ^^ {case l~"+"~r => l |+| r}
        | expr~"-"~term ^^ {case l~"-"~r => l |-| r}
        | comp
        | term )

    lazy val term: PackratParser[Expr] =
      (   term~"*"~fact ^^ {case l~"*"~r => l |*| r}
        | term~"/"~fact ^^ {case l~"/"~r => l |/| r}
        | fact )

    // factors
    lazy val fact: PackratParser[Expr] =
      (   number
        | "("~expr~")" ^^ {case "("~e~")" => Paren(e)} )


    // comparators
    lazy val comp: PackratParser[Expr] =
      (   expr~"<"~expr ^^ {case l~"<"~r => l |<| r}
        | expr~">"~expr ^^ {case l~">"~r => l |>| r}
        | expr~"="~expr ^^ {case l~"="~r => l |=| r} )


  // numbers
    def number: Parser[Num] = wholeNumber ^^ {s => Num(s.toInt)}
    
 }
