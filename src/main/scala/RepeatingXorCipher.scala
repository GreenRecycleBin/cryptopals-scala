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
