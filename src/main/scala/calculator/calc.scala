package calculator

import scala.tools.nsc.EvalLoop
import calculator.parser.CalcParser
import calculator.semantics.eval


// Modified by Jean Sung 
// CS 111, External Lab 
object Calculator extends EvalLoop with App {
  override def prompt = "what next? :) : "

  loop { line ⇒
    try {
    CalcParser(line) match {
      case CalcParser.Success(t, _) ⇒ println(eval(t))
      case e: CalcParser.NoSuccess  ⇒ println(e)
      } } catch{
        case mathError: ArithmeticException ⇒ println("Not a valid operation")
      }
      
    }
  }

