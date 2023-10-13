package io.github.greenrecyclebin.cryptopals

import java.nio.charset.{Charset, StandardCharsets}

object Base64:
  class Encoder:
    /** This array is a lookup table that translates 6-bit positive integer
      * index values into their "Base64 Alphabet" equivalents as specified in
      * "Table 1: The Base64 Alphabet" of RFC 2045 (and RFC 4648).
      */
    private val Base64 = Array[Byte](
      'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
      'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd',
      'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
      't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7',
      '8', '9', '+', '/'
    )

    def encode(src: Array[Byte]): Array[Byte] =
      val len = dstLength(src.length)
      val dst = Array.ofDim[Byte](len)

      encode0(src, 0, src.length, dst)

      dst

    private def dstLength(srclen: Int) =
      try Math.multiplyExact(4, Math.addExact(srclen, 2) / 3)
      catch case e: ArithmeticException => -1

    private def encode0(
        src: Array[Byte],
        off: Int,
        end: Int,
        dst: Array[Byte]
    ) =
      val base64 = Base64
      var sp = off
      val slen = (end - off) / 3 * 3
      val sl = off + slen
      var dp = 0

      while sp < sl do
        encodeBlock(src, sp, sl, dst, dp)

        val dlen = (sl - sp) / 3 * 4
        dp += dlen
        sp = sl

      // There are 1 or 2 leftover bytes.
      if sp < end then
        val b0 = src(sp) & 0xff
        sp += 1

        dst(dp) = base64((b0 >> 2) & 0x3f)
        dp += 1

        if sp == end then
          dst(dp) = base64((b0 << 4) & 0x3f)
          dp += 1

          dst(dp) = '='
          dp += 1

          dst(dp) = '='
          dp += 1
        else
          val b1 = src(sp) & 0xff
          sp += 1

          dst(dp) = base64((b0 << 4) & 0x3f | (b1 >> 2))
          dp += 1

          dst(dp) = base64((b1 << 2) & 0x3f)
          dp += 1

          dst(dp) = '='
          dp += 1

      dp

    private def encodeBlock(
        src: Array[Byte],
        sp: Int,
        sl: Int,
        dst: Array[Byte],
        dp: Int
    ): Unit =
      val base64 = Base64

      var dp0 = dp
      var sp0 = sp

      while sp0 < sl do
        var bits = (src(sp0) & 0xff) << 16
        sp0 += 1

        bits |= (src(sp0) & 0xff) << 8
        sp0 += 1

        bits |= (src(sp0) & 0xff)
        sp0 += 1

        dst(dp0) = base64((bits >>> 18) & 0x3f)
        dp0 += 1

        dst(dp0) = base64((bits >>> 12) & 0x3f)
        dp0 += 1

        dst(dp0) = base64((bits >>> 6) & 0x3f)
        dp0 += 1

        dst(dp0) = base64(bits & 0x3f)
        dp0 += 1

  def getEncoder: Encoder = Encoder()
