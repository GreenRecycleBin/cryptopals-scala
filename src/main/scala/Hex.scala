package io.github.greenrecyclebin.cryptopals

object Hex:
  def xorToChars(hex: String, b0: Byte): String =
    val bytes = xorToBytes(hex, b0)

    bytes.map(_.toChar).mkString

  private def xorToBytes(hex: String, b0: Byte): Seq[Byte] =
    val bs = toBytes(hex)

    bs.map(_ ^ b0).map(_.toByte)

  def xor(hex1: String, hex2: String): String =
    val bs1 = toBytes(hex1)
    val bs2 = toBytes(hex2)

    if bs1.length == bs2.length then
      xor(bs1, bs2)

      return fromBytes(bs2)

    val maxLen = Math.max(bs1.length, bs2.length)

    val (a, b) =
      if bs1.length < bs2.length then (bs2, Array.copyOf(bs1, maxLen))
      else (bs1, Array.copyOf(bs2, maxLen))

    xor(a, b)

    fromBytes(b)

  def toBytes(hex: String): Array[Byte] =
    if hex.length % 2 != 0 then
      throw IllegalArgumentException("The number of characters must be even.")

    val out = Array.ofDim[Byte](hex.length / 2)
    var i = 0

    while i < hex.length do
      var bits = Character.digit(hex(i), 16) << 4
      i += 1

      bits |= Character.digit(hex(i), 16)
      i += 1

      out(i / 2 - 1) = bits.toByte

    out

  private def fromBytes(bs: Seq[Byte]): String =
    val hex = StringBuilder()

    for i <- bs.indices
    do
      hex += Character.forDigit((bs(i) >> 4) & 0xf, 16)
      hex += Character.forDigit(bs(i) & 0xf, 16)

    hex.result()

  // Precondition: a.len == b.len.
  // b is the output parameter and will be overwritten.
  private def xor(a: Array[Byte], b: Array[Byte]): Unit =
    if a.length != b.length then
      throw IllegalArgumentException("Byte arrays must have the same length.")

    for i <- a.indices
    do b(i) = ((a(i) ^ b(i)) & 0xff).toByte
