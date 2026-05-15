package com.galaxy.merchantsGuide.service

import org.scalatest.FlatSpec

class NoteServiceSpec extends FlatSpec {
  val service = new NoteService

  "processNotes" should "process a ValueConversionNote and its ValueDefinitionNote regardless of order" in {
    val notes = List("how much is glob glob ?", "glob is I")

    val result = service.processNotes(notes)
    assert(result.isSuccess)
    assert(result.get.head == "glob glob is 2")
  }

  "processNotes" should
    "process a MaterialValueNote and its ValueDefinitionNote and MaterialDefinitionNote regardless of order" in {
    val notes = List("how many Credits is glob glob Silver ?", "glob is I", "glob Silver is 10 Credits")

    val result = service.processNotes(notes)
    assert(result.isSuccess)
    assert(result.get.head == "glob glob Silver is 20 Credits")
  }

  "processNotes" should "respond with an error message if reading a note of no known type" in {
    val notes = List("how can this note be read ?")

    val result = service.processNotes(notes)
    assert(result.isSuccess)
    assert(result.get.head == "I have no idea what you are talking about. ")
  }

  "processNotes" should "process the given test notes and respond as expected" in {
    val note1 = "glob is I"
    val note2 = "prok is V"
    val note3 = "pish is X"
    val note4 = "tegj is L"
    val note5 = "glob glob Silver is 34 Credits"
    val note6 = "glob prok Gold is 57800 Credits"
    val note7 = "pish pish Iron is 3910 Credits"
    val note8 = "how much is pish tegj glob glob ?"
    val note9 = "how many Credits is glob prok Silver ?"
    val note10 = "how many Credits is glob prok Gold ?"
    val note11 = "how many Credits is glob prok Iron ?"
    val note12 = "how much wood could a woodchuck chuck if a woodchuck could chuck wood ?"

    val notes = List(note1, note2, note3, note4, note5, note6, note7, note8, note9, note10, note11, note12)

    val result = service.processNotes(notes)
    assert(result.isSuccess)
    assert(result.get.head == "pish tegj glob glob is 42")
    assert(result.get(1) == "glob prok Silver is 68 Credits")
    assert(result.get(2) == "glob prok Gold is 57800 Credits")
    assert(result.get(3) == "glob prok Iron is 782 Credits")
    assert(result.get(4) == "I have no idea what you are talking about. ")
  }
}
