package com.knoldus


import org.apache.commons.io.IOUtils
import org.apache.http.client.methods.HttpGet
import scala.concurrent.ExecutionContext.Implicits.global
import org.apache.http.impl.client.HttpClientBuilder

import scala.concurrent.Future

class Extractor {


  def extractor(url:String):Future[String]={
    val request = new HttpGet(url)
    val client = HttpClientBuilder.create().build()
    Future {
      val response = client.execute(request)
      IOUtils.toString(response.getEntity().getContent())
    }
  }

}

