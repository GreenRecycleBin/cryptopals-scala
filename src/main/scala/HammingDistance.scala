package io.github.greenrecyclebin.cryptopals

import java.nio.charset.StandardCharsets

object HammingDistance:
  def apply(s1: String, s2: String): Int =
    val bs1 = s1.getBytes(StandardCharsets.ISO_8859_1)
    val bs2 = s2.getBytes(StandardCharsets.ISO_8859_1)

    apply(bs1, bs2)

  def apply(bs1: Seq[Byte], bs2: Seq[Byte]): Int =
    val minLen = Math.min(bs1.length, bs2.length)
    val distance = bs1.zip(bs2).map((b1, b2) => Integer.bitCount(b1 ^ b2)).sum
    val longerArray = if bs1.length < bs2.length then bs2 else bs1

    val extraOneBits =
      longerArray.drop(minLen).foldLeft(0)(_ + Integer.bitCount(_))

    distance + Math.max(bs1.length, bs2.length) - minLen

  def averageHammingDistance(bs: Seq[Byte], keySize: Int): Double =
    val groups = bs.grouped(keySize).take(5)

    val distances = groups.sliding(2).withPartial(false).map { pair =>
      val bs1 = pair.head
      val bs2 = pair.last

      HammingDistance(bs1, bs2) / keySize
    }

    var sum = 0d
    var count = 0

    while distances.hasNext do
      sum += distances.next()
      count += 1

    if count > 0 then sum / count else Int.MaxValue
