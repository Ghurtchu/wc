package domain

sealed trait Command {
  override def toString: String =
    getClass.getSimpleName.init.toLowerCase
}

object Command {

  import scala.PartialFunction.condOpt

  val DefaultCommands = List(Line, Word, Byte)

  case object Byte      extends Command
  case object Character extends Command
  case object Word      extends Command
  case object Line      extends Command

  def fromString: String => Option[Command] = condOpt(_) {
    case "m" => Character
    case "c" => Byte
    case "w" => Word
    case "l" => Line
  }
}
