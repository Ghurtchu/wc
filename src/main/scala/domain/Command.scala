package domain

import scala.PartialFunction.condOpt

sealed trait Command {
  override def toString: String =
    getClass.getSimpleName.init.toLowerCase
}

object Command {
  case object Byte      extends Command
  case object Character extends Command
  case object Word      extends Command
  case object Line      extends Command

  val DefaultCommands =
    List(Command.Line, Command.Word, Command.Byte)

  def fromString: String => Option[Command] =
    condOpt(_) {
      case "m" => Character
      case "c" => Byte
      case "w" => Word
      case "l" => Line
    }
}
