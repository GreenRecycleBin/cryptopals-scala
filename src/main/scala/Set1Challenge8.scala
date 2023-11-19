package io.github.greenrecyclebin.cryptopals

import scala.io.Source
import scala.util.Using

object Set1Challenge8:
  def main(args: Array[String]): Unit =
    Using(
      Source.fromFile(
        "/Users/greenrecyclebin/Programming/Scala/Cryptopals/src/main/scala/8.txt"
      )
    ) { source =>
      val linesEncryptedWithEcb = source.getLines.zipWithIndex
        .map((ciphertext, i) => (i, ciphertext, encryptedWithEcb(ciphertext)))
        .filter(_._3)

      println(linesEncryptedWithEcb.mkString("\n"))
    }.get

  private def encryptedWithEcb(hex: String) =
    val bs = Hex.toBytes(hex)

    Aes.encryptedWithEcb(bs)
