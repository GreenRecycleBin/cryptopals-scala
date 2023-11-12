package io.github.greenrecyclebin.cryptopals

import RepeatingXorCipher.decrypt

import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Paths}
import javax.crypto.Cipher
import javax.crypto.spec.{IvParameterSpec, SecretKeySpec}
import scala.collection.immutable.ArraySeq
import scala.collection.{IndexedSeqView, mutable}

object Set2Challenge10:
  def main(args: Array[String]): Unit =
    /* Encrypt the plaintext. */
    val plaintext = "Hello, World!"
    val bs = plaintext.getBytes(StandardCharsets.ISO_8859_1)

    val key = "YELLOW SUBMARINE"
    val keyBs = key.getBytes(StandardCharsets.ISO_8859_1)
    val iv = ArraySeq.from(Array.ofDim[Byte](16))
    val bsc = aesCbcEncrypt(keyBs, iv, ArraySeq.from(bs))

    /* Decrypt the ciphertext. */
    val bsp = aesCbcDecrypt(keyBs, iv, bsc)

    println(String(bsp.toArray, StandardCharsets.ISO_8859_1))

    /* Decrypt 10.txt. */
    val s = Files
      .readString(
        Paths.get(
          "/Users/greenrecyclebin/Programming/Scala/Cryptopals/src/main/scala/10.txt"
        )
      )
      .replace("\n", "")
    val bs2 = java.util.Base64.getDecoder.decode(s)
    val bsp2 = aesCbcDecrypt(keyBs, iv, ArraySeq.from(bs2))

    println(String(bsp2.toArray, StandardCharsets.ISO_8859_1))

  private def aesCbcEncrypt(
      key: Array[Byte],
      iv: Seq[Byte],
      bs: Seq[Byte]
  ): Seq[Byte] =
    val keySpec = SecretKeySpec(key, "AES")
    val cipher = Cipher.getInstance("AES/ECB/NoPadding")
    cipher.init(Cipher.ENCRYPT_MODE, keySpec)

    val blockSize = cipher.getBlockSize
    val padded = PKCS7Padding.pad(bs, blockSize)
    val bsc = Array.ofDim[Byte](cipher.getOutputSize(padded.size))

    padded.grouped(blockSize).zipWithIndex.foldLeft(iv) { (a, bWithIndex) =>
      val (b, i) = bWithIndex
      val xor = a.zip(b).map(_ ^ _).map(_.toByte)

      // Seq#grouped boxes Array[Int] -> Array[java.lang.Integer].
      val c = cipher.update(xor.toArray)
      (i * blockSize until (i + 1) * blockSize).zip(c).foreach(bsc(_) = _)

      ArraySeq.from(c)
    }

    ArraySeq.from(bsc)

  private def aesCbcDecrypt(
      key: Array[Byte],
      iv: Seq[Byte],
      bs: Seq[Byte]
  ): Seq[Byte] =
    val keySpec = SecretKeySpec(key, "AES")
    val cipher = Cipher.getInstance("AES/ECB/NoPadding")
    cipher.init(Cipher.DECRYPT_MODE, keySpec)

    val blockSize = cipher.getBlockSize
    val bsp = Array.ofDim[Byte](cipher.getOutputSize(bs.size))

    /* Decrypt 10.txt. */
    bs.grouped(blockSize).zipWithIndex.foldLeft(iv) { (a, bWithIndex) =>
      val (b, i) = bWithIndex

      // Seq#grouped boxes Array[Int] -> Array[java.lang.Integer].
      val xor = cipher.update(b.toArray)
      val p = a.zip(xor).map(_ ^ _).map(_.toByte)
      (i * blockSize until (i + 1) * blockSize).zip(p).foreach(bsp(_) = _)

      b
    }

    PKCS7Padding.unpad(bsp, blockSize)
