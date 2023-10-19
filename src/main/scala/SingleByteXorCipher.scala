package io.github.greenrecyclebin.cryptopals

import java.nio.charset.StandardCharsets
import scala.collection.{IndexedSeqView, SeqView}

object SingleByteXorCipher:
  def decryptHex(ciphertext: String): SeqView[Plaintext] =
    (0 to Byte.MaxValue).view.map(_.toByte).map(b => {
      val plaintext = Hex.xorToChars(ciphertext, b)

      Plaintext(b, plaintext, TextScorer.score(plaintext))
    })

  def decrypt(bs: Seq[Byte]): SeqView[PlaintextBytes] =
    (0 to Byte.MaxValue).view.map(_.toByte).map(b => {
      val plaintext = bs.map(_ ^ b).map(_.toByte)

      PlaintextBytes(b, plaintext, TextScorer.score(String(plaintext.toArray, StandardCharsets.ISO_8859_1)))
    })

  case class Plaintext(key: Byte, plaintext: String, score: Int)

  case class PlaintextBytes(key: Byte, plaintextBytes: Seq[Byte], score: Int)
