package cvgen

import java.nio.file.{Files, Paths}
import scala.xml.Elem

import cvgen.CV._
import cvgen.RenderCV._
import cvgen.parser.JsonParser
import cvgen.renderers.ElemRenderer._

object GenCV {
  def main(args: Array[String]): Unit = {
    val renderedCv = for {
      cv <- JsonParser.readFile("./src/main/resources/cv.json")
    } yield {
      cv.render[Elem]
    }

    Files.write(Paths.get("../index.html"), renderedCv.toString.toCharArray.map(_.toByte))
  }
}
