package io.github.greenrecyclebin.cryptopals

import scala.io.Source
import scala.util.{Failure, Success, Using}

object Set1Challenge5:
  def main(args: Array[String]): Unit =
    val plaintext =
      """Burning 'em, if you ain't quick and nimble
        |I go crazy when I hear a cymbal""".stripMargin

    val ciphertext = RepeatingXorCipher.encryptHex(plaintext, "ICE")

    println(plaintext)
    println(ciphertext)

    println(
      ciphertext == "0b3637272a2b2e63622c2e69692a23693a2a3c6324202d623d63343c2a26226324272765272a282b2f20430a652e2c652a3124333a653e2b2027630c692b20283165286326302e27282f"
    )
