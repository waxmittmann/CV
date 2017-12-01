package templatey

import io.circe._
import io.circe.generic.JsonCodec
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._
import templatey.RenderCV.{SectionDescription, SimpleSectionDescription, Subsection}

object Reader {

  @JsonCodec
  case class Thing(name: String, age: Int)

  val thingString =
    """
      |{
      |   "name": "Joe",
      |   "age": 53
      |}
    """.stripMargin

  val sectionString =
    """
      |{
      |   "title": "Hi There!"
      |}
    """.stripMargin

  val subsectionString =
    """
      |{
      |   "title": "ABC",
      |   "description": "Boo"
      |}
    """.stripMargin

  /*
    case class SimpleSectionDescription(
    title: String
  ) extends SectionDescription

 @JsonCodec
  case class Subsection(
    title: String,
    description: String
  )

   */

  def main(args: Array[String]): Unit = {

    val json = Json.fromString(thingString)
    println(json)

    val thing = decode[Thing](thingString)
    println(thing)

    val section = decode[SectionDescription](sectionString)
    println(section)

    val subsection = decode[Subsection](subsectionString)
    println(subsection)
  }

}
