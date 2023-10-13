package io.github.greenrecyclebin.cryptopals

import java.nio.charset.StandardCharsets

object Set1Challenge1:
  def main(args: Array[String]): Unit =
    val in = "49276d206b696c6c696e6720796f757220627261696e206c696b65206120706f69736f6e6f7573206d757368726f6f6d"
    val dst = Base64.getEncoder.encode(Hex.toBytes(in))
    val out = String(dst, StandardCharsets.ISO_8859_1)

    println(in)
    println(out)
    println(out == "SSdtIGtpbGxpbmcgeW91ciBicmFpbiBsaWtlIGEgcG9pc29ub3VzIG11c2hyb29t")
