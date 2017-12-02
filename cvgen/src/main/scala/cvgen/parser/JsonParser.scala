package cvgen.parser

import java.nio.file.{FileSystems, Files, Path}
import java.util
import scala.collection.JavaConverters._

import cats.syntax.functor._
import cvgen.CV
import cvgen.CV._
import io.circe._
import io.circe.generic.auto._
import io.circe.generic.semiauto._
import io.circe.parser._

import scala.xml.Elem

object JsonParser {

  object JsonCodecs {
    implicit val decodeXmlString: Decoder[Elem] = (c: HCursor) => {
      c.value.asString
        .map(s => Right(scala.xml.XML.loadString(s)))
        .getOrElse(Left(DecodingFailure("Couldn't decode string to elem", c.history)))
    }

    // Todo: Add link to SO post on why this
    implicit val sectionDescriptionDecoder = List[Decoder[SectionDescription]](
      Decoder[ElemSectionDescription].widen,
      Decoder[WithSubsectionsSectionDescription].widen,
      Decoder[SimpleSectionDescription].widen
    ).reduceLeft(_ or _)

    implicit val sectionItemDecoder: Decoder[SectionItem] = deriveDecoder[SectionItem]

    implicit val sectionDecoder: Decoder[Section] = deriveDecoder[Section]

    implicit val cv = Decoder[CV]
  }

  import JsonCodecs._

  def read(cvJson: String): Either[Error, CV] =
    decode[CV](cvJson)

  def readFile(cvFile: String): Either[Error, CV] = {
    val jsonString = Files.readAllLines(FileSystems.getDefault().getPath(cvFile)).asScala.mkString("\n")
    read(jsonString)
  }
}
