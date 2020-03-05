package com.knoldus
import com.knoldus.models._
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{AsyncFlatSpec, BeforeAndAfterAll}
import net.liftweb.json._
import net.liftweb.json.Serialization.write

import scala.concurrent.Future

class ParserSpec extends AsyncFlatSpec with BeforeAndAfterAll with MockitoSugar{

  val extract:Extractor=mock[Extractor]
  val parse:Parser=new Parser(extract)

  val stubUserList = List(User(1, "1", "3", "4", Address("5", "6", "7", "8", Geo("9", "10")), "11", "12", Company("13", "14", "15")),User(2, "2", "3", "4", Address("5", "6", "7", "8", Geo("9", "10")), "11", "12", Company("13", "14", "15")))
  val stubPostList = List(Post(1, 2, "3", "4"),Post(2, 2, "3", "4"))
  val stubCommentsList = List(Comment(1, 2, "3", "4", "5"),Comment(2, 2, "3", "4", "5"))
  val userUrl = "https://jsonplaceholder.typicode.com/users"

  implicit val formats = DefaultFormats
  val userJsonString = write(stubUserList)


  when(extract.extractor(userUrl)).thenReturn(Future(userJsonString))

  "Parser Function" should "Parse user json format into User case class" in {
    val futureFind: Future[List[User]] = parse.parser[User](userUrl)
    futureFind map { find => assert(find == stubUserList) }
  }















}
