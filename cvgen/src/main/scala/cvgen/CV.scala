package cvgen

import scala.xml.Elem

import cvgen.CV.Section
import cvgen.RenderCV.Paragraph

case class CV(
  blurb: Seq[Paragraph],
  experience: Section,
  education: Section,
  skills: Section
)

object CV {
  case class Section(
    title: String,
    items: List[SectionItem]
  )

  case class SectionItem(
    title: String,
    dateSpan: Option[String], // Todo dates
    description: SectionDescription
  )

  object SectionItem {
    def simple(title: String, description: SectionDescription): SectionItem =
      SectionItem(title, None, description)

    def dated(dateSpan: String, title: String, description: SectionDescription): SectionItem =
      SectionItem(title, Some(dateSpan), description)
  }

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
}
