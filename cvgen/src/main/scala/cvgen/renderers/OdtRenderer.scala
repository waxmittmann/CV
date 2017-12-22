package cvgen.renderers

import java.io.{File, FileOutputStream}
import scala.collection.JavaConverters._

import cvgen.CV._
import cvgen.renderers.OdtFormat.{FontSize, Normal, Small}
import cvgen.{CV, StatefulCVRender}

class OdtRenderer extends StatefulCVRender {
  case class WordRendererState private() {
    private val content = new StringBuilder()

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

    def styles(): Unit = content.append(OdtFormat.styles)
    def header(): Unit = content.append(OdtFormat.header)
    def contentHeader(): Unit = content.append(OdtFormat.contentHeader)
    def footer(): Unit = content.append(OdtFormat.footer)
    def contentFooter(): Unit = content.append(OdtFormat.contentFooter)

    def title(str: String): Unit = content.append(OdtFormat.title(str))
    def subtitle(str: String): Unit = content.append(OdtFormat.subtitle(str))
    def heading1(str: String): Unit = content.append(OdtFormat.heading1(str))
    def heading2(str: String): Unit = content.append(OdtFormat.heading2(str))
    def textParagraph(str: String): Unit = content.append(OdtFormat.textParagraph(str))
    def listItem(str: String): Unit = content.append(OdtFormat.listItem(str))
    def listHeader(i: Int): Unit = content.append(OdtFormat.listHeader(0))
    def listFooter(): Unit = content.append(OdtFormat.listFooter)
    def sectionHeading(date: String, title: String): Unit = content.append(OdtFormat.sectionHeading(date, title))
    def text(fontSize: FontSize = Normal): TextBuilder = {
      content.append(OdtFormat.startParagraph(fontSize))
      new TextBuilder(fontSize)
    }

    def write(outpath: String): Unit = {
      val fos = new FileOutputStream(new File(outpath))
      fos.write(content.toString().getBytes)
    }
  }

  override type STATE = WordRendererState
  override type OUT = Unit

  override lazy implicit val sectionRenderer: OutRenderer[CV.Section] =
    (value: CV.Section, state: WordRendererState) => {
      state.heading1(value.title)
      value.items.foreach(i => sectionItemRenderer.render(i, state))
      (state, ())
    }

  override lazy implicit val sectionItemRenderer: OutRenderer[CV.SectionItem] =
    (value: CV.SectionItem, state: WordRendererState) => {
      value.dateSpan.map { ds =>
        state.sectionHeading(ds, value.title)
      }.getOrElse(state.heading2(value.title))

//      state.heading2(value.title)
//      value.dateSpan.foreach(v => state.heading2(v))
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
      state.listItem(value.text)
      //state.document.addParagraph(value.text)
      (state, ())
    }

  override lazy implicit val jlistRenderer: OutRenderer[CV.JList] =
    (value: JList, state: WordRendererState) => {
      state.listHeader(0)
      value.items.foreach(e => listItemRenderer.render(e, state))
      state.listFooter
      state.textParagraph("")
      (state, ())
    }

  implicit val cvRenderer: OutRenderer[CV] = new OutRenderer[CV] {
    val renderer: OutRenderer[Section] = implicitly[OutRenderer[Section]]

    override def render(cvData: CV, state: WordRendererState) = {

      state.header
      state.styles
      state.contentHeader

      state.title("Maximilian Wittmann, PhD")

      state.subtitle("Doctor of Computer Science")
      state.subtitle("Bachelor of Computer Science with First Class Honors")

      // Todo
//      cvData.blurb.map(s1.document.addParagraph)

      state
        .text(Small)
        .plain("The best way to reach me is at ")
        .link("mailto:damxam@gmail.com?Subject=Your%20CV", "damxam@gmail.com")
        .plain(", or through ")
        .link("http://au.linkedin.com/in/maximilianwittmann", "LinkedIn")
        .finish()


      renderer.render(cvData.experience, state)._1
      renderer.render(cvData.education, state)._1
      renderer.render(cvData.skills, state)._1

      state
        .text(Small)
        .plain("You can also check out my ")
        .link("https://github.com/waxmittmann", "GitHub account")
        .plain(" to see what kind of things I've been playing with.")
        .finish()

      state.contentFooter
      state.footer
      (state, ())
    }
  }

  def render(cv: CV): WordRendererState = cvRenderer.render(cv, WordRendererState())._1
}
