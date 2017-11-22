package templatey

import java.nio.file.{Files, Paths}

import templatey.RenderCV.{DatedSectionItem, ElemSectionDescription, Section, SimpleSectionDescription, SimpleSectionItem, Subsection, WithSubsectionsSectionDescription}

object GenCV {
  def main(args: Array[String]): Unit = {

    val premonition = DatedSectionItem(
      "May 2017 - Now:",
      "Senior Software Engineer @ Premonition",
      SimpleSectionDescription(
        "Since joining Premonition I’ve worked on three products, all in Scala, and all using core Scala libraries such as Scalaz, Http4s, Doobie and Argonaut. This includes developing a new back-end from scratch to support a new product, including a full suite of tests mocking a set of remote services."
      )
    )

    val cba = DatedSectionItem(
      "March 2016 – May 2017:",
      "Senior Software Engineer (Big data) @ CBA",
      SimpleSectionDescription(
        "During my time at CBA I worked on an internal product used to coordinate and transfer large volumes of data from a Hadoop-based big data cluster to internal consumers as part of the bank’s ETL pipeline. It was written largely in Scala and built on Cascading / Scalding.\nI also worked on a web-based DevOps tool developed to configure the application described earlier. It was written with a Scala backend (using Akka-Http) and a JavaScript frontend (using Angular)."
      )
    )

    val covata = DatedSectionItem(
      title = "Software Developer @ Covata Australia",
      dateSpan = "October 2014 - Now",
      ElemSectionDescription(
        <div>
          Employed as a full-stack software developer, responsible for developing a security application using a Java + Scala + Angular + Spring stack in an Agile team. In addition to software development skills, the role also requires aspects of a Business Analyst, with all team members expected to contribute to the ongoing design and improvement of the product.
          Some highlights so far:
          <ul>
            <li>Won our spike day for the development of an approach to non-destructively reorganizing data through alternate storage (currently serialization, but optimally use of a NoSQL database) of database views.</li>
            <li>Led the development of our (multi-client RESTful web-API) product's internationalization framework.</li>
          </ul>
        </div>
      )
    )

    val thoughtworks = DatedSectionItem(
      "Graduate Software Developer @ ThoughtWorks Australia",
      "October 2013 - October 2014",
      WithSubsectionsSectionDescription(
        "Employed as part of ThoughtWorks professional services (consulting services) as a software developer. During my year at ThoughtWorks I worked with several clients:",
        List(
          Subsection(
            title = "Software Developer @ a Major Australian bank",
            description = "Employed to perform ongoing improvement of an online insurance sales platform. The platform backend is Spring-based and it employs a single-page client-side MVC front-end built on Angular."
          ),
          Subsection(
            title = "DevOps @ a Major Australian bank",
            description = "Employed to transiton the client from a privately-hosted cloud located in a traditional data center to a cloud hosted using Amazon Web Services (AWS). Ansible was utilized to automatically deploy web applications to AWS and to provision the necessary infrastructure."
          ),
          Subsection(
            title = "Software Developer @ a Non-profit organization",
            description = "Employed to develop an online questionnaire web application for mobile devices. The web application was built on the Javascript node.js framework backed by a MySQL database."
          ),
          Subsection(
            title = "Continuous Integration consultant @ a Large beverage distributor",
            description = "Employed to introduce Agile quality assurance practices, including an automated testing framework and continuous integration (CI) facilitated by CI pipeline software. Responsibilities included setting up the framework and required technologies as well as supporting the local development team in adopting Agile development practices."
          )
        )
      )
    )

    val phd = DatedSectionItem(
      "2008-2013:",
      "Doctor of Computer Science @ Macquarie University, Australia",
      SimpleSectionDescription(
        "    The doctoral project, conducted with a MQRES (The Macquarie University Research Excellence Scholarships) involved the analysis of version repositories produced by students during their work on Computer Graphics assignment projects in order to identify student problems and issues with Computer Graphics programming. The analysis involved the development of a software tool for the analysis of version histories (called SCORE) as well as the development of software engineering methods for the machine-analysis of version histories.\n    During this time I was also involved in leading practical and tutorial sessions, as well as lecturing as a casual lecturer."
      )
    )

    val honors = DatedSectionItem(
      "2005-2007:",
      "Bachelor of Computer Science with Honors (First Class) @ Macquarie University, Australia",
      SimpleSectionDescription(
        "The honors project involved the development of a tool for the placement of objects in a room (developed with OpenGL). This tool was utilized to explore the nature of human memory through a controlled experiment in which participants were asked to reconstruct the room from memory and the order of their placement actions was recorded."
      )
    )

    val bachelors = DatedSectionItem(
      "2001-2005:",
      "Bachelor of Computer Science (GPA 3.743 of 4.0) @ Macquarie University, Australia",
      SimpleSectionDescription(
        "Completed my bachelor's degree at Macquarie University. I received letters of commendation for several of the courses I undertook and was involved with several societies such as the Buddhist Society, the Atheist Society, the MU Amnesty branch and Students for Peace and Justice."
      )
    )

    val programmingLanguages = SimpleSectionItem(
      title = "Programming Languages",
      description = ElemSectionDescription(
        <div class="mainSectionDescription">
          <div class="mainSubSectionItem">
            <div class="sectionTitle">Expert (2+ years full-time experience):</div>
            <ul class="skillsPanel">
              <li class="skill">Java</li>
              <li class="skill">Scala</li>
            </ul>
          </div>
          <div class="mainSubSectionItem">
            <div class="sectionTitle">Proficient (1+ year full-time experience):</div>
            <ul class="skillsPanel">
              <li class="skill">Python</li>
              <li class="skill">Javascript</li>
              <li class="skill">Html/CSS/Sass</li>
              <li class="skill">SQL</li>
            </ul>
          </div>
          <div class="mainSubSectionItem">
            <div class="sectionTitle">Some exposure (I've worked with this before):</div>
            <ul class="skillsPanel">
              <li class="skill">C/C++</li>
              <li class="skill">Clojure</li>
              <li class="skill">Haskell</li>
            </ul>
          </div>
        </div>
      )
    )

    val frameworks = SimpleSectionItem(
      title = "Frameworks and Technologies:",
      description = ElemSectionDescription(
        <div class="mainSectionDescription">
          <div class="mainSubSectionItem">
            <div class="sectionTitle">Expert (2+ years full-time experience):</div>
            <ul class="skillsPanel">
              <li class="skill">Git/Sourcetree</li>
            </ul>
          </div>
          <div class="mainSubSectionItem">
            <div class="sectionTitle">Proficient (1+ year full-time experience):</div>
            <ul class="skillsPanel">
              <li class="skill">Hadoop with Cascading / Scalding</li>
              <li class="skill">AWS</li>
              <li class="skill">Play</li>
              <li class="skill">Spring</li>
              <li class="skill">Angular</li>
            </ul>
          </div>
          <div class="mainSubSectionItem">
            <div class="sectionTitle">Some exposure:</div>
            <ul class="skillsPanel">
              <li class="skill">Spark</li>
              <li class="skill">Akka</li>
              <li class="skill">Ansible</li>
              <li class="skill">Node.js</li>
            </ul>
          </div>
        </div>
      )
    )

    val xhtml = RenderCV.cv(
      Section(List(premonition, cba, covata, thoughtworks)),
      Section(List(phd, honors, bachelors)),
      Section(List(programmingLanguages, frameworks))
    )

    println(xhtml)
    //Files.write(Paths.get("./cv.html"), xhtml.toString.toCharArray.map(_.toByte))
    Files.write(Paths.get("../index.html"), xhtml.toString.toCharArray.map(_.toByte))
  }
}
