package com.galaxy.merchantsGuide.service

import com.galaxy.merchantsGuide.entity.{Material, Number}
import com.galaxy.merchantsGuide.enumeration.{NoteTypeValidations, RomanNumerals}
import com.galaxy.merchantsGuide.exception.ValidationException

import scala.util.Try

// TODO Agregar a la interfaz pública de esta clase métodos para tratar con notas de un tipo definido.
class NoteService {

  /**
    * Process the notes considering it's intention and responds in case of question note.
    *
    * @param notes to be processed.
    * @return A List of responses to the questions in notes or a list of failure messages for the incorrect notes.
    */
  def processNotes(notes: List[String]): Try[List[String]] = Try {
    lazy val errorResponse = "I have no idea what you are talking about. "

    val definedNumbers: List[Number] = notes.filter(NoteTypeValidations.ValueDefinitionNote).map(Number(_))
    val materials: List[Material] = notes.filter(NoteTypeValidations.MaterialDefinitionNote).map(Material(_, definedNumbers))

    notes.filter(note => !NoteTypeValidations.ValueDefinitionNote(note) &&
      !NoteTypeValidations.MaterialDefinitionNote(note)).map {

      case n if NoteTypeValidations.ValueConversionNote(n) => parseValueConversionNote(n, definedNumbers) match {
        case Left(res) => res.toString
        case Right(err) => errorResponse + err.getMessage
      }
      case n if NoteTypeValidations.MaterialValueNote(n) => parseMaterialValueNote(n, definedNumbers, materials) match {
        case Left(res) => res.toString
        case Right(err) => errorResponse + err.getMessage
      }
      case n if NoteTypeValidations.MaterialConversionNote(n) => parseMaterialConversionNote(n, definedNumbers, materials) match {
        case Left(res) => res.toString
        case Right(err) => errorResponse + err.getMessage
      }
      case _ => errorResponse
    }
  }

  // how many Silver is glob Gold ? 
  private def parseMaterialConversionNote(note: String, numbers: List[Number], materials: List[Material]):
  Either[String, Throwable] = try {
    // Find how much is glob Gold: glob * Gold = materialPrice in Credits
    val materialPrice: BigDecimal = calculateMaterialPrice(note, numbers, materials)

    // Find how many Silver i can buy with that
    val noteAsList: List[String] = note.split(" ").toList
    val goldPrice: BigDecimal = materials.filter(material => material.name == noteAsList(2)).head.pricePerUnit
    val totalAmount: BigDecimal = materialPrice / goldPrice

    Left(s"Mock note $totalAmount")
  } catch {
    case ex: Throwable => Right(ex)
  }

  /**
    * Parses NoteTypeValidations.ValueConversionNote type notes such as: "how much is pish tegj glob glob ?".
    * @param note to be parsed.
    * @param numbers representing the defined valued for the alien alphabet numbers.
    * @return result of parsing.
    */
  // TODO Cambiar el tipo de dato de retorno por Try
  private def parseValueConversionNote(note: String, numbers: List[Number]): Either[String, Throwable] = try {
    // Parse the note to transform it to a list and get it's  numbers as written by aliens.
    val noteAsList: List[String] = note.split(" ").toList
    val numbersInAlien: List[String] = noteAsList.slice(3, noteAsList.length - 1)

    // Transform the numbers as written by aliens into a list of Number.
    val numbersFromNote: List[Number] = numbersInAlien.flatMap(alienNumber => numbers.find(_.alienName == alienNumber))

    Left(numbersInAlien.mkString(" ") + " is " + RomanNumerals.toInt(numbersFromNote.map(_.romanNumeral)))
  } catch {
    case ex: Throwable => Right(ex)
  }

  /**
    * Parses NoteTypeValidations.MaterialValueNote type notes such as: "how many Credits is glob prok Iron ?".
    * @param note to be parsed.
    * @param numbers representing the defined valued for the alien alphabet numbers.
    * @param materials the existing materials according to material definitions.
    * @return result of parsing.
    */
  // TODO Cambiar el tipo de dato de retorno por Try
  private def parseMaterialValueNote(note: String, numbers: List[Number], materials: List[Material]):
    Either[String, Throwable] = try {
      val (numbersInAlien, materialName) = (getAlienNumberNames(note), getMaterial(note, materials).name)
      val res: BigDecimal = calculateMaterialPrice(note, numbers, materials)

      Left(numbersInAlien.mkString(" ") + " " + materialName + " is " + res.intValue + " Credits")
    } catch {
      case ex: Throwable => Right(ex)
    }

  // Parse the note to transform it to a list and get it's numbers as written by aliens.
  private def getAlienNumberNames(note: String): List[String] = {
    val noteAsList: List[String] = note.split(" ").toList
    noteAsList.slice(4, noteAsList.length - 2)
  }

  // Parse the note to get the Material.
  private def getMaterial(note: String, materials: List[Material]): Material = {
    val notes = note.split(" ").toList
    val materialName: String = notes(notes.length - 2)

    materials.find(_.name == materialName).getOrElse(
      throw ValidationException(s"No material found with name: $materialName")
    )
  }

  private def calculateMaterialPrice(note: String, numbers: List[Number], materials: List[Material]): BigDecimal = {
    val numbersInAlien: List[String] = getAlienNumberNames(note)
    val material: Material = getMaterial(note, materials)

    // Transform the numbers as written by aliens into a list of Number.
    val numbersFromNote: List[Number] = numbersInAlien.flatMap(alienName => numbers.find(_.alienName == alienName))

    val res: BigDecimal = material.pricePerUnit * RomanNumerals.toInt(numbersFromNote.map(_.romanNumeral))

    res
  }
}
