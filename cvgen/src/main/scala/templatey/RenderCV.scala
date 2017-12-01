package templatey

import scala.xml.Elem

import io.circe.generic.JsonCodec
import io.circe._, io.circe.generic.semiauto._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
import cats.syntax.functor._

object RenderCV {

  type Paragraph = String


  implicit val decodeXmlString: Decoder[Elem] = (c: HCursor) => {
    c.value.asString
      .map(s => Right(scala.xml.XML.loadString(s)))
      .getOrElse(Left(DecodingFailure("Couldn't decode string to elem", c.history)))
  }

    /*
  for {
    text <- c.value.asString
    elem <- {
      val x: Elem = scala.xml.XML.loadString(text)
      Some(x)
    }
  } yield elem
*/

  trait RenderElem[S] {
    def render(value: S): Elem
  }

  /**
    * Shapes and such
    */
  //@JsonCodec
  case class CV(
    blurb: Seq[Paragraph],
    experience: Section,
    education: Section,
    skills: Section
  )

  //@JsonCodec
  case class Section(items: List[SectionItem])
  implicit val sectionDecoder = deriveDecoder[Section]

  //@JsonCodec
  sealed trait SectionItem
//  implicit val sectionItemDecoder = deriveDecoder[SectionItem]
  implicit val sectionItemDecoder = List[Decoder[SectionItem]](
    Decoder[DatedSectionItem].widen,
    Decoder[SimpleSectionItem].widen
  ).reduceLeft(_ or _)

  //@JsonCodec
  case class DatedSectionItem(
    title: String,
    dateSpan: String, // Todo dates
    description: SectionDescription
  ) extends SectionItem

  //@JsonCodec
  case class SimpleSectionItem(
    title: String,
    description: SectionDescription
  ) extends SectionItem

  //@JsonCodec
  sealed trait SectionDescription
//  implicit val sectionDescriptionDecoder = deriveDecoder[SectionDescription]
  implicit val sectionDescriptionDecoder = List[Decoder[SectionDescription]](
    Decoder[SimpleSectionDescription].widen,
    Decoder[ElemSectionDescription].widen
  ).reduceLeft(_ or _)

  //@JsonCodec
  case class SimpleSectionDescription(
    title: String
  ) extends SectionDescription

  //@JsonCodec
  case class ElemSectionDescription(
    description: Elem
  ) extends SectionDescription

  //@JsonCodec
  case class Subsection(
    title: String,
    description: String
  )
  implicit val subsectionDecoder = deriveDecoder[Subsection]

  //@JsonCodec
  case class WithSubsectionsSectionDescription(
    title: String,
    subsections: List[Subsection]
  ) extends SectionDescription

  implicit object RenderSectionDescription extends RenderElem[SectionDescription] {
    override def render(value: SectionDescription): Elem = value match {
      case SimpleSectionDescription(title) =>
        <div class="sectionDescription">
          <div>{title}</div>
        </div>

      case ElemSectionDescription(description) =>
        <div class="sectionDescription">
          {description}
        </div>

      case WithSubsectionsSectionDescription(title, subsections) =>
        <div class="sectionDescription">
          <div>{title}</div>
          <div>{subsections.map(subsection)}</div>
        </div>
    }
  }

  implicit object RenderSectionItem extends RenderElem[SectionItem] {
    override def render(value: SectionItem): Elem = value match {
      case data @ DatedSectionItem(_, _, _)   => datedItem(data)
      case data @ SimpleSectionItem(_, _)     => simpleItem(data)
    }

    def datedItem(data: DatedSectionItem)(implicit renderDescription: RenderElem[SectionDescription]): Elem =
      <div class="mainSectionItem">
        <div class="dateSpan">{data.dateSpan}</div>
        <div class="sectionTitle">{data.title}</div>
        <div class="sectionDescription">{renderDescription.render(data.description)}</div>
      </div>

    def simpleItem(data: SimpleSectionItem)(implicit renderDescription: RenderElem[SectionDescription]): Elem =
      <div class="mainSectionItem">
        <div class="sectionTitle">{data.title}</div>
        <div class="sectionDescription">{renderDescription.render(data.description)}</div>
      </div>
  }

  /**
    * Render methods
    */
  def cv(cvData: CV): Elem = <html>
    <head>
      <meta name="viewport" content="width=device-width, initial-scale=1"/>
      <link rel="stylesheet" type="text/css" href="./css/common.css" media="all"/>

      <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'/>
    </head>
    <body class="main">
      <h1 class="pageHeader">Maximilian Wittmann, PhD</h1>
      <ul class="academicTitles">
        <li class="academicTitle">Doctor of Computer Science</li>
        <li class="academicTitle">Bachelor of Computer Science with First Class Honors</li>
      </ul>

      <div class="aboutMeBlurb"> { cvData.blurb.map(text => <p>{text}</p>) } </div>
      <!--
      <div class="aboutTheBlurb" class="contactMe">
        <a href="#" onclick="toggle_visibility('aboutTheBlurbDetails');">This CV lives on AWS. Want to find out more?</a>
        <div id="aboutTheBlurbDetails">
          Here is an explanation of how this blurb is built.
        </div>
      </div>
      -->
      <div class="contactMe">The best way to reach me is at <a href="mailto:damxam@gmail.com?Subject=Your%20CV" target="_top">damxam@gmail.com</a>, or through <a href="http://au.linkedin.com/in/maximilianwittmann">LinkedIn</a>.</div>

      <div class="cvMain">
        {section("Experience", cvData.experience)}
        {section("Education", cvData.education)}
        {section("Skills", cvData.skills)}
      </div>

      <div class="footer">You can also check out my <a href="https://github.com/waxmittmann">GitHub account</a> to see what kind of things I've been playing with.</div>
    </body>

    <script type="text/javascript">
    {
      <!--
      function toggle_visibility(id) {
         var e = document.getElementById(id);
         if(e.style.display == 'block')
            e.style.display = 'none';
         else
            e.style.display = 'block';
      }
      //-->
    }
    </script>
  </html>

  def subsection(subsection: Subsection): Elem =
    <div class="mainSubSectionItem">
      <div class="sectionTitle">{subsection.title}</div>
      <div class="sectionDescription">{subsection.description}</div>
    </div>

  def section(header: String, section: Section)(implicit sectionItemRender: RenderElem[SectionItem]): Elem =
    <div class="mainSection">
      <h1 class="mainSectionHeader">{header}</h1>
      {section.items.map(sectionItemRender.render)}
    </div>
}