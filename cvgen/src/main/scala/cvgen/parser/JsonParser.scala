package cvgen.parser

import java.nio.file.{FileSystems, Files}
import java.util
import scala.collection.JavaConverters._
import scala.xml.Elem

import cats.syntax.functor._
import io.circe._
import io.circe.generic.auto._
import io.circe.generic.semiauto._
import io.circe.parser._

import cvgen.CV
import cvgen.CV._

object JsonParser {

  object JsonCodecs {
    implicit lazy val decodeXmlString: Decoder[Elem] = (c: HCursor) => {
      c.value.asString
        .map(s => Right(scala.xml.XML.loadString(s)))
        .getOrElse(Left(DecodingFailure("Couldn't decode string to elem", c.history)))
    }

    implicit lazy val decodeTextDiv: Decoder[TextDiv] = deriveDecoder[TextDiv]

    implicit lazy val decodeElementDiv: Decoder[ElementDiv] = deriveDecoder[ElementDiv]
    
    implicit lazy val decodeDiv: Decoder[Div] = List[Decoder[Div]](
      Decoder[TextDiv].widen,
      Decoder[ElementDiv].widen,
    ).reduceLeft(_ or _)

    implicit lazy val decodeListItem: Decoder[ListItem] = List[Decoder[ListItem]](
      Decoder[TextListItem].widen,
      Decoder[ElementListItem].widen,
    ).reduceLeft(_ or _)

    implicit lazy val decodeJList: Decoder[JList] = deriveDecoder[JList]

    implicit lazy val decodeElement: Decoder[Element] = List[Decoder[Element]](
      Decoder[Div].widen,
      Decoder[JList].widen,
    ).reduceLeft(_ or _)

    // Todo: Add link to SO post on why this
    implicit lazy val sectionDescriptionDecoder = List[Decoder[SectionDescription]](
      Decoder[ElementSectionDescription].widen,
      Decoder[WithSubsectionsSectionDescription].widen,
      Decoder[SimpleSectionDescription].widen
    ).reduceLeft(_ or _)

    implicit lazy val sectionItemDecoder: Decoder[SectionItem] = deriveDecoder[SectionItem]

    implicit lazy val sectionDecoder: Decoder[Section] = deriveDecoder[Section]

    implicit lazy val cv = Decoder[CV]
  }

  import JsonCodecs._

  def read(cvJson: String): Either[Error, CV] =
    decode[CV](cvJson)

  def readFile(cvFile: String): Either[Error, CV] = {
    val jsonString = Files.readAllLines(FileSystems.getDefault().getPath(cvFile)).asScala.mkString("\n")
    read(jsonString)
  }
}
