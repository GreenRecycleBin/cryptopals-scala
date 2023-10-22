package io.github.greenrecyclebin.cryptopals

import RepeatingXorCipher.decrypt

import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Paths}
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

object Set1Challenge7:
  def main(args: Array[String]): Unit =
    val key = "YELLOW SUBMARINE"
    val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
    val keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.ISO_8859_1), "AES")
    cipher.init(Cipher.DECRYPT_MODE, keySpec)

    val bs = Files.readString(Paths.get("/Users/greenrecyclebin/Programming/Scala/Cryptopals/src/main/scala/7.txt")).replace("\n", "")
    val plaintext = cipher.doFinal(java.util.Base64.getDecoder.decode(bs))

    println(String(plaintext, StandardCharsets.ISO_8859_1))
