package com.galaxy.merchantsGuide.enumeration

import org.scalatest.FlatSpec

// TODO Validar las excepciones que se tiran cuando se parsean numeros inválidos.
// TODO Agregar una validación para Strings vacíos.
class RomanNumeralsSpec extends FlatSpec {

  "III" should "be parsed as 3" in {
    assert(RomanNumerals.toInt(List(RomanNumerals.I, RomanNumerals.I, RomanNumerals.I)) == 3)
  }
  "IV" should "be parsed as 4" in {
    assert(RomanNumerals.toInt(List(RomanNumerals.I, RomanNumerals.V)) == 4)
  }
  "LXIX" should "be parsed as 69" in {
    assert(RomanNumerals.toInt(List(RomanNumerals.L, RomanNumerals.X, RomanNumerals.I, RomanNumerals.X)) == 69)
  }
  "XXXIX" should "be parsed as 39" in {
    assert(RomanNumerals.toInt(
      List(RomanNumerals.X, RomanNumerals.X, RomanNumerals.X, RomanNumerals.I, RomanNumerals.X)) == 39)
  }
  "CDXX" should "be parsed as 69" in {
    assert(RomanNumerals.toInt(List(RomanNumerals.C, RomanNumerals.D, RomanNumerals.X, RomanNumerals.X)) == 420)
  }
  "IIVIII" should "be considered an invalid number" in {
    assert(!RomanNumerals.validateRepetitions(
      List(RomanNumerals.I, RomanNumerals.I, RomanNumerals.V, RomanNumerals.I, RomanNumerals.I, RomanNumerals.I)))
  }
  "IIII" should "be considered an invalid number" in {
    assert(!RomanNumerals.validateFourTimes(
      List(RomanNumerals.I, RomanNumerals.I, RomanNumerals.I, RomanNumerals.I)))
  }
  "XXIXX" should "be considered an invalid number" in {
    assert(!RomanNumerals.validateWrongSeparation(
      List(RomanNumerals.X, RomanNumerals.X, RomanNumerals.I, RomanNumerals.X, RomanNumerals.X)))
  }
  "XM" should "be considered an invalid number" in {
    assert(!RomanNumerals.validateWrongSubtraction(List(RomanNumerals.X, RomanNumerals.M)))
  }
  "IL" should "be considered an invalid number" in {
    assert(!RomanNumerals.validateWrongSubtraction(List(RomanNumerals.I, RomanNumerals.L)))
  }
}
