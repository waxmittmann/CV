package templatey

import io.circe.{Decoder, Json}
import io.circe.parser.decode
import org.specs2.mutable.Specification
import templatey.CV._
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

      decode[SectionDescription](sectionItem) must beRight(SimpleSectionDescription("This is the section description"))
    }

    "correctly parse an elem section description" in {
      val sectionItem =
        """
          |{
          |   "description": "<test><sometagshere>blah</sometagshere></test>"
          |}
        """.stripMargin

      decode[SectionDescription](sectionItem) must beRight(ElemSectionDescription(<test><sometagshere>{"blah"}</sometagshere></test>))
    }

    "correctly parse a section description with subsections" in {
      val sectionItem =
        """
          |{
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

      decode[SectionDescription](sectionItem) must beRight(
        WithSubsectionsSectionDescription(
          "TITLE",
          List(
            Subsection("I am subsection", "Blah"),
            Subsection("More subsection", "Blah Blah")
          )
        )
      )
    }

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

      decode[SectionItem](sectionItem) must beRight(
        SectionItem.simple("My best experience", SimpleSectionDescription("This is the section description"))
      )
    }

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

      decode[SectionItem](sectionItem) must beRight(
        SectionItem.dated("Yesterday - Tomorrow", "My best experience", SimpleSectionDescription("This is the section description"))
      )
    }

    "correctly parse a section" in {
      val section =
        """
          |{
          |     "header": "title",
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

      decode[Section](section) must beRight(
        Section(
          "title",
          List(
            SectionItem.dated("Yesterday - Tomorrow", "My best experience", SimpleSectionDescription("This is the section description")),
            SectionItem.simple("My best experience", ElemSectionDescription(<h1>{"I am description!"}</h1>))
          )
        )
      )
    }

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
