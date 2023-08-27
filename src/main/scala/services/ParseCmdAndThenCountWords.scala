package services

import domain._
import ParseCmdAndThenCountWords._
import services.ParseCmdAndThenCountWords.Error._

import java.nio.file.NoSuchFileException
import scala.util.Try

trait ParseCmdAndThenCountWords {
  def apply(
    cmd: String,
    loadInput: => Try[String],
  ): Either[Error, CountResult]
}

object ParseCmdAndThenCountWords {

  sealed trait Error {
    def msg: String
  }

  object Error {
    final case class UnknownCommand(cmd: String) extends Error {
      override def msg: String =
        s"unknown command: $cmd"
    }

    final case class FileNotFound(filepath: String) extends Error {
      override def msg: String =
        s"could not find the file: $filepath"
    }

    case object Unknown extends Error {
      override def msg: String =
        "unknown error"
    }
  }

  def fromStdIn: ParseCmdAndThenCountWords =
    of(_ => Unknown)

  def fromFile(filepath: String): ParseCmdAndThenCountWords =
    of {
      case _: NoSuchFileException => FileNotFound(filepath)
      case _                      => Unknown
    }

  private def of(mapError: Throwable => Error): ParseCmdAndThenCountWords =
    (cmd, loadInput) =>
      for {
        cmd   <- parseCmd(cmd)
        input <- loadInput.toEither.left.map(mapError)
      } yield countWords(cmd, input)

  private def parseCmd(cmd: String): Either[UnknownCommand, Command] =
    Command
      .fromString(cmd)
      .toRight(UnknownCommand(cmd))

  private def countWords(cmd: Command, input: String): CountResult =
    Counter
      .fromCommand(cmd)
      .count(input)
}
