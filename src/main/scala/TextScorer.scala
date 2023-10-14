package io.github.greenrecyclebin.cryptopals

object TextScorer {
  private val CharFrequencies = List(
    ' ', 'E', 'S', 'I', 'A', 'R', 'N', 'T', 'O',
    'L', 'C', 'D', 'U', 'G', 'P', 'M', 'H', 'B',
    'Y', 'F', 'V', 'K', 'W', 'Z', 'X', 'J', 'Q'
  )

  private val CharToScore = CharFrequencies.zipWithIndex.toMap

  def score(s: String): Int = score(s, c => CharToScore getOrElse(Character.toUpperCase(c), CharFrequencies.length))

  def score(s: String, f: Char => Int): Int = s.map(f(_)).sum
}
