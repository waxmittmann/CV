package templatey

import java.nio.charset.Charset
import java.nio.file.{Files, Path, Paths}

import scala.xml.Elem

object Main {

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

    val xhtml = cv(
      Section(List(premonition, cba, covata, thoughtworks)),
      Section(List(phd, honors, bachelors)),
      Section(List(programmingLanguages, frameworks))
    )

    println(xhtml)
    Files.write(Paths.get("./cv.html"), xhtml.toString.toCharArray.map(_.toByte))
  }

  sealed trait SectionDescription {
    def asElem: Elem
  }

  case class SimpleSectionDescription(
    title: String
  ) extends SectionDescription {
    override def asElem =
      <div class="sectionDescription">
        <div>{title}</div>
      </div>
  }

  case class ElemSectionDescription(
    description: Elem
  ) extends SectionDescription {
    override def asElem =
      <div class="sectionDescription">
        {description}
      </div>
  }

  case class Subsection(
    title: String,
    description: String
  )

  case class WithSubsectionsSectionDescription(
    title: String,
    subsections: List[Subsection]
  ) extends SectionDescription {
    override def asElem =
      <div class="sectionDescription">
        <div>{subsections.map(subsection)}</div>
      </div>
  }

  def subsection(subsection: Subsection): Elem =
    <div class="mainSubSectionItem">
      <div class="sectionTitle">{subsection.title}</div>
      <div class="sectionDescription">{subsection.description}</div>
    </div>


  trait RenderElem[S] {
    def render(value: S): Elem
  }

  implicit object RenderSectionItem extends RenderElem[SectionItem] {
    override def render(value: SectionItem): Elem = value match {
      case data @ DatedSectionItem(_, _, _)   => datedItem(data)
      case data @ SimpleSectionItem(_, _)     => simpleItem(data)
    }

    def datedItem(data: DatedSectionItem): Elem =
      <div class="mainSectionItem">
        <div class="dateSpan">{data.dateSpan}</div>
        <div class="sectionTitle">{data.title}</div>
        <div class="sectionDescription">{data.description.asElem}</div>
      </div>

    def simpleItem(data: SimpleSectionItem): Elem =
      <div class="mainSectionItem">
        <div class="sectionTitle">{data.title}</div>
        <div class="sectionDescription">{data.description.asElem}</div>
      </div>
  }

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

  case class Section(
    items: List[SectionItem]
  )

  class CV(
    experience: Section,
    education: Section,
    skills: Section
  )

  def section(header: String, section: Section)(implicit sectionItemRender: RenderElem[SectionItem]): Elem =
    <div class="mainSection">
      <h1 class="mainSectionHeader">{header}</h1>
      {section.items.map(sectionItemRender.render)}
    </div>

  def cv(
    experience: Section,
    education: Section,
    skills: Section
  ): Elem = <html>
    <head>
      <meta name="viewport" content="width=device-width, initial-scale=1"/>
        <link rel="stylesheet" type="text/css" href="./css/common.css" media="all"/>

        <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'/>
        </head>
        <body class="main">
          <h1 class="pageHeader">Maximilian Wittmann, PdD</h1>
          <ul class="academicTitles">
            <li class="academicTitle">Doctor of Computer Science</li>
            <li class="academicTitle">Bachelor of Computer Science with First Class Honors</li>
          </ul>

          <div class="aboutMeBlurb">
            <p>Since getting my first computer as a Christmas present (an Olivetti 386) I have been passionate about computing. An undergraduate degree in Computer Science reinforced that passion, so much so that I decided to pursue a doctorate. While the academic setting taught me many valuable lessons, I longed to be involved in developing software with direct practical applications.</p>
            <p>Moving to industry has allowed me to round out my skillset, to move beyond mere programming to the engineering of quality, well-tested software in a team environment. In addition my experience as a consultant has given me experience in dealing with stakeholders and has let me better understand the way in which software produces customer value, and how such value can be measured.</p>
            <p>I want to use this ten years' worth of programming experience in academic and industry settings to work with cutting-edge technologies, eschewing the well-trodden path in favor of new frontiers where I can contribute to making the world a simpler, more fun, and hopefully a little better place.</p>
          </div>
          <div class="contactMe">The best way to reach me is at <a href="mailto:damxam@gmail.com?Subject=Your%20CV" target="_top">damxam@gmail.com</a>, or through <a href="http://au.linkedin.com/in/maximilianwittmann">LinkedIn</a>.</div>

          <div class="cvMain">
            {section("Experience", experience)}
            {section("Education", education)}
            {section("Skills", skills)}
          </div>

          <div class="footer">You can also check out my <a href="">fledgling GitHub account</a>, the most interesting thing going on there at the moment is probably my <a href="">twiddling with Java 8 Lambdas</a>.</div>
        </body>
      </html>
    }

