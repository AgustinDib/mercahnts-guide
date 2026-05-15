package com.galaxy.merchantsGuide.entity

import com.galaxy.merchantsGuide.enumeration.{NoteTypeValidations, RomanNumerals}
import com.galaxy.merchantsGuide.enumeration.RomanNumerals.RomanNumeral
import com.galaxy.merchantsGuide.exception.ValidationException

/**
  * Class representing a number from an alien alphabet with it's roman counterpart.
  * @param alienName for the number.
  * @param romanNumeral for the number.
  */
case class Number(alienName: String, romanNumeral: RomanNumeral)

object Number {
  /**
    * An apply method that transforms a note of type ValueDefinitionNote into a Number.
    * @param note to be parsed as a Number.
    * @return parsed Number.
    */
  def apply(note: String): Number = {
    if (!NoteTypeValidations.ValueDefinitionNote(note)) {
      throw ValidationException(s"$note is not a valid NoteTypeValidations.ValueDefinitionNote.")
    }

    val noteAsList = note.split(" ")
    Number(noteAsList.head, RomanNumerals.withName(noteAsList.last.toUpperCase))
  }
}