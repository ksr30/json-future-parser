package com.knoldus

import com.knoldus.models._
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{AsyncFlatSpec, BeforeAndAfterAll}

import scala.concurrent.Future

class ClassListAggregatorSpec extends AsyncFlatSpec with BeforeAndAfterAll with MockitoSugar {
  val parse: Parser = mock[Parser]
  val classListAggregator: ClassListAggregator = new ClassListAggregator(parse)

  val userUrl = "https://jsonplaceholder.typicode.com/users"
  val postUrl = "https://jsonplaceholder.typicode.com/posts"
  val commentUrl = "https://jsonplaceholder.typicode.com/comments"

  val stubUserList = List(User(1, "1", "3", "4", Address("5", "6", "7", "8", Geo("9", "10")), "11", "12", Company("13", "14", "15")), User(2, "2", "3", "4", Address("5", "6", "7", "8", Geo("9", "10")), "11", "12", Company("13", "14", "15")))
  val stubPostList = List(Post(1, 2, "3", "4"), Post(2, 2, "3", "4"))
  val stubCommentsList = List(Comment(1, 2, "3", "4", "5"), Comment(2, 2, "3", "4", "5"))

  val stubUserWithPostList = List(UserWithPost(stubUserList(0), List(stubPostList(0))), UserWithPost(stubUserList(1), List(stubPostList(1))))
  val stubPostWithCommentList = List(PostWithComments(stubPostList(0), List(stubCommentsList(1))), PostWithComments(stubPostList(1), List(stubCommentsList(1))))

  when(parse.parser[User](userUrl)).thenReturn(Future(stubUserList))
  when(parse.parser[Post](postUrl)).thenReturn(Future(stubPostList))
  when(parse.parser[Comment](commentUrl)).thenReturn(Future(stubCommentsList))
  //when(classListAggregate.userPostMaker(userUrl,postUrl)).thenReturn(Future(stubUserWithPostList))

  "User Post Maker" should "eventually return list of future of User With Posts" in {
    val futureFind: Future[List[UserWithPost]] = classListAggregator.userPostMaker(userUrl, postUrl)
    futureFind map { find => assert(find == stubUserWithPostList) }
  }

  "Post Comment Maker" should "eventually return list of future of Posts with Comments" in {
    val futureFind: Future[List[PostWithComments]] = classListAggregator.postCommentMaker(postUrl, commentUrl)
    futureFind map { find => assert(find == stubPostWithCommentList) }
  }


}
