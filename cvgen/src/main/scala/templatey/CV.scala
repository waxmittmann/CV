package templatey

import templatey.CV.Section
import templatey.RenderCV.Paragraph

import scala.xml.Elem

case class CV(
  blurb: Seq[Paragraph],
  experience: Section,
  education: Section,
  skills: Section
)

object CV {
  case class Section(items: List[SectionItem])

  sealed trait SectionItem

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
}
