package io.github.greenrecyclebin.cryptopals

import scala.io.Source
import scala.util.{Failure, Success, Using}

object Set1Challenge4:
  def main(args: Array[String]): Unit =
    Using(Source.fromFile("/Users/greenrecyclebin/Programming/Scala/Cryptopals/src/main/scala/4.txt")) { source =>
      val (ciphertext, plaintext) = source.getLines.map(ciphertext =>
        val plaintext = SingleByteXorCipher.decryptHex(ciphertext).sortBy(_.score).collectFirst(identity).get

        (ciphertext, plaintext)
      ).toList.sortBy(_._2.score).collectFirst(identity).get

      println(ciphertext)
      println(plaintext)
    }.get
