package io.github.greenrecyclebin.cryptopals

import scala.util.control.Breaks.*

object PKCSPadding {
  def PKCS7Padding(bs: Seq[Byte], blockSize: Int): Seq[Byte] =
    val iterator = bs.grouped(blockSize)
    var last: Seq[Byte] = null

    breakable {
      while iterator.hasNext do
        last = iterator.next()

        if !iterator.hasNext then break()
    }

    val paddingSize = blockSize - last.size
    val padding = Seq.fill(paddingSize)(paddingSize.toByte)

    bs.concat(padding)
}