/*
            <div class="mainSection">
              <h1 class="mainSectionHeader">Experience</h1>
              <div class="mainSectionItem">
                <div class="dateSpan">October 2014 - Now:</div>
                <div class="sectionTitle">Software Developer @ Covata Australia</div>
                <div class="sectionDescription">Employed as a full-stack software developer, responsible for developing a security application using a Java + Scala + Angular + Spring stack in an Agile team. In addition to software development skills, the role also requires aspects of a Business Analyst, with all team members expected to contribute to the ongoing design and improvement of the product.
                  Some highlights so far:
                  <ul>
                    <li>Won our spike day for the development of an approach to non-destructively reorganizing data through alternate storage (currently serialization, but optimally use of a NoSQL database) of database views.</li>
                    <li>Led the development of our (multi-client RESTful web-API) product's internationalization framework.</li>
                  </ul>
                </div>
              </div>

              <div class="mainSectionItem">
                <div class="dateSpan">October 2013 - October 2014:</div>
                <div class="sectionTitle">Graduate Software Developer @ ThoughtWorks Australia</div>
                <div class="sectionDescription">
                  <div>Employed as part of ThoughtWorks professional services (consulting services) as a software developer. During my year at ThoughtWorks I worked with several clients:
                  </div>
                  <div class="mainSubSectionItem">
                    <div class="sectionTitle">Software Developer @ a Major Australian bank</div>
                    <div class="sectionDescription">Employed to perform ongoing improvement of an online insurance sales platform. The platform backend is Spring-based and it employs a single-page client-side MVC front-end built on Angular.</div>
                  </div>
                  <div class="mainSubSectionItem">
                    <div class="sectionTitle">DevOps @ a Major Australian bank</div>
                    <div class="sectionDescription">Employed to transiton the client from a privately-hosted cloud located in a traditional data center to a cloud hosted using Amazon Web Services (AWS). Ansible was utilized to automatically deploy web applications to AWS and to provision the necessary infrastructure.</div>
                  </div>
                  <div class="mainSubSectionItem">
                    <div class="sectionTitle">Software Developer @ a Non-profit organization</div>
                    <div class="sectionDescription">Employed to develop an online questionnaire web application for mobile devices. The web application was built on the Javascript node.js framework backed by a MySQL database.</div>
                  </div>
                  <div class="mainSubSectionItem">
                    <div class="sectionTitle">Continuous Integration consultant @ a Large beverage distributor</div>
                    <div class="sectionDescription">Employed to introduce Agile quality assurance practices, including an automated testing framework and continuous integration (CI) facilitated by CI pipeline software. Responsibilities included setting up the framework and required technologies as well as supporting the local development team in adopting Agile development practices. </div>
                  </div>
                </div>
              </div>

              <div class="mainSectionItem">
                <div class="dateSpan">2003 - 2012:</div>
                <div class="sectionTitle">Casual Tertiary Educator (Lecturer, Practical Supervisor and Tutorial Demonstrator) and Casual Research Assistant @ Macquarie University</div>
                <div class="sectionDescription">Employed as lecturer, tutorial demonstrator and practical supervisor for the unit "Introduction to Computer Graphics" and tutorial demonstrator / practical supervisor for many other courses. Responsibilities included the development of course materials (lectures, assignments and tutorial exercises) and face-to-face teaching.
                  Employed as research assistant to Dr. Matt Bower. Responsible for the production of a literature review for an ARC discovery project grant application.</div>
              </div>
            </div>

            <!-- Education -->
            <div class="mainSection">
              <h1 class="mainSectionTitle">Education</h1>
              <div class="mainSectionItem">
                <div class="dateSpan">2008 - 2013:</div>
                <div class="sectionTitle">Doctor of Computer Science @ Macquarie University, Australia</div>
                <div class="sectionDescription">The doctoral project, conducted with a MQRES (The Macquarie University Research Excellence Scholarships) involved the analysis of version repositories produced by students during their work on Computer Graphics assignment projects in order to identify student problems and issues with Computer Graphics programming. The analysis involved the development of a software tool for the analysis of version histories (called SCORE) as well as the development of software engineering methods for the machine-analysis of version histories.</div>
              </div>
              <div class="mainSectionItem">
                <div class="dateSpan">2005 - 2007:</div>
                <div class="sectionTitle">Bachelor of Computer Science with Honors (First Class) @ Macquarie University, Australia</div>
                <div class="sectionDescription">The honors project involved the development of a tool for the placement of objects in a room (developed with OpenGL). This tool was utilized to explore the nature of human memory through a controlled experiment in which participants were asked to reconstruct the room from memory and the order of their placement actions was recorded.</div>
              </div>
              <div class="mainSectionItem">
                <div class="dateSpan">2001 - 2005:</div>
                <div class="sectionTitle">Bachelor of Computer Science (GPA 3.743 of 4.0) @ Macquarie University, Australia</div>
              </div>
            </div>

            <div class="mainSection skillsSection">
              <h1 class="mainSectionTitle">Skills</h1>

              <div class="mainSectionItem">
                <div class="sectionTitle">Programming Languages:</div>
                <div class="mainSectionDescription">
                  <div class="mainSubSectionItem">
                    <div class="sectionTitle">Expert:</div>
                    <ul class="skillsPanel">
                      <li class="skill">Java</li>
                    </ul>
                  </div>
                  <div class="mainSubSectionItem">
                    <div class="sectionTitle">Proficient:</div>
                    <ul class="skillsPanel">
                      <li class="skill">Javascript</li>
                      <li class="skill">Scala</li>
                      <li class="skill">Html/(S)CSS</li>
                      <li class="skill">SQL</li>
                    </ul>
                  </div>
                  <div class="mainSubSectionItem">
                    <div class="sectionTitle">Some exposure:</div>
                    <ul class="skillsPanel">
                      <li class="skill">C/C++</li>
                      <li class="skill">Clojure</li>
                    </ul>
                  </div>
                </div>
              </div>

              <div class="mainSectionItem">
                <div class="sectionTitle">Frameworks and Technologies:</div>
                <div class="mainSectionDescription">
                  <div class="mainSubSectionItem">
                    <div class="sectionTitle">Proficient:</div>
                    <ul class="skillsPanel">
                      <li class="skill">Spring</li>
                      <li class="skill">JUnit (+Mockito/PowerMock)</li>
                      <li class="skill">Git/Sourcetree</li>
                    </ul>
                  </div>
                  <div class="mainSubSectionItem">
                    <div class="sectionTitle">Some exposure:</div>
                    <ul class="skillsPanel">
                      <li class="skill">Angular</li>
                      <li class="skill">Play</li>
                      <li class="skill">Node.js</li>
                      <li class="skill">Various Build/Dependency-management tools</li>
                    </ul>
                  </div>
                </div>
              </div>

              <div class="mainSectionItem">
                <div class="sectionTitle">Miscellaneous:</div>
                <div class="mainSectionDescription">
                  <div class="mainSubSectionItem">
                    <div class="sectionTitle">Expert:</div>
                    <ul class="skillsPanel">
                      <li class="skill">Eternal curiosity</li>
                    </ul>
                  </div>
                  <div class="mainSubSectionItem">
                    <div class="sectionTitle">Proficient:</div>
                    <ul class="skillsPanel">
                      <li class="skill">OsX, Linux, Windows</li>
                      <li class="skill">IntelliJ, Eclipse</li>
                    </ul>
                  </div>
                  <div class="mainSubSectionItem">
                    <div class="sectionTitle">Some exposure:</div>
                    <ul class="skillsPanel">
                      <li class="skill">AWS / DevOps</li>
                      <li class="skill">Insert buzzwords here!</li>
                    </ul>
                  </div>
                </div>
              </div>
 */