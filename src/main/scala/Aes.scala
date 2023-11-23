package io.github.greenrecyclebin.cryptopals

import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import scala.collection.immutable.ArraySeq

object Aes:
  private val AesEcbPkcS5PaddingCipher =
    Cipher.getInstance("AES/ECB/PKCS5Padding")

  private val AesEcbNoPaddingCipher = Cipher.getInstance("AES/ECB/NoPadding")

  def randomKey: Array[Byte] =
    scala.util.Random.nextBytes(AesEcbNoPaddingCipher.getBlockSize)

  def ecbEncrypt(key: Array[Byte], bs: Array[Byte]): Array[Byte] =
    ecb(key, bs, Cipher.ENCRYPT_MODE)

  def ecbDecrypt(key: Array[Byte], bs: Array[Byte]): Array[Byte] =
    ecb(key, bs, Cipher.DECRYPT_MODE)

  private def ecb(
      key: Array[Byte],
      bs: Array[Byte],
      mode: Int
  ): Array[Byte] =
    val keySpec = new SecretKeySpec(key, "AES")

    mode match
      case Cipher.ENCRYPT_MODE | Cipher.DECRYPT_MODE =>
        AesEcbPkcS5PaddingCipher.init(mode, keySpec)
      case _ =>
        throw IllegalArgumentException(
          "mode must be either Cipher.ENCRYPT_MODE or Cipher.DECRYPT_MODE."
        )

    AesEcbPkcS5PaddingCipher.doFinal(bs)

  def cbcEncrypt(
      key: Array[Byte],
      iv: Seq[Byte],
      bs: Seq[Byte]
  ): Seq[Byte] =
    val keySpec = new SecretKeySpec(key, "AES")
    AesEcbNoPaddingCipher.init(Cipher.ENCRYPT_MODE, keySpec)

    val blockSize = AesEcbNoPaddingCipher.getBlockSize
    val padded = PKCS7Padding.pad(bs, blockSize)
    val bsc =
      Array.ofDim[Byte](AesEcbNoPaddingCipher.getOutputSize(padded.size))

    padded.grouped(blockSize).zipWithIndex.foldLeft(iv) { (a, bWithIndex) =>
      val (b, i) = bWithIndex
      val xor = a.zip(b).map(_ ^ _).map(_.toByte)

      // Seq#grouped boxes Array[Int] -> Array[java.lang.Integer].
      val c = AesEcbNoPaddingCipher.update(xor.toArray)
      (i * blockSize until (i + 1) * blockSize).zip(c).foreach(bsc(_) = _)

      ArraySeq.from(c)
    }

    ArraySeq.from(bsc)

  def cbcDecrypt(
      key: Array[Byte],
      iv: Seq[Byte],
      bs: Seq[Byte]
  ): Seq[Byte] =
    val keySpec = new SecretKeySpec(key, "AES")
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

  def encryptedWithEcb(bs: Seq[Byte]): Boolean =
    val byteFrequencies = bs
      .grouped(16)
      .iterator
      .to(Iterable)
      .groupMapReduce(identity)(_ => 1)(_ + _)

    byteFrequencies.values.exists(_ > 1)
