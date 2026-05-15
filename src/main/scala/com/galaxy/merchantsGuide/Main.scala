package com.galaxy.merchantsGuide

import com.galaxy.merchantsGuide.service.NoteService

import scala.io.Source
import scala.util.{Failure, Success}

object Main extends App {

  val noteService = new NoteService
  val fileName = if (args.isEmpty) "sample.txt" else args.head

  try {
    val bufferedSource = Source.fromFile(fileName)
    noteService.processNotes(bufferedSource.getLines.toList) match {
      case Success(res) => res.foreach(println)
      case Failure(e) => {
        bufferedSource.close
        throw e
      }
    }
    bufferedSource.close
  } catch {
    case e: Throwable => println(e.getMessage)
  }
}
