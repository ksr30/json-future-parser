package com.knoldus

import net.liftweb.json._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


class Parser(extractor: Extractor) {
  implicit val formats = DefaultFormats


  def parser[A](url: String)(implicit m: Manifest[A]): Future[List[A]] = {
    val futureData = extractor.extractor(url).map(parse(_))
    futureData.map(_.children.map(_.extract[A]))

  }


}

