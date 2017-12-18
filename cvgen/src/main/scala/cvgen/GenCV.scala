package cvgen

import java.nio.file.{Files, Paths}

import scala.xml.Elem
import cvgen.CV._
import cvgen.RenderCV._
import cvgen.parser.JsonParser
import cvgen.renderers.ElemRenderer._
import io.circe

object GenCV {
  val str =
    """
      |{
      |    "title": "Skills",
      |    "items": [
      |      {
      |        "title": "Programming Languages",
      |        "description": {
      |          "description": {
      |            "styles": ["mainSectionDescription"],
      |            "elems": [
      |              {
      |                "styles": ["mainSubSectionItem"],
      |                "elems": [
      |                  {
      |                    "styles": ["sectionTitle"],
      |                    "text": "Expert (2+ years full-time experience):"
      |                  },
      |                  {
      |                    "styles": ["skillsPanel"],
      |                    "items": [
      |                      {
      |                        "styles": ["skill"],
      |                        "text": "Java"
      |                      },
      |                      {
      |                        "styles": ["skill"],
      |                        "text": "Scala"
      |                      }
      |                    ]
      |                  }
      |                ]
      |              },
      |              {
      |                "styles": ["mainSubSectionItem"],
      |                "elems": [
      |                  {
      |                    "styles": ["sectionTitle"],
      |                    "text": "Proficient (1+ year full-time experience):"
      |                  },
      |                  {
      |                    "styles": ["skillsPanel"],
      |                    "items": [
      |                      {
      |                        "styles": ["skill"],
      |                        "text": "Python"
      |                      },
      |                      {
      |                        "styles": ["skill"],
      |                        "text": "Javascript"
      |                      },
      |                      {
      |                        "styles": ["skill"],
      |                        "text": "Html/CSS/Sass"
      |                      },
      |                      {
      |                        "styles": ["skill"],
      |                        "text": "SQL"
      |                      }
      |                    ]
      |                  }
      |                ]
      |              },
      |              {
      |                "styles": ["mainSubSectionItem"],
      |                "elems": [
      |                  {
      |                    "styles": ["sectionTitle"],
      |                    "text": "Some exposure (I've worked with this before):"
      |                  },
      |                  {
      |                    "styles": ["skillsPanel"],
      |                    "items": [
      |                      {
      |                        "styles": ["skill"],
      |                        "text": "C/C++"
      |                      },
      |                      {
      |                        "styles": ["skill"],
      |                        "text": "Clojure"
      |                      },
      |                      {
      |                        "styles": ["skill"],
      |                        "text": "Haskell"
      |                      }
      |                    ]
      |                  }
      |                ]
      |              }
      |            ]
      |          }
      |        }
      |      },
      |      {
      |        "styles": ["mainSectionDescription"],
      |        "elems": [
      |          {
      |            "styles": ["mainSubSectionItem"],
      |            "elems": [
      |              {
      |                "styles": ["sectionTitle"],
      |                "text": "Expert (2+ years full-time experience):"
      |              },
      |              {
      |                "styles": ["skillsPanel"],
      |                "items": [
      |                  {
      |                    "styles": ["skill"],
      |                    "text": "Git"
      |                  }
      |                ]
      |              }
      |            ]
      |          },
      |          {
      |            "styles": ["mainSubSectionItem"],
      |            "elems": [
      |              {
      |                "styles": ["sectionTitle"],
      |                "text": "Proficient (1+ year full-time experience):"
      |              },
      |              {
      |                "styles": ["skillsPanel"],
      |                "items": [
      |                  {
      |                    "styles": ["skill"],
      |                    "text": "Cascading/Scalding"
      |                  },
      |                  {
      |                    "styles": ["skill"],
      |                    "text": "AWS"
      |                  },
      |                  {
      |                    "styles": ["skill"],
      |                    "text": "Http4s"
      |                  },
      |                  {
      |                    "styles": ["skill"],
      |                    "text": "Play Framework"
      |                  },
      |                  {
      |                    "styles": ["skill"],
      |                    "text": "Spring"
      |                  },
      |                  {
      |                    "styles": ["skill"],
      |                    "text": "Angular"
      |                  }
      |                ]
      |              }
      |            ]
      |          },
      |          {
      |            "styles": ["mainSubSectionItem"],
      |            "elems": [
      |              {
      |                "styles": ["sectionTitle"],
      |                "text": "Some exposure (I've worked with this before):"
      |              },
      |              {
      |                "styles": ["skillsPanel"],
      |                "items": [
      |                  {
      |                    "styles": ["skill"],
      |                    "text": "Spark"
      |                  },
      |                  {
      |                    "styles": ["skill"],
      |                    "text": "Akka"
      |                  },
      |                  {
      |                    "styles": ["skill"],
      |                    "text": "Ansible"
      |                  }
      |                ]
      |              }
      |            ]
      |          }
      |        ]
      |      }
      |    ]
      |  }
    """.stripMargin

  def main(args: Array[String]): Unit = {

//    import circe._
//    import circe.syntax._
//    import JsonParser.JsonCodecs._
//    import io.circe.parser._
//    val sec = decode[Section](str)
//    println(sec)

    val renderedCv: Either[circe.Error, Elem] = for {
      cv <- JsonParser.readFile("./src/main/resources/cv2.json")
    } yield {
      println(cv)
      cv.render[Elem]
    }

    renderedCv
      .map(cv => Files.write(Paths.get("../index.html"), cv.toString.toCharArray.map(_.toByte)))
      .left.map(err => throw new Exception(s"Failed to gen CV:\n$err"))

    //Files.write(Paths.get("../index.html"), renderedCv.toString.toCharArray.map(_.toByte))
  }
}
