package com.galaxy.merchantsGuide.exception

/**
  * An [[Exception]] which may be thrown when dealing with validations.
  * @param message A message explaining what went wrong.
  * @param cause The underlying exception that triggered this one, if there is one.
  */
case class ValidationException(message: String, cause: Throwable = null) extends Exception(message, cause)
