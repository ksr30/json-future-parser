package com.knoldus

import com.knoldus.models.{Comment, Post, PostWithComments, User, UserWithPost}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


class ClassListAggregator(parse: Parser) {

  def userPostMaker(userUrl: String, postUrl: String): Future[List[UserWithPost]] = {
    val futureUsers = parse.parser[User](userUrl)
    val futurePosts = parse.parser[Post](postUrl)

    futureUsers.map(users => futurePosts.map(posts => users.map(user => UserWithPost(user, posts.filter(user.id == _.userId))))).flatten
  }

  def postCommentMaker(postUrl: String, commentUrl: String): Future[List[PostWithComments]] = {
    val futurePosts = parse.parser[Post](postUrl)
    val futureComments = parse.parser[Comment](commentUrl)
    futurePosts.map(posts => futureComments.map(comments => posts.map(post => PostWithComments(post, comments.filter(post.id == _.postId))))).flatten
  }


}



