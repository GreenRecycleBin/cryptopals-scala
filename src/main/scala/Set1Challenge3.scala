package io.github.greenrecyclebin.cryptopals

object Set1Challenge3:
  def main(args: Array[String]): Unit =
    val ciphertext =
      "1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736"

    val plaintexts =
      SingleByteXorCipher.decryptHex(ciphertext).sortBy(_.score).toList

    println(ciphertext)
    println(plaintexts.mkString("\n"))
