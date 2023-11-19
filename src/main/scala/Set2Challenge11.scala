package io.github.greenrecyclebin.cryptopals

import javax.crypto.Cipher
import scala.collection.mutable
import scala.util.Random

object Set2Challenge11:
  private val MinRandomBytesSize = 5
  private type EncryptionOracle = Seq[Byte] => Seq[Byte]

  def main(args: Array[String]): Unit =
    (1 to 1000).foreach { _ =>
      val (expectedMode, oracle) = createEncryptionOracle()
      val actualMode = detectEcbOrCbc(oracle)

      assert(actualMode == expectedMode)
    }

  private def detectEcbOrCbc(oracle: EncryptionOracle): Boolean =
    val cipher = Cipher.getInstance("AES/ECB/NoPadding")
    val blockSize = cipher.getBlockSize

    val plaintext =
      Array.ofDim[Byte](2 * blockSize + (blockSize - MinRandomBytesSize))

    val ciphertext = oracle(plaintext)

    Aes.encryptedWithEcb(ciphertext)

  private def createEncryptionOracle(): (Boolean, EncryptionOracle) =
    val ecb = Random.nextBoolean()

    def encryptionOracle(bs: Seq[Byte]): Seq[Byte] =
      val builder = mutable.ArrayBuilder.make[Byte]

      // Returns a random Int in [5, 10].
      def randomBytesSize = Random.nextInt(6) + MinRandomBytesSize

      builder.addAll(Random.nextBytes(randomBytesSize))
      builder.addAll(bs)
      builder.addAll(Random.nextBytes(randomBytesSize))

      val plaintext = builder.result()

      if ecb then Aes.ecbEncrypt(Aes.randomKey, plaintext)
      else Aes.cbcEncrypt(Aes.randomKey, Aes.randomKey, plaintext)

    (ecb, encryptionOracle)
