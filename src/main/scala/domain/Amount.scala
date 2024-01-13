package domain

final case class Amount private (value: BigInt) extends AnyVal {
  override def toString: String = value.toString
}

object Amount {
  def fromBigInt(n: BigInt): Option[Amount] =
    Option.when(n >= 0)(new Amount(n))

  def empty: Amount = Amount(0)
}
