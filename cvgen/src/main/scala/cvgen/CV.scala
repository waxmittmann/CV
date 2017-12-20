package cvgen

import cvgen.CV._
import cvgen.RenderCV.{Paragraph, Renderer, StatefulRenderer}

case class CV(
  blurb: Seq[Paragraph],
  experience: Section,
  education: Section,
  skills: Section
)

trait CVRender {
  type OUT
  type OutRenderer[S] = Renderer[S, OUT]

  implicit val sectionRenderer: OutRenderer[Section]

  implicit val sectionItemRenderer: OutRenderer[SectionItem]

  implicit val sectionDescriptionRenderer: OutRenderer[SectionDescription]

  implicit val simpleSectionDescriptionRenderer: OutRenderer[SimpleSectionDescription]

  implicit val elemSectionDescriptionRenderer: OutRenderer[ElementSectionDescription]

  implicit val subsectionRenderer: OutRenderer[Subsection]

  implicit val withSubsectionsSectionDescriptionRenderer: OutRenderer[WithSubsectionsSectionDescription]

  implicit val elementRenderer: OutRenderer[Element]

  implicit val divRender: OutRenderer[Div]

  implicit val elemDivRenderer: OutRenderer[ElementDiv]

  implicit val textDivRenderer: OutRenderer[TextDiv]

  implicit val listItemRenderer: OutRenderer[ListItem]

  implicit val elemListItemRenderer: OutRenderer[ElementListItem]

  implicit val textListItemRenderer: OutRenderer[TextListItem]

  implicit val jlistRenderer: OutRenderer[JList]

  implicit val cvRenderer: OutRenderer[CV]
}

trait StatefulCVRender {
  type OUT
  type STATE

  type OutRenderer[S] = StatefulRenderer[S, STATE, OUT]

  implicit val sectionRenderer: OutRenderer[Section]

  implicit val sectionItemRenderer: OutRenderer[SectionItem]

  implicit val sectionDescriptionRenderer: OutRenderer[SectionDescription]

  implicit val simpleSectionDescriptionRenderer: OutRenderer[SimpleSectionDescription]

  implicit val elemSectionDescriptionRenderer: OutRenderer[ElementSectionDescription]

  implicit val subsectionRenderer: OutRenderer[Subsection]

  implicit val withSubsectionsSectionDescriptionRenderer: OutRenderer[WithSubsectionsSectionDescription]

  implicit val elementRenderer: OutRenderer[Element]

  implicit val divRender: OutRenderer[Div]

  implicit val elemDivRenderer: OutRenderer[ElementDiv]

  implicit val textDivRenderer: OutRenderer[TextDiv]

  implicit val listItemRenderer: OutRenderer[ListItem]

  implicit val elemListItemRenderer: OutRenderer[ElementListItem]

  implicit val textListItemRenderer: OutRenderer[TextListItem]

  implicit val jlistRenderer: OutRenderer[JList]

  implicit val cvRenderer: OutRenderer[CV]
}


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

  case class ElementSectionDescription(
    description: Element
  ) extends SectionDescription

  case class Subsection(
    title: String,
    description: String
  )

  case class WithSubsectionsSectionDescription(
    title: String,
    subsections: List[Subsection]
  ) extends SectionDescription

  sealed trait Element

  sealed trait Div extends Element

  case class ElementDiv(
    styles: List[String],
    elems: List[Element]
  ) extends Div

  case class TextDiv(
    styles: List[String],
    text: String
  ) extends Div

  sealed trait ListItem

  case class ElementListItem(
    styles: List[String],
    elem: Element
  ) extends ListItem

  case class TextListItem(
    styles: List[String],
    text: String
  ) extends ListItem

  case class JList(
    styles: List[String],
    items: List[ListItem]
  ) extends Element
}
