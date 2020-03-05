package com.knoldus

import com.knoldus.models._
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{AsyncFlatSpec, BeforeAndAfterAll}

import scala.concurrent.Future

class MaxFindSpec extends AsyncFlatSpec with BeforeAndAfterAll with MockitoSugar {

  val classListAggregate:ClassListAggregator = mock[ClassListAggregator]
  val parse:Parser=mock[Parser]

  val maxFind: MaxFind = new MaxFind(classListAggregate,parse)

  val userUrl = "https://jsonplaceholder.typicode.com/users"
  val postUrl = "https://jsonplaceholder.typicode.com/posts"
  val commentUrl = "https://jsonplaceholder.typicode.com/comments"
  //val futureUserWithPost= Future(List(UserWithPost(User(id=1, name="Ksr", username="ksr30", email="gmail.com", address=Address(street= "street", suite="suite", city="city", zipcode="333", geo= Geo(lat="lat", lng="33")), phone="phone", website="website", company=Company(name="knoldus", catchPhrase="phase", bs="ddd")))))
  val stubUserList = List(User(1, "1", "3", "4", Address("5", "6", "7", "8", Geo("9", "10")), "11", "12", Company("13", "14", "15")),User(2, "2", "3", "4", Address("5", "6", "7", "8", Geo("9", "10")), "11", "12", Company("13", "14", "15")))
  val stubPostList = List(Post(1, 2, "3", "4"),Post(2, 2, "3", "4"))
  val stubCommentsList = List(Comment(1, 2, "3", "4", "5"),Comment(2, 2, "3", "4", "5"))

  val stubUserWithPostList=List(UserWithPost(stubUserList(0),stubPostList),UserWithPost(stubUserList(1),stubPostList))
  val stubPostWithCommentList=List(PostWithComments(stubPostList(0),stubCommentsList),PostWithComments(stubPostList(1),stubCommentsList))


  when(classListAggregate.userPostMaker(userUrl,postUrl)).thenReturn(Future(stubUserWithPostList))
  when(classListAggregate.postCommentMaker(postUrl,commentUrl)).thenReturn(Future(stubPostWithCommentList))
  when(parse.parser[User](userUrl)).thenReturn(Future(stubUserList))
  //when(service.login("johndoe", "secret")).thenReturn(Some(User("johndoe")))


  "Max Finder" should "eventually find user with maximum post" in {
    val futureFind: Future[String] = maxFind.maxFinder("Post")
    futureFind map { find => assert(find == "2") }
  }

  it should "eventually find user with maximum comments" in {
    val futureFindComment: Future[String] = maxFind.maxFinder("Comment")
    futureFindComment map { find => assert(find == "2") }
  }


}
