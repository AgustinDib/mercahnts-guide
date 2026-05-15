# Merchant's Guide to the Galaxy
ThoughtWorks Code Assignment

## How to run
- **Application**: Using a command line tool, from the project's root type `sbt run` for using a default sample file or `sbt "run fileName"` for using your own file, placed in project's root.
- **Tests**: Using a command line tool, from the project's root type `sbt test`.
## Design notes
This application has been designed as an object oriented solution constructed following functional priciples.
###
It's a Domain Driven Designed application that counts with logic encapsulated in different entities that describe the business involved:
###
- **Material**: represents a material such as iron which holds a price per unit and a name.
- **Number**: represents a number from an alien alphabet with it's roman counterpart.
- **Roman Numeral**: describes the mapping between roman numeral symbols and it's corresponding integer values.
###
There is also a Service layer that exposes functionality for parsing different types of notes. This types are expressed as an enumeration that holds a validation for the type it represents.
###
Application logic is unit tested with a heavy concern facing validations and edge cases.
## TO-DO
- Add logging.
- Add a persistence layer.
- Add integration tests.
- Expose a Rest API.
- Use of .properties file for hardcoded text.
- Add a service for storing/retrieving Number definitions.
- Add a service for storing/retrieving Material definitions.