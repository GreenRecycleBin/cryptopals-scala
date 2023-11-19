package io.github.greenrecyclebin.cryptopals

import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Paths}
import scala.collection.immutable.ArraySeq

object Set2Challenge10:
  def main(args: Array[String]): Unit =
    /* Encrypt the plaintext. */
    val plaintext = "Hello, World!"
    val bs = plaintext.getBytes(StandardCharsets.ISO_8859_1)

    val key = "YELLOW SUBMARINE"
    val keyBs = key.getBytes(StandardCharsets.ISO_8859_1)
    val iv = ArraySeq.from(Array.ofDim[Byte](16))
    val bsc = Aes.cbcEncrypt(keyBs, iv, ArraySeq.from(bs))

    /* Decrypt the ciphertext. */
    val bsp = Aes.cbcDecrypt(keyBs, iv, bsc)

    println(String(bsp.toArray, StandardCharsets.ISO_8859_1))

    /* Decrypt 10.txt. */
    val s = Files
      .readString(
        Paths.get(
          "/Users/greenrecyclebin/Programming/Scala/Cryptopals/src/main/scala/10.txt"
        )
      )
      .replace("\n", "")
    val bs2 = java.util.Base64.getDecoder.decode(s)
    val bsp2 = Aes.cbcDecrypt(keyBs, iv, ArraySeq.from(bs2))

    println(String(bsp2.toArray, StandardCharsets.ISO_8859_1))
