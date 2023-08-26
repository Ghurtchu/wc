package services

import domain.Command.DefaultCommands
import domain.MultiCountResult

import scala.util.Try

trait LoadInputAndThenCountWords {
  def apply(
    filepath: String,
    loadInput: => Try[String],
  ): Option[MultiCountResult]
}

object LoadInputAndThenCountWords {
  def fromFile: LoadInputAndThenCountWords =
    (filepath, loadInput) =>
      loadInput.map { input =>
        val results = DefaultCommands
          .map(Counter.fromCommand(_).count(input))

        MultiCountResult(results, filepath)
      }.toOption
}
