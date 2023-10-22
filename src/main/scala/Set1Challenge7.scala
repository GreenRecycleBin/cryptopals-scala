package io.github.greenrecyclebin.cryptopals

import RepeatingXorCipher.decrypt

import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Paths}
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

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

    val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")

    val keySpec =
      SecretKeySpec(key.getBytes(StandardCharsets.ISO_8859_1), "AES")

    cipher.init(Cipher.DECRYPT_MODE, keySpec)
    val bs = java.util.Base64.getDecoder.decode(s)
    val plaintext = cipher.doFinal(bs)

    println(String(plaintext, StandardCharsets.ISO_8859_1))
