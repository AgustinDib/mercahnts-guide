package com.galaxy.merchantsGuide.entity

import com.galaxy.merchantsGuide.enumeration.RomanNumerals.RomanNumeral
import com.galaxy.merchantsGuide.enumeration.{NoteTypeValidations, RomanNumerals}
import com.galaxy.merchantsGuide.exception.ValidationException

/**
  * Class representing a material.
  * @param pricePerUnit of material.
  * @param name for the material.
  */
case class Material(pricePerUnit: BigDecimal, name: String)

object Material {
  /**
    * An apply method that transforms a note of type MaterialDefinitionNote into a Material.
    * @param note to be parsed as a Material.
    * @return parsed Material.
    */
  def apply(note: String, numbers: List[Number]): Material = {
    if (!NoteTypeValidations.MaterialDefinitionNote(note))
      throw ValidationException(s"$note is not a valid NoteTypeValidations.MaterialDefinitionNote.")

    val noteAsList: List[String] = note.split(" ").toList

    // Since a valid MaterialDefinitionNote as a list keeps it's last 4 elements always "materialName is 34 Credits" we
    // can calculate the price in credits and the list of numbers by position.
    val alienNumbers: List[String] = noteAsList.dropRight(4)
    val romanNumber: List[RomanNumeral] = alienNumbers.map { alienNumber =>
      numbers.filter(_.alienName == alienNumber).map(_.romanNumeral).head
    }

    val materialPriceInCredits = noteAsList(noteAsList.length - 2).toInt
    val materialAmount = RomanNumerals.toInt(romanNumber)

    Material(calculatePricePerUnit(materialAmount, materialPriceInCredits), noteAsList.dropRight(3).last)
  }

  /**
    * Private method that calculates the price per unit of material.
    * @param amount of material.
    * @param price of the material in Credits.
    */
  private def calculatePricePerUnit(amount: Int, price: Int): BigDecimal = {
    BigDecimal.valueOf(price) / BigDecimal.valueOf(amount)
  }
}