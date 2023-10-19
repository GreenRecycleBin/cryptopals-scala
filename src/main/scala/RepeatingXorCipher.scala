package io.github.greenrecyclebin.cryptopals

import java.nio.charset.StandardCharsets

def cycle[T](seq: Seq[T]): LazyList[T] =
  def xs: LazyList[T] = seq.to(LazyList) #::: xs

  xs

object RepeatingXorCipher:
  def encryptHex(plaintext: String, keys: String): String =
    val repeatingKeys = cycle(keys.map(_.toByte))

    Hex.fromBytes(
      plaintext
        .getBytes(StandardCharsets.ISO_8859_1)
        .zip(repeatingKeys)
        .map(_ ^ _)
        .map(_.toByte)
    )

  def decrypt(bs: Seq[Byte]): (String, String) =
    val keySizes = (2 to 40)
      .map(keySize =>
        (keySize, HammingDistance.averageHammingDistance(bs, keySize))
      )
      .sortBy(_._2)
      .map(_._1)

    val keys = keySizes
      .take(3)
      .map(keySize =>
        val transposedBs = bs.grouped(keySize).map(_.toArray).toArray.transpose

        transposedBs.map { b =>
          val plaintextBytes = SingleByteXorCipher
            .decrypt(b)
            .sortBy(_.score)
            .collectFirst(identity)
            .get

          plaintextBytes.key
        }
      )

    val (key, plaintext, _) = keys
      .map(key => (key, decrypt(bs, key)))
      .map((key, plaintext) => (key, plaintext, TextScorer.score(plaintext)))
      .sortBy(_._3)
      .collectFirst(identity)
      .get

    (String(key, StandardCharsets.ISO_8859_1), plaintext)

  private def decrypt(bs: Seq[Byte], key: Seq[Byte]): String =
    val repeatingKeys = cycle(key)

    String(
      bs.zip(repeatingKeys).map(_ ^ _).map(_.toByte).toArray,
      StandardCharsets.ISO_8859_1
    )
