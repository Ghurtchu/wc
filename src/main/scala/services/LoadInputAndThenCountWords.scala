package services

import domain.Command.DefaultCommands
import domain.MultiCountResult

import scala.util.Try

trait LoadInputAndThenCountWords {
  def apply(filepath: String, loadInput: => Try[String]): Option[MultiCountResult]
}

object LoadInputAndThenCountWords {
  def fromFile: LoadInputAndThenCountWords = (filepath, input) =>
    input.map { raw =>
      val countResults = DefaultCommands.map(Counter.fromCommand(_).count(raw))

      MultiCountResult(countResults, filepath)
    }.toOption
}
