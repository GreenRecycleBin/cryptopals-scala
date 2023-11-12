package io.github.greenrecyclebin.cryptopals

import scala.collection.immutable.ArraySeq
import scala.util.control.Breaks.*

object PKCS7Padding:
  def pad(bs: Seq[Byte], blockSize: Int): Seq[Byte] =
    val it = bs.grouped(blockSize)
    val last = getLast(it)
    val paddingSize = blockSize - last.size
    val padding = ArraySeq.fill(paddingSize)(paddingSize.toByte)

    ArraySeq.from(bs.concat(padding))

  def unpad(bs: Seq[Byte], blockSize: Int): Seq[Byte] =
    val (it, it2) = bs.grouped(blockSize).duplicate
    val last = getLast(it)

    if last.size < blockSize then return ArraySeq.from(bs)

    val lastByte = last.last

    if last.takeRight(lastByte).forall(_ == lastByte) then
      return it2.toSeq.dropRight(1).flatten.concat(last.dropRight(lastByte))

    bs

  private def getLast(it: Iterator[Seq[Byte]]): Seq[Byte] =
    var last: Seq[Byte] = null

    breakable {
      while it.hasNext do
        last = it.next()

        if !it.hasNext then break()
    }

    last
