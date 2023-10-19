package io.github.greenrecyclebin.cryptopals

import RepeatingXorCipher.decrypt

import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Paths}

object Set1Challenge6:
  def main(args: Array[String]): Unit =
    val s1 = "this is a test"
    val s2 = "wokka wokka!!!"
    val distance = HammingDistance(s1, s2)

    println(s"HammingDistance($s1, $s2) = $distance")
    println(distance == 37)

    val bs = Files.readString(Paths.get("/Users/greenrecyclebin/Programming/Scala/Cryptopals/src/main/scala/6.txt")).replace("\n", "")

    println(decrypt(java.util.Base64.getDecoder.decode(bs)))
