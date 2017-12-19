package cvgen

import java.nio.file.{Files, Paths}

import scala.xml.Elem
import cvgen.CV._
import cvgen.RenderCV._
import cvgen.parser.JsonParser
import cvgen.renderers.ElemRenderer._
import io.circe

object GenCV {

  def main(args: Array[String]): Unit = {

    val renderedCv: Either[circe.Error, Elem] = for {
      cv <- JsonParser.readFile("./src/main/resources/cv.json")
    } yield {
      //println(cv)
      cv.render[Elem]
    }

    renderedCv
      .map(cv => Files.write(Paths.get("../index.html"), cv.toString.toCharArray.map(_.toByte)))
      .left.map(err => throw new Exception(s"Failed to gen CV:\n$err"))

    //Files.write(Paths.get("../index.html"), renderedCv.toString.toCharArray.map(_.toByte))
  }
}
