package io.github.greenrecyclebin.cryptopals

import scala.collection.immutable.ArraySeq
import scala.util.control.Breaks.*

object PKCS7Padding:
  def pad(bs: Seq[Byte], blockSize: Int): Seq[Byte] =
    val it = bs.grouped(blockSize)
    var last: Seq[Byte] = null

    breakable {
      while it.hasNext do
        last = it.next()

        if !it.hasNext then break()
    }

    val paddingSize = blockSize - last.size
    val padding = ArraySeq.fill(paddingSize)(paddingSize.toByte)

    ArraySeq.from(bs.concat(padding))
