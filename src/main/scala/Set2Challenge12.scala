package io.github.greenrecyclebin.cryptopals

import javax.crypto.Cipher
import scala.collection.mutable
import scala.util.Random

object Set2Challenge12:
  private val MinRandomBytesSize = 5
  private type EncryptionOracle = Seq[Byte] => Seq[Byte]

  def main(args: Array[String]): Unit =
    (1 to 1000).foreach { _ =>
      val (expectedMode, oracle) = createEncryptionOracle()
      val actualMode = detectEcbOrCbc(oracle)

      assert(actualMode == expectedMode)
    }

  println(foo(List(-2, -1, 0, 1, 2)) == foo2(List(-2, -1, 0, 1, 2)))

  def foo3(xs: List[Int]) =
    xs.map: x =>
        x + 1
      .filter: x =>
        x > 0

  def foo4(a: List[Int]) =
    a.map: x =>
         x + 1
       .filter: x =>
        x > 0

  def foo(xs: List[Int]) =
    xs.map: x =>
        x + 1
      .toSet.filter: x =>
          x > 0
       .toList

  def foo2(xs: List[Int]) =
    xs
      .map: x =>
        x + 1
      .toSet
      .filter: x =>
        x > 0
      .toList

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
