package com.galaxy.merchantsGuide.enumeration

import org.scalatest.FlatSpec

class NoteTypeValidationsSpec extends FlatSpec {

  "prok is V" should "be a valid ValueDefinitionNote" in {
    val note =  "prok is V"
    assert(NoteTypeValidations.ValueDefinitionNote(note))
  }

  "prok is is V" should "not be a valid ValueDefinitionNote" in {
    val note =  "prok is is V"
    assert(!NoteTypeValidations.ValueDefinitionNote(note))
  }

  "prok is" should "not be a valid ValueDefinitionNote" in {
    val note =  "prok is"
    assert(!NoteTypeValidations.ValueDefinitionNote(note))
  }

  "glob glob Silver is 34 Credits" should "be a valid MaterialDefinitionNote" in {
    val note =  "glob glob Silver is 34 Credits"
    assert(NoteTypeValidations.MaterialDefinitionNote(note))
  }

  "glob glob Silver is 3f4 Credits" should "be a valid MaterialDefinitionNote" in {
    val note =  "glob glob Silver is 3f4 Credits"
    assert(!NoteTypeValidations.MaterialDefinitionNote(note))
  }

  "glob glob Silver is is 34 Credits" should "be a valid MaterialDefinitionNote" in {
    val note =  "glob glob Silver is is 34 Credits"
    assert(!NoteTypeValidations.MaterialDefinitionNote(note))
  }

  "glob glob Silver 34 Credits" should "not be a valid MaterialDefinitionNote" in {
    val note =  "glob glob Silver 34 Credits"
    assert(!NoteTypeValidations.MaterialDefinitionNote(note))
  }

  "glob glob Silver is 34" should "not be a valid MaterialDefinitionNote" in {
    val note =  "glob glob Silver is 34"
    assert(!NoteTypeValidations.MaterialDefinitionNote(note))
  }

  "how much is pish tegj glob glob ?" should "be a valid ValueConversionNote" in {
    val note = "how much is pish tegj glob glob ?"
    assert(NoteTypeValidations.ValueConversionNote(note))
  }

  "how much is ?" should "not be a valid ValueConversionNote" in {
    val note = "how much is ?"
    assert(!NoteTypeValidations.ValueConversionNote(note))
  }

  "how much pish tegj glob glob ?" should "not be a valid ValueConversionNote" in {
    val note = "how much pish tegj glob glob ?"
    assert(!NoteTypeValidations.ValueConversionNote(note))
  }

  "how many Credits is glob prok Silver ?" should "be a valid MaterialValueNote" in {
    val note = "how many Credits is glob prok Silver ?"
    assert(NoteTypeValidations.MaterialValueNote(note))
  }

  "how many Credits is glob prok Silver" should "not be a valid MaterialValueNote" in {
    val note = "how many Credits is glob prok Silver"
    assert(!NoteTypeValidations.MaterialValueNote(note))
  }
  "how many Silver is glob prok Silver ?" should "be a valid MaterialConversionNote" in {
    val note = "how many Silver is glob prok Silver ?"
    assert(NoteTypeValidations.MaterialConversionNote(note))
  }
  "how many Silver  is glob prok  Silver   ?" should "not be a valid MaterialConversionNote" in {
    val note = "how many Silver  is glob prok  Silver   ?"
    assert(!NoteTypeValidations.MaterialConversionNote(note))
  }
  "how many Silver is glob Silver ?" should "be a valid MaterialConversionNote" in {
    val note = "how many Silver is glob Silver ?"
    assert(NoteTypeValidations.MaterialConversionNote(note))
  }
  "how many Silver is glob prok Silver" should "not be a valid MaterialConversionNote" in {
    val note = "how many Silver is glob prok Silver"
    assert(!NoteTypeValidations.MaterialConversionNote(note))
  }
  "how many Silver is Silver ?" should "not be a valid MaterialConversionNote" in {
    val note = "how many Silver is Silver ?"
    assert(!NoteTypeValidations.MaterialConversionNote(note))
  }
  "how many is glob prok Silver ?" should "not be a valid MaterialConversionNote" in {
    val note = "how many is glob prok Silver ?"
    assert(!NoteTypeValidations.MaterialConversionNote(note))
  }
}
