package com.galaxy.merchantsGuide.enumeration

/**
  * Enumeration class that describes the validations that filter the different type of notes.
  */
// TODO Agregar el tipo de dato de retorno para hacer mas explícita la lectura de este Enum.
// TODO Quizás esto podría dejar de ser un Enum y pasar a ser un Helper
object NoteTypeValidations extends Enumeration {
  // A note that maps an alien word t a roman numeral: "prok is V"
  val ValueDefinitionNote: String => Boolean = (note: String) => {
    val noteAsList: List[String] = note.toLowerCase.split(" ").toList
    noteAsList.length == 3 &&
      noteAsList(1) == "is" &&
      RomanNumerals.values.contains(RomanNumerals.withName(noteAsList.last.toUpperCase))
  }

  // A note that maps an amount of a given material to it's price: "glob glob Silver is 34 Credits"
  val MaterialDefinitionNote = (note: String) => {
    val noteAsList: List[String] = note.toLowerCase.split(" ").toList
    noteAsList.last == "credits" &&
      noteAsList.contains("is") &&
      noteAsList.count(_ != "is") == noteAsList.length - 1 &&
      noteAsList(noteAsList.length - 2).forall(Character.isDigit)

  }

  // A note that asks for a translation between a number expressed in alien words to it's value as an integer: "how much
  // is pish tegj glob glob ?"
  val ValueConversionNote = (note: String) => {
    val noteAsList: List[String] = note.toLowerCase.split(" ").toList
    note.toLowerCase.startsWith("how much is") &&
      note.endsWith("?") &&
      noteAsList.exists(word => word != "how" && word != "much" && word != "is" && word != "?")
  }

  // A note that asks for the price of a given amount of a given material: "how many Credits is glob prok Silver ?"
  val MaterialValueNote = (note: String) => note.toLowerCase.startsWith("how many credits is") && note.endsWith("?")

  // A note that asks for the amount of a given Material that can be bought with the amount of other Material: "how many
  // Silver is glob Gold ?"
  val MaterialConversionNote: String => Boolean = (note: String) => {
    val noteAsList: List[String] = note.toLowerCase.split(" ").toList

    note.toLowerCase.startsWith("how many") && note.endsWith("?") && noteAsList.size > 6 && noteAsList(3) == "is"
  }
}
