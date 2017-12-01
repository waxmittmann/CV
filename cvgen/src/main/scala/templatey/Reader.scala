package templatey

import io.circe._
import io.circe.parser._
import templatey.CV._
import io.circe.generic.JsonCodec
import io.circe._
import io.circe.generic.semiauto._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._
import cats.syntax.functor._

import scala.xml.Elem

object Reader {

  object JsonCodecs {
    implicit val decodeXmlString: Decoder[Elem] = (c: HCursor) => {
      c.value.asString
        .map(s => Right(scala.xml.XML.loadString(s)))
        .getOrElse(Left(DecodingFailure("Couldn't decode string to elem", c.history)))
    }

    // Todo: Add link to SO post on why this
    implicit val sectionItemDecoder = List[Decoder[SectionItem]](
      Decoder[DatedSectionItem].widen,
      Decoder[SimpleSectionItem].widen
    ).reduceLeft(_ or _)

    implicit val sectionDescriptionDecoder = List[Decoder[SectionDescription]](
      Decoder[SimpleSectionDescription].widen,
      Decoder[ElemSectionDescription].widen
    ).reduceLeft(_ or _)

    implicit val cv = Decoder[CV]
  }

  import JsonCodecs._

  def read(cvJson: String): Either[Error, CV] =
    decode[CV](cvJson)

  def main(args: Array[String]): Unit = {

    val cv =
      """
        |{
        |
        |
        |}
      """.stripMargin

    println(read(cv))
  }

}
