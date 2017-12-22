package cvgen.renderers

import java.io.{File, FileOutputStream}

import cvgen.CV._
import cvgen.renderers.ElemRenderer.XmlRenderer
import cvgen.renderers.OdtFormat.{FontSize, Normal, Small}
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy
import org.apache.poi.xwpf.usermodel.XWPFDocument
import cvgen.{CV, CVRender, StatefulCVRender}
import org.apache.poi.wp.usermodel.Paragraph
import org.odftoolkit.simple.TextDocument
import org.odftoolkit.simple.text.list.{List => OdtList}

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


class OdtRenderer extends StatefulCVRender {
  def render(cv: CV) = cvRenderer.render(cv, WordRendererState())._1

  //  override lazy type OUT = WordDocument

  case class WordRendererState private() {
//    def lineBreak = content.append(OdtFormat.lineBreak)
//    def lineBreak2 = content.append(OdtFormat.lineBreak2)

    // Todo: Should temp buffer and only write on finish
    class TextBuilder(fontSize: FontSize = Normal) {
      def plain(text: String): TextBuilder = {
        content.append(text)
        this
      }

      def link(url: String, text: String): TextBuilder = {
        content.append(OdtFormat.hyperlink(text, url, fontSize))
        this
      }

      def finish(): Unit = content.append(OdtFormat.endParagraph)
    }

    def text(fontSize: FontSize = Normal): TextBuilder = {
      content.append(OdtFormat.startParagraph(fontSize))
      new TextBuilder(fontSize)
    }

    def styles = content.append(OdtFormat.styles)

    def title(str: String) = content.append(OdtFormat.title(str))
    def subtitle(str: String) = content.append(OdtFormat.subtitle(str))

    def header = content.append(OdtFormat.header)
    def contentHeader = content.append(OdtFormat.contentHeader)

    def footer = content.append(OdtFormat.footer)
    def contentFooter = content.append(OdtFormat.contentFooter)

    private val content = new StringBuilder()

//    def list(contentsFn: => String): WordRendererState = {
//      content.append(OdtFormat.)
//
//      content.append(OdtFormat.listHeader)
//      content.append(contentsFn)
//      content.append(OdtFormat.listFooter)
//    }
//

    // Don't use
//    def append(str: String): Unit = content.append(OdtFormat.escapeXml(str))

    def heading1(str: String): Unit = content.append(OdtFormat.heading1(str))
    def heading2(str: String): Unit = content.append(OdtFormat.heading2(str))

    def textParagraph(str: String): Unit = content.append(OdtFormat.textParagraph(str))

    def listItem(str: String): Unit = content.append(OdtFormat.listItem(str))

    def listHeader(i: Int) = content.append(OdtFormat.listHeader(0))

    def listFooter = content.append(OdtFormat.listFooter)

    def write(outpath: String): Unit = {
      val fos = new FileOutputStream(new File(outpath))
      fos.write(content.toString().getBytes)
    }
  }

  override type STATE = WordRendererState
  override type OUT = Unit

//  val document: XWPFDocument = new XWPFDocument()

    //document.addList()

  override lazy implicit val sectionRenderer: OutRenderer[CV.Section] =
    (value: CV.Section, state: WordRendererState) => {
      state.heading1(value.title)
      value.items.foreach(i => sectionItemRenderer.render(i, state))
      (state, ())
    }

  override lazy implicit val sectionItemRenderer: OutRenderer[CV.SectionItem] =
    (value: CV.SectionItem, state: WordRendererState) => {
      state.heading2(value.title)
      value.dateSpan.foreach(v => state.heading2(v))
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
      state.textParagraph(value.title)
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
      state.textParagraph(value.title)
      state.textParagraph(value.description)
      (state, ())
    }

  override lazy implicit val withSubsectionsSectionDescriptionRenderer: OutRenderer[CV.WithSubsectionsSectionDescription] =
    (value: WithSubsectionsSectionDescription, state: WordRendererState) => {
      state.textParagraph(value.title)
      value.subsections.foreach { subsection =>
        state.textParagraph(subsection.title)
        state.textParagraph(subsection.description)
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
      state.textParagraph(value.text)
      (state, ())
    }

  override lazy implicit val listItemRenderer: OutRenderer[CV.ListItem] =
    (value: ListItem, state: WordRendererState) => value match {
      case li: ElementListItem => elemListItemRenderer.render(li, state)
      case li: TextListItem => textListItemRenderer.render(li, state)
    }

  override lazy implicit val elemListItemRenderer: OutRenderer[CV.ElementListItem] =
    (value: ElementListItem, state: WordRendererState) => {
      elementRenderer.render(value.elem, state)
    }

  override lazy implicit val textListItemRenderer: OutRenderer[CV.TextListItem] =
    (value: TextListItem, state: WordRendererState) => {
      println(s"Rendering list item ${value.text}")
      state.listItem(value.text)
      //state.document.addParagraph(value.text)
      (state, ())
    }

  override lazy implicit val jlistRenderer: OutRenderer[CV.JList] =
    (value: JList, state: WordRendererState) => {
      state.listHeader(0)
//      state.append(OdtFormat.listHeader(0))
      value.items.foreach(e => listItemRenderer.render(e, state))
      state.listFooter
//      state.lineBreak2
//      state.lineBreak
      state.textParagraph("")
//      state.lineBreak
      (state, ())
    }

  implicit val cvRenderer: OutRenderer[CV] = new OutRenderer[CV] {
    val renderer: OutRenderer[Section] = implicitly[OutRenderer[Section]]

    override def render(cvData: CV, state: WordRendererState) = {

      state.header
      state.styles
      state.contentHeader
//      state.append(OdtFormat.header)
//      state.append(OdtFormat.contentHeader)

      state.title("Maximilian Wittmann, PhD")
//      state.append(OdtFormat.title("Maximilian Wittmann, PhD"))
      //state.document.addParagraph("Maximilian Wittmann, PhD").getOdfElement.setStyleName("Title")


////      state.document.addParagraph("").set

//      state.listHeader(0)
//      state.append(OdtFormat.listHeader(0))

      state.subtitle("Doctor of Computer Science")
      state.subtitle("Bachelor of Computer Science with First Class Honors")

//      state.append(OdtFormat.subtitle("Doctor of Computer Science"))
//      state.append(OdtFormat.subtitle("Bachelor of Computer Science with First Class Honors"))
//      state.append(OdtFormat.listFooter)

//      state.listFooter

      // Todo
//      cvData.blurb.map(s1.document.addParagraph)

      // Todo: This correctly, also escape xml
      //state.textParagraph("""The best way to reach me is at <a href="mailto:damxam@gmail.com?Subject=Your%20CV" target="_top">damxam@gmail.com</a>, or through <a href="http://au.linkedin.com/in/maximilianwittmann">LinkedIn</a>.</div>""")
      state
        .text(Small)
        .plain("The best way to reach me is at ")
        .link("mailto:damxam@gmail.com?Subject=Your%20CV", "damxam@gmail.com")
        .plain(", or through ")
        .link("http://au.linkedin.com/in/maximilianwittmann", "LinkedIn")
        .finish()


      val s2 = renderer.render(cvData.experience, state)._1
      val s3 = renderer.render(cvData.education, state)._1
      val s4 = renderer.render(cvData.skills, state)._1

      state
        .text(Small)
        .plain("You can also check out my ")
        .link("https://github.com/waxmittmann", "GitHub account")
        .plain(" to see what kind of things I've been playing with.")
        .finish()

      state.contentFooter
      state.footer
      (s4, ())
    }
  }

//  def write(outpath: String): Unit = {
//    document.save(outpath)
//  }
}
