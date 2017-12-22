package cvgen.renderers

import cvgen.CV._
import cvgen.renderers.ElemRenderer.XmlRenderer
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy
import org.apache.poi.xwpf.usermodel.XWPFDocument
import cvgen.{CV, CVRender, StatefulCVRender}
import org.apache.poi.wp.usermodel.Paragraph
import org.odftoolkit.simple.TextDocument
import org.odftoolkit.simple.text.list.{ List => OdtList }

import scala.collection.JavaConverters._
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


class WordRenderer extends StatefulCVRender {
  def render(cv: CV) = cvRenderer.render(cv, WordRendererState())._1

  //  override lazy type OUT = WordDocument

  case class WordRendererState private(
    document: TextDocument = TextDocument.newTextDocument(),
    curList: Option[OdtList] = None,
    curParagraph: Option[Paragraph] = None
  ) {

    println("Text styles:\n")
    println(document.getDocumentStyles.getTextStyles.asScala.map(_.toString))
    println("Done...")

    def finishList: WordRendererState = this.copy(curList = None)

    def addList = this.copy(curList = Some(document.addList().setDecorator()))

    def write(outpath: String): Unit = {
      document.save(outpath)
    }
  }

  override type STATE = WordRendererState
  override type OUT = Unit

//  val document: XWPFDocument = new XWPFDocument()

    //document.addList()

  override lazy implicit val sectionRenderer: OutRenderer[CV.Section] =
    (value: CV.Section, state: WordRendererState) => {
      state.document.addParagraph(value.title).getOdfElement.setStyleName("Heading_20_1")
      value.items.foreach(i => sectionItemRenderer.render(i, state))
//      document.createvalue.title
      (state, ())
    }

  override lazy implicit val sectionItemRenderer: OutRenderer[CV.SectionItem] =
    (value: CV.SectionItem, state: WordRendererState) => {
      state.document.addParagraph(value.title).getOdfElement.setStyleName("Heading_20_2")
      value.dateSpan.foreach(v => state.document.addParagraph(v).getOdfElement.setStyleName("Heading_20_3"))
      sectionDescriptionRenderer.render(value.description, state)
    }

  override lazy implicit val sectionDescriptionRenderer: OutRenderer[CV.SectionDescription] =
    (value: SectionDescription, state: WordRendererState) => value match {
      case d: SimpleSectionDescription => simpleSectionDescriptionRenderer.render(d, state)
      case d: ElementSectionDescription => elemSectionDescriptionRenderer.render(d, state)
      case d: WithSubsectionsSectionDescription => withSubsectionsSectionDescriptionRenderer.render(d, state)
    }

  override lazy implicit val simpleSectionDescriptionRenderer: OutRenderer[CV.SimpleSectionDescription] =
    (value: SimpleSectionDescription, state: WordRendererState) => {
      state.document.addParagraph(value.title)
      (state, ())
    }

  override lazy implicit val elemSectionDescriptionRenderer: OutRenderer[CV.ElementSectionDescription] =
    (value: ElementSectionDescription, state: WordRendererState) => {
      value.description match {
        case d: Div => divRender.render(d, state)

        case l: JList => jlistRenderer.render(l, state)
      }
    }

  override lazy implicit val subsectionRenderer: OutRenderer[CV.Subsection] =
    (value: Subsection, state: WordRendererState) => {
      state.document.addParagraph(value.title)
      state.document.addParagraph(value.description)
      (state, ())
    }

  override lazy implicit val withSubsectionsSectionDescriptionRenderer: OutRenderer[CV.WithSubsectionsSectionDescription] =
    (value: WithSubsectionsSectionDescription, state: WordRendererState) => {
      state.document.addParagraph(value.title)
      value.subsections.foreach { subsection =>
        state.document.addParagraph(subsection.title)
        state.document.addParagraph(subsection.description)
      }
      (state, ())
    }

  override lazy implicit val elementRenderer: OutRenderer[CV.Element] =
    (value: Element, state: WordRendererState) => value match {
      case d: Div   => divRender.render(d, state)
      case l: JList => jlistRenderer.render(l, state)
    }

  override lazy implicit val divRender: OutRenderer[CV.Div] =
    (value: Div, state: WordRendererState) => value match {
      case d: ElementDiv => elemDivRenderer.render(d, state)
      case d: TextDiv  => textDivRenderer.render(d, state)
    }

  override lazy implicit val elemDivRenderer: OutRenderer[CV.ElementDiv] =
    (value: ElementDiv, state: WordRendererState) => {
      // Todo: Styles
      value.elems.foreach(e => elementRenderer.render(e, state))
      (state, ())
    }

  override lazy implicit val textDivRenderer: OutRenderer[CV.TextDiv] =
    (value: TextDiv, state: WordRendererState) => {
      state.document.addParagraph(value.text)
      (state, ())
    }

  override lazy implicit val listItemRenderer: OutRenderer[CV.ListItem] =
    (value: ListItem, state: WordRendererState) => value match {
      case li: ElementListItem => elemListItemRenderer.render(li, state)
      case li: TextListItem => textListItemRenderer.render(li, state)
    }

  override lazy implicit val elemListItemRenderer: OutRenderer[CV.ElementListItem] =
    (value: ElementListItem, state: WordRendererState) => {
      elementRenderer.render(value.elem, state.addList)
    }

  override lazy implicit val textListItemRenderer: OutRenderer[CV.TextListItem] =
    (value: TextListItem, state: WordRendererState) => {
      println(s"Rendering list item ${value.text}")
      state.curList.get.addItem(value.text)
      //state.document.addParagraph(value.text)
      (state, ())
    }

  override lazy implicit val jlistRenderer: OutRenderer[CV.JList] =
    (value: JList, state: WordRendererState) => {
      val stateWithList = state.addList
      println("Rendering jlist")
      value.items.foreach(e => listItemRenderer.render(e, stateWithList))
      (stateWithList.finishList, ())
    }

  implicit val cvRenderer: OutRenderer[CV] = new OutRenderer[CV] {
    val renderer: OutRenderer[Section] = implicitly[OutRenderer[Section]]

    override def render(cvData: CV, state: WordRendererState) = {

      state.document.addParagraph("Maximilian Wittmann, PhD").getOdfElement.setStyleName("Title")


////      state.document.addParagraph("").set
      val s1 = state.addList
      s1.curList.get.addItem("Doctor of Computer Science")
      s1.curList.get.addItem("Bachelor of Computer Science with First Class Honors")

      cvData.blurb.map(s1.document.addParagraph)

      s1.document.addParagraph("""The best way to reach me is at <a href="mailto:damxam@gmail.com?Subject=Your%20CV" target="_top">damxam@gmail.com</a>, or through <a href="http://au.linkedin.com/in/maximilianwittmann">LinkedIn</a>.</div>""")


      val s2 = renderer.render(cvData.experience, s1)._1
      val s3 = renderer.render(cvData.education, s2)._1
      val s4 = renderer.render(cvData.skills, s3)._1

      s4.document.addParagraph("""<div class="footer">You can also check out my <a href="https://github.com/waxmittmann">GitHub account</a> to see what kind of things I've been playing with.</div>""")
      (s4, ())
    }
  }

//  def write(outpath: String): Unit = {
//    document.save(outpath)
//  }
}
