package templatey

import io.circe._
import templatey.Children.{ChildA, ChildB}
import templatey.Stuff.Parent
//import io.circe.generic.JsonCodec
//import io.circe.generic.auto._
import io.circe.generic.semiauto._
import io.circe.parser._
//import io.circe.syntax._
import templatey.RenderCV.{SectionDescription, SimpleSectionDescription, Subsection}
import cats.syntax.functor._

object Stuff {
  sealed trait Parent
}

object Parent {
  implicit val childACodec = deriveDecoder[ChildA]
  implicit val childBCodec = deriveDecoder[ChildB]
//  implicit val parentCodec = deriveDecoder[Parent]

  implicit val decodeParent: Decoder[Parent] =
    List[Decoder[Parent]](
      Decoder[ChildA].widen,
      Decoder[ChildB].widen
    ).reduceLeft(_ or _)
}

object Children {
  case class ChildA(str: String) extends Parent
  case class ChildB(nr: Int) extends Parent
}

object Reader {

//  @JsonCodec
//  case class Thing(name: String, age: Int)
//
//  val thingString =
//    """
//      |{
//      |   "name": "Joe",
//      |   "age": 53
//      |}
//    """.stripMargin

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

  val parentStr =
    """
      |{
      |   "str": "haha"
      |}
    """.stripMargin

//  object JsonCodecs {
//    implicit val parentCodec = deriveDecoder[Parent]
//  }
  import Parent._

  def main(args: Array[String]): Unit = {

//    val json = Json.fromString(thingString)
//    println(json)
//
//    val thing = decode[Thing](thingString)
//    println(thing)

    val section: Either[Error, SectionDescription] = decode[SectionDescription](sectionString)
//    val section = decode[SimpleSectionDescription](sectionString)
    println(section)

    val subsection = decode[Subsection](subsectionString)
    println(subsection)

    val test = decode[Stuff.Parent](parentStr)
    println(test)
  }

}
