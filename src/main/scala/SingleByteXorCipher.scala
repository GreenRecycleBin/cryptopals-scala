package io.github.greenrecyclebin.cryptopals

import scala.collection.{IndexedSeqView, SeqView}

object SingleByteXorCipher:
  def decryptHex(ciphertext: String): Seq[Plaintext] =
    (0 to Byte.MaxValue).map(_.toByte).map { b =>
      val plaintext = Hex.xorToChars(ciphertext, b)

      Plaintext(b, plaintext, TextScorer.score(plaintext))
    }

  case class Plaintext(key: Byte, plaintext: String, score: Int)
