package io.github.greenrecyclebin.cryptopals

object Hex:
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
