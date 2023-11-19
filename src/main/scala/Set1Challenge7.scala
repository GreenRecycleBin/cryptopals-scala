package io.github.greenrecyclebin.cryptopals

import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Paths}

object Set1Challenge7:
  def main(args: Array[String]): Unit =
    val key = "YELLOW SUBMARINE"

    val s = Files
      .readString(
        Paths.get(
          "/Users/greenrecyclebin/Programming/Scala/Cryptopals/src/main/scala/7.txt"
        ),
        StandardCharsets.ISO_8859_1
      )
      .replace("\n", "")

    val bs = java.util.Base64.getDecoder.decode(s)

    val plaintext =
      Aes.ecbDecrypt(key.getBytes(StandardCharsets.ISO_8859_1), bs)

    println(String(plaintext, StandardCharsets.ISO_8859_1))
