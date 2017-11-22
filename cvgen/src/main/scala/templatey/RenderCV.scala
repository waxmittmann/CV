package templatey

import scala.xml.Elem

object RenderCV {

  type Paragraph = String

  /**
    * Shapes and such
    */
  case class CV(
    blurb: Seq[Paragraph],
    experience: Section,
    education: Section,
    skills: Section
  )

  case class Section(items: List[SectionItem])

  sealed trait SectionItem

  trait RenderElem[S] {
    def render(value: S): Elem
  }

  case class DatedSectionItem(
    title: String,
    dateSpan: String, // Todo dates
    description: SectionDescription
  ) extends SectionItem

  case class SimpleSectionItem(
    title: String,
    description: SectionDescription
  ) extends SectionItem

  sealed trait SectionDescription

  case class SimpleSectionDescription(
    title: String
  ) extends SectionDescription

  case class ElemSectionDescription(
    description: Elem
  ) extends SectionDescription

  case class Subsection(
    title: String,
    description: String
  )

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
      <h1 class="pageHeader">Maximilian Wittmann, PdD</h1>
      <ul class="academicTitles">
        <li class="academicTitle">Doctor of Computer Science</li>
        <li class="academicTitle">Bachelor of Computer Science with First Class Honors</li>
      </ul>

      <div class="aboutMeBlurb"> { cvData.blurb.map(text => <p>{text}</p>) } </div>
      <div class="contactMe">The best way to reach me is at <a href="mailto:damxam@gmail.com?Subject=Your%20CV" target="_top">damxam@gmail.com</a>, or through <a href="http://au.linkedin.com/in/maximilianwittmann">LinkedIn</a>.</div>

      <div class="cvMain">
        {section("Experience", cvData.experience)}
        {section("Education", cvData.education)}
        {section("Skills", cvData.skills)}
      </div>

      <div class="footer">You can also check out my <a href="">fledgling GitHub account</a>, the most interesting thing going on there at the moment is probably my <a href="">twiddling with Java 8 Lambdas</a>.</div>
    </body>
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