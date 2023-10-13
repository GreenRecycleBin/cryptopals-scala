package io.github.greenrecyclebin.cryptopals

object Set1Challenge2:
  def main(args: Array[String]): Unit =
    val hex1 = "1c0111001f010100061a024b53535009181c"
    val hex2 = "686974207468652062756c6c277320657965"
    val xor = Hex.xor(hex1, hex2)

    println(hex1)
    println(hex2)
    println(xor)
    println(xor == "746865206b696420646f6e277420706c6179")
