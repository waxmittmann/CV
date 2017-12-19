package cvgen.renderers

import scala.xml.Elem

import cvgen.{CV, CVRender}
import cvgen.CV._

object ElemRenderer extends CVRender {
  override type OUT = Elem

  type XmlRenderer[S] = OutRenderer[S]

  override lazy val textDivRenderer: ElemRenderer.OutRenderer[TextDiv] = (value: TextDiv) =>
    <div class={value.styles.mkString(" ")}>
      {value.text}
    </div>

  override lazy val elementRenderer: ElemRenderer.OutRenderer[Element] = {
    case div: Div => divRender.render(div)

    case list@JList(_, _) => jlistRenderer.render(list)
  }

  override lazy val elemDivRenderer: ElemRenderer.OutRenderer[ElementDiv] = (value: ElementDiv) =>
    <div class={value.styles.mkString(" ")}>
      {value.elems.map(elementRenderer.render)}
    </div>


  override lazy val divRender: ElemRenderer.OutRenderer[Div] = {
    case div: ElementDiv => {elemDivRenderer.render(div)}

    case div: TextDiv => {textDivRenderer.render(div)}
  }

  override lazy val elemListItemRenderer: ElemRenderer.OutRenderer[ElementListItem] = (value: ElementListItem) =>
    <li class={value.styles.mkString(" ")}>elementRenderer.render(item)</li>

  override lazy val textListItemRenderer: ElemRenderer.OutRenderer[TextListItem] = (value: TextListItem) =>
    <li class={value.styles.mkString(" ")}>
      {value.text}
    </li>

  override lazy val listItemRenderer: ElemRenderer.OutRenderer[ListItem] = {
    case d@ElementListItem(_, _) =>
      elemListItemRenderer.render(d)

    case d@TextListItem(_, _) =>
      textListItemRenderer.render(d)
  }

  override lazy val jlistRenderer: ElemRenderer.OutRenderer[JList] = (value: JList) =>
    <ul class={value.styles.mkString(" ")}>
      {value.items.map { item: ListItem =>
        {listItemRenderer.render(item)}
      }}
    </ul>

  implicit val simpleSectionDescriptionRenderer: ElemRenderer.OutRenderer[SimpleSectionDescription] = (value: SimpleSectionDescription) =>
    <div class="sectionDescription">
      <div>
        {value.title}
      </div>
    </div>

  implicit val elemSectionDescriptionRenderer: XmlRenderer[ElementSectionDescription]  = (value: ElementSectionDescription) =>
    <div class="sectionDescription">
      {elementRenderer.render(value.description)}
    </div>

  implicit val subsectionRenderer: ElemRenderer.OutRenderer[Subsection]  = (value: Subsection) => {
    <div class="mainSubSectionItem">
      <div class="sectionTitle">
        {value.title}
      </div>
      <div class="sectionDescription">
        {value.description}
      </div>
    </div>
  }

  implicit val withSubsectionsSectionDescriptionRenderer: XmlRenderer[WithSubsectionsSectionDescription] = new XmlRenderer[WithSubsectionsSectionDescription] {
    val abc: XmlRenderer[Subsection] = implicitly[XmlRenderer[Subsection]]

    override def render(value: WithSubsectionsSectionDescription) =
      <div class="sectionDescription">
        <div>{value.title}</div>
        <div>{
          value.subsections.map((s: Subsection) => abc.render(s))
        }</div>
      </div>
  }

  implicit val sectionDescriptionRenderer: XmlRenderer[SectionDescription] = {
    case d@SimpleSectionDescription(_) =>
      implicitly[XmlRenderer[SimpleSectionDescription]].render(d)

    case d@ElementSectionDescription(_) =>
      implicitly[XmlRenderer[ElementSectionDescription]].render(d)

    case d@WithSubsectionsSectionDescription(_, _) =>
      implicitly[XmlRenderer[WithSubsectionsSectionDescription]].render(d)
  }

  implicit val sectionItemRenderer: XmlRenderer[SectionItem] = new XmlRenderer[SectionItem] {
    override def render(value: SectionItem): Elem = value match {
      case SectionItem(title, Some(dateSpan), description)   => datedItem(title, dateSpan, description)
      case SectionItem(title, None, description)             => simpleItem(title, description)
    }

    val renderDescription: XmlRenderer[SectionDescription] = implicitly[XmlRenderer[SectionDescription]]

    def datedItem(title: String, dateSpan: String, description: SectionDescription): Elem =
      <div class="mainSectionItem">
        <div class="dateSpan">{dateSpan}</div>
        <div class="sectionTitle">{title}</div>
        <div class="sectionDescription">{renderDescription.render(description)}</div>
      </div>

    def simpleItem(title: String, description: SectionDescription): Elem =
      <div class="mainSectionItem">
        <div class="sectionTitle">{title}</div>
        <div class="sectionDescription">{renderDescription.render(description)}</div>
      </div>
  }

  implicit val sectionRenderer: XmlRenderer[Section] = new XmlRenderer[Section] {
    val renderer: XmlRenderer[SectionItem] = implicitly[XmlRenderer[SectionItem]]

    override def render(value: Section) =
      <div class="mainSection">
        <h1 class="mainSectionHeader">{value.title}</h1>
        {value.items.map(renderer.render)}
      </div>
  }

  implicit val cvRenderer: XmlRenderer[CV] = new XmlRenderer[CV] {
    val renderer: XmlRenderer[Section] = implicitly[XmlRenderer[Section]]

    override def render(cvData: CV) = {
      <html>
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
            {renderer.render(cvData.experience)}
            {renderer.render(cvData.education)}
            {renderer.render(cvData.skills)}
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
    }
  }
}
