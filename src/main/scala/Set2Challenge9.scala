package io.github.greenrecyclebin.cryptopals

import RepeatingXorCipher.decrypt

import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Paths}
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import scala.io.Source
import scala.util.Using

object Set2Challenge9:
  def main(args: Array[String]): Unit =
    val plaintext = "YELLOW SUBMARINE"

    val paddedPlaintext =
      PKCS7Padding.pad(plaintext.getBytes(StandardCharsets.ISO_8859_1), 20)

    println(plaintext)
    println(paddedPlaintext)
