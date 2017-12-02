package cvgen.renderers

import cvgen.CV
import cvgen.CV._
import cvgen.RenderCV.Renderer

import scala.xml.Elem

object ElemRenderer {
  trait XmlRenderer[S] extends Renderer[S, Elem]

  implicit val simpleSectionDescriptionRenderer = new XmlRenderer[SimpleSectionDescription] {
    override def render(value: SimpleSectionDescription) =
      <div class="sectionDescription">
        <div>{value.title}</div>
      </div>
  }

  implicit val elemSectionRenderer = new XmlRenderer[ElemSectionDescription] {
    override def render(value: ElemSectionDescription) =
      <div class="sectionDescription">
        {value.description}
      </div>
  }

  implicit val subsectionRenderer = new XmlRenderer[Subsection] {
    override def render(value: Subsection) = {
      <div class="mainSubSectionItem">
        <div class="sectionTitle">{value.title}</div>
        <div class="sectionDescription">{value.description}</div>
      </div>
    }
  }

  implicit val withSubsectionsSectionDescription = new XmlRenderer[WithSubsectionsSectionDescription] {
    val subsectionRenderer = implicitly[XmlRenderer[Subsection]]

    override def render(value: WithSubsectionsSectionDescription) =
      <div class="sectionDescription">
        <div>{value.title}</div>
        <div>{value.subsections.map(subsectionRenderer.render)}</div>
      </div>
  }

  implicit val sectionDescriptionRenderer = new XmlRenderer[SectionDescription] {
    override def render(value: SectionDescription): Elem = value match {
      case d @ SimpleSectionDescription(_) =>
        implicitly[XmlRenderer[SimpleSectionDescription]].render(d)

      case d @ ElemSectionDescription(_) =>
        implicitly[XmlRenderer[ElemSectionDescription]].render(d)

      case d @ WithSubsectionsSectionDescription(_, _) =>
        implicitly[XmlRenderer[WithSubsectionsSectionDescription]].render(d)
    }
  }

  implicit val sectionItemRenderer = new XmlRenderer[SectionItem] {
    override def render(value: SectionItem): Elem = value match {
      case SectionItem(title, Some(dateSpan), description)   => datedItem(title, dateSpan, description)
      case SectionItem(title, None, description)             => simpleItem(title, description)
    }

    val renderDescription = implicitly[XmlRenderer[SectionDescription]]

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

  implicit val sectionRenderer = new XmlRenderer[Section] {
    val sectionItemRenderer = implicitly[XmlRenderer[SectionItem]]

    override def render(value: Section) =
      <div class="mainSection">
        <h1 class="mainSectionHeader">{value.title}</h1>
        {value.items.map(sectionItemRenderer.render)}
      </div>
  }

  implicit val cvRenderer = new XmlRenderer[CV] {
    val sectionRenderer = implicitly[XmlRenderer[Section]]

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
            {sectionRenderer.render(cvData.experience)}
            {sectionRenderer.render(cvData.education)}
            {sectionRenderer.render(cvData.skills)}
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

