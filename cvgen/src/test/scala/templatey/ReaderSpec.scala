package templatey

import io.circe.{Decoder, Json}
import io.circe.parser.decode
import org.specs2.mutable.Specification
import templatey.CV.{Section, SectionDescription, SectionItem}
import templatey.Reader.JsonCodecs._
import templatey.Reader.{JsonCodecs, read}

object ReaderSpec extends Specification {

  "Reader" should {
    "correctly parse a simple section description" in {
      val sectionItem =
        """
          |{
          |   "title": "This is the section description"
          |}
        """.stripMargin

      println(decode[SectionDescription](sectionItem))
      ko
    }.pendingUntilFixed

    "correctly parse an elem section description" in {
      val sectionItem =
        """
          |{
          |   "description": "<test>\n<sometagshere>blah</sometagshere></test>"
          |}
        """.stripMargin

      println(decode[SectionDescription](sectionItem))
      ko
    }.pendingUntilFixed

    "correctly parse a section description with subsections" in {
      val sectionItem =
        """
          |"description": {
          |   "title": "TITLE",
          |   "subsections": [
          |     {
          |       "title": "I am subsection",
          |       "description": "Blah"
          |     },
          |     {
          |       "title": "More subsection",
          |       "description": "Blah Blah"
          |     }
          |   ]
          |}
        """.stripMargin

      println(decode[SectionDescription](sectionItem))
      ko
    }.pendingUntilFixed

    "correctly parse an undated section item" in {
      val sectionItem =
        """
          |       {
          |         "title": "My best experience",
          |         "description": {
          |           "title": "This is the section description"
          |         }
          |       }
        """.stripMargin

      println(JsonCodecs.sectionItemDecoder.decodeJson(Json.fromString(sectionItem)))
      ko
    }.pendingUntilFixed

    "correctly parse a dated section item" in {
      val sectionItem =
        """
          |       {
          |         "title": "My best experience",
          |         "dateSpan": "Yesterday - Tomorrow",
          |         "description": {
          |           "title": "This is the section description"
          |         }
          |       }
        """.stripMargin

      println(decode[SectionItem](sectionItem))
      ko
    }.pendingUntilFixed

    "correctly parse a section" in {
      val section =
        """
          |{
          |     "items": [
          |       {
          |         "title": "My best experience",
          |         "dateSpan": "Yesterday - Tomorrow",
          |         "description": {
          |           "title": "This is the section description"
          |         }
          |       },
          |       {
          |         "title": "My best experience",
          |         "description": {
          |           "description": "<h1>I am description!</h1>"
          |         }
          |       }
          |     ]
          |}
        """.stripMargin

      println(decode[Section](section))
      ko
    }.pendingUntilFixed

    "correctly parse a complete cv" in {
      val cv =
        """
          |{
          |  "blurb": [
          |    "This is a blurb.",
          |    "Yes indeed"
          |  ],
          |
          |  "experience": {
          |     "items": [
          |       {
          |         "title": "My best experience",
          |         "dateSpan": "Yesterday - Tomorrow",
          |         "description": {
          |           "title": "This is the section description"
          |         }
          |       },
          |       {
          |         "title": "My best experience",
          |         "description": {
          |           "description": "<h1>I am description!</h1>"
          |         }
          |       }
          |     ]
          |  },
          |
          |  "education": { "items": [
          |    {
          |      "title": "My best experience",
          |      "dateSpan": "Yesterday - Tomorrow",
          |      "description": {
          |        "title": "This is the section description",
          |        "subsections": [
          |          {
          |            "title": "SubsectionA",
          |            "description": "Blah"
          |          },
          |          {
          |            "title": "SubsectionB",
          |            "description": "Bleh"
          |          }
          |        ]
          |      }
          |    }
          |  ]},
          |
          |  "skills": { "items": [] }
          |}
        """.stripMargin

      println(read(cv))


      ko
    }.pendingUntilFixed
  }
}
