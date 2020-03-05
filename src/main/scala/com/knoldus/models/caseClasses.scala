package com.knoldus.models

case class UserWithPost(user: User, post: List[Post])

case class PostWithComments(post: Post, comment: List[Comment])

case class Address(street: String, suite: String, city: String, zipcode: String, geo: Geo)

case class Geo(lat: String, lng: String)

case class Company(name: String, catchPhrase: String, bs: String)

case class User(id: Int, name: String, username: String, email: String, address: Address, phone: String, website: String, company: Company)

case class Post(userId: Int, id: Int, title: String, body: String)

case class Comment(postId: Int, id: Int, name: String, email: String, body: String)
