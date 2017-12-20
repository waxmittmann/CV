package cvgen.renderers

import cvgen.CV._
import cvgen.renderers.ElemRenderer.XmlRenderer
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy
import org.apache.poi.xwpf.usermodel.XWPFDocument
import cvgen.{CV, CVRender}
import org.apache.poi.wp.usermodel.Paragraph
import org.odftoolkit.simple.TextDocument

//class WordDocument protected(
//    val curParagraph: Option[Paragraph] = None
//) {
//  val document: XWPFDocument = new XWPFDocument()
//
//  def newParagraph: WordDocument = copy(curParagraph = Some(document.createParagraph()))
//
//  def completeParagraph: WordDocument = copy(curParagraph = None)
//
//  def copy(
//    curParagraph: Option[Paragraph] = curParagraph
//  ): WordDocument =
//    new WordDocument(curParagraph)
//}


class WordRenderer extends CVRender {
//  override lazy type OUT = WordDocument
  override type OUT = Unit

//  val document: XWPFDocument = new XWPFDocument()
  val document: TextDocument = TextDocument.newTextDocument()

  override lazy implicit val sectionRenderer: OutRenderer[CV.Section] =
    (value: CV.Section) => {
      document.addParagraph(value.title)
      value.items.foreach(sectionItemRenderer.render)
//      document.createvalue.title
    }

  override lazy implicit val sectionItemRenderer: OutRenderer[CV.SectionItem] =
    (value: CV.SectionItem) => {
      document.addParagraph(value.title)
      value.dateSpan.foreach(document.addParagraph)
      sectionDescriptionRenderer.render(value.description)
    }

  override lazy implicit val sectionDescriptionRenderer: OutRenderer[CV.SectionDescription] =
    (value: SectionDescription) => value match {
      case d: SimpleSectionDescription => simpleSectionDescriptionRenderer.render(d)
      case d: ElementSectionDescription => elemSectionDescriptionRenderer.render(d)
      case d: WithSubsectionsSectionDescription => withSubsectionsSectionDescriptionRenderer.render(d)
    }

  override lazy implicit val simpleSectionDescriptionRenderer: OutRenderer[CV.SimpleSectionDescription] =
    (value: SimpleSectionDescription) => {
      document.addParagraph(value.title)
    }

  override lazy implicit val elemSectionDescriptionRenderer: OutRenderer[CV.ElementSectionDescription] =
    (value: ElementSectionDescription) => {
      System.err.println("Elem section descriptions are no longer supported")
      //value.description.
    }

  override lazy implicit val subsectionRenderer: OutRenderer[CV.Subsection] =
    (value: Subsection) => {
      document.addParagraph(value.title)
      document.addParagraph(value.description)
    }

  override lazy implicit val withSubsectionsSectionDescriptionRenderer: OutRenderer[CV.WithSubsectionsSectionDescription] =
    (value: WithSubsectionsSectionDescription) => {
      document.addParagraph(value.title)
      value.subsections.foreach { subsection =>
        document.addParagraph(subsection.title)
        document.addParagraph(subsection.description)
      }
    }

  override lazy implicit val elementRenderer: OutRenderer[CV.Element] =
    (value: Element) => value match {
      case d: Div   => divRender.render(d)
      case l: JList => jlistRenderer.render(l)
    }

  override lazy implicit val divRender: OutRenderer[CV.Div] =
    (value: Div) => value match {
      case d: ElementDiv => elemDivRenderer.render(d)
      case d: TextDiv  => textDivRenderer.render(d)
    }

  override lazy implicit val elemDivRenderer: OutRenderer[CV.ElementDiv] =
    (value: ElementDiv) => {
      // Todo: Styles
      value.elems.foreach(elementRenderer.render)
    }

  override lazy implicit val textDivRenderer: OutRenderer[CV.TextDiv] =
    (value: TextDiv) => {
      document.addParagraph(value.text)
    }

  override lazy implicit val listItemRenderer: OutRenderer[CV.ListItem] =
    (value: ListItem) => value match {
      case li: ElementListItem => elemListItemRenderer.render(li)
      case li: TextListItem => textListItemRenderer.render(li)
    }

  override lazy implicit val elemListItemRenderer: OutRenderer[CV.ElementListItem] =
    (value: ElementListItem) => {
      // Todo: Add to list. Hmm.
      elementRenderer.render(value.elem)
    }

  override lazy implicit val textListItemRenderer: OutRenderer[CV.TextListItem] =
    (value: TextListItem) => {
      document.addParagraph(value.text)
    }

  override lazy implicit val jlistRenderer: OutRenderer[CV.JList] =
    (value: JList) => {
      value.items.foreach(listItemRenderer.render)
    }

  implicit val cvRenderer: OutRenderer[CV] = new OutRenderer[CV] {
    val renderer: OutRenderer[Section] = implicitly[OutRenderer[Section]]

    override def render(cvData: CV) = {

      document.addParagraph("Maximilian Wittmann, PhD")


      val li = document.addList()
      li.addItem("Doctor of Computer Science")
      li.addItem("Bachelor of Computer Science with First Class Honors")

      cvData.blurb.map(document.addParagraph)

      document.addParagraph("""The best way to reach me is at <a href="mailto:damxam@gmail.com?Subject=Your%20CV" target="_top">damxam@gmail.com</a>, or through <a href="http://au.linkedin.com/in/maximilianwittmann">LinkedIn</a>.</div>""")


      {renderer.render(cvData.experience)}
      {renderer.render(cvData.education)}
      {renderer.render(cvData.skills)}


     document.addParagraph("""<div class="footer">You can also check out my <a href="https://github.com/waxmittmann">GitHub account</a> to see what kind of things I've been playing with.</div>""")
      ()
    }
  }

  def write(outpath: String): Unit = {
    document.save(outpath)
  }
}
