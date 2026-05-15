package com.galaxy.merchantsGuide.enumeration

import com.galaxy.merchantsGuide.exception.ValidationException

/**
  * Enumeration class that describes the mapping between roman numeral symbols and it's corresponding integer values.
  */
object RomanNumerals extends Enumeration {
  type RomanNumeral = Value

  val I = Value(1)
  val V = Value(5)
  val X = Value(10)
  val L = Value(50)
  val C = Value(100)
  val D = Value(500)
  val M = Value(1000)

  /**
    * Transforms a roman number into an integer.
    * @param numbers to be transformed.
    * @return integer value for the roman number.
    */
  def toInt(numbers: List[RomanNumeral]): Int = {
    // Method private to .toInt
    @scala.annotation.tailrec
    def sum(nums: List[RomanNumeral], acc: Int): Int = {
      nums match {
        case n if n.isEmpty => acc
        case n if n.length == 1 => sum(n.tail, acc + n.head.id)
        case n if n.head.id < n(1).id => sum(n.tail.tail, acc + n(1).id - n.head.id)
        case n if n.head.id >= n(1).id => sum(n.tail, acc + n.head.id)
      }
    }

    if (validateRepetitions(numbers) && validateFourTimes(numbers) && validateWrongSeparation(numbers) &&
      validateWrongSubtraction(numbers) && numbers.nonEmpty) {
      sum(numbers, 0)
    } else throw ValidationException(s"${numbers.toString} is not a valid roman numeral.")
  }

  /**
    * "D", "L", and "V" can never be repeated. "I", "X", "C" and "M" can never be repeated more than 4 times.
    * @param numbers Roman Numerals to be validated.
    * @return true for a valid number, else false.
    */
  protected[enumeration] def validateRepetitions(numbers: List[RomanNumeral]): Boolean = {
    numbers.count(_ == RomanNumerals.D) <= 1 &&
      numbers.count(_ == RomanNumerals.L) <= 1 &&
      numbers.count(_ == RomanNumerals.V) <= 1 &&
      numbers.count(_ == RomanNumerals.I) <= 4 &&
      numbers.count(_ == RomanNumerals.X) <= 4 &&
      numbers.count(_ == RomanNumerals.C) <= 4 &&
      numbers.count(_ == RomanNumerals.M) <= 4
  }

  /**
    * The symbols "I", "X", "C", and "M" can be repeated no more than three times in succession.
 *
    * @param numbers Roman Numerals to be validated.
    * @return true for a valid number, else false.
    */
  @scala.annotation.tailrec
  // TODO Cambiar el nombre de este método por uno mas descriptivo.
  protected[enumeration] def validateFourTimes(numbers: List[RomanNumeral]): Boolean = {
    if (numbers.length <= 3) true else {
      val firstFour = numbers.take(4)
      if (firstFour.distinct.length == 1) false else validateFourTimes(numbers.tail)
    }
  }

  /**
    * I, X, C and M may appear four times if the third and fourth are separated by a smaller value, such as XXXIX.
    *
    * @param numbers Roman Numerals to be validated.
    * @return true for a valid number, else false.
    */
  @scala.annotation.tailrec
  protected[enumeration] def validateWrongSeparation(numbers: List[RomanNumeral]): Boolean = {
    if (numbers.length > 4) {
      val firstFiveElements = numbers.take(5)
      val charFrequency = firstFiveElements.count(_ == firstFiveElements.head)

      if (charFrequency == 4 && firstFiveElements.head.id <= firstFiveElements(3).id) false
      else validateWrongSeparation(numbers.tail)
    } else true
  }

  /**
    * "I" can be subtracted from "V" and "X" only. "X" can be subtracted from "L" and "C" only. "C" can be subtracted
    * from "D" and "M" only. "V", "L", and "D" can never be subtracted.
    *
    * @param numbers Roman Numerals to be validated.
    * @return true for a valid number, else false.
    */
  @scala.annotation.tailrec
  def validateWrongSubtraction(numbers: List[RomanNumeral]): Boolean = {
    if (numbers.length <= 1) true else {
      if (numbers.head.id < numbers(1).id &&
        (numbers.head == RomanNumerals.V || numbers.head == RomanNumerals.L || numbers.head == RomanNumerals.D ||
          (numbers.head == RomanNumerals.I && (numbers(1) != RomanNumerals.V && numbers(1) != RomanNumerals.X)) ||
          (numbers.head == RomanNumerals.X && (numbers(1) != RomanNumerals.L && numbers(1) != RomanNumerals.C)) ||
          (numbers.head == RomanNumerals.C && (numbers(1) != RomanNumerals.D && numbers(1) != RomanNumerals.M)))
      ) false else validateWrongSubtraction(numbers.tail)
    }
  }
}
