package models

import com.mongodb.casbah.Imports._
import play.api.Play.current
import se.radley.plugin.salat._
import com.mongodb.casbah.MongoCollection
import com.mongodb.DBObject
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.commons.MongoDBList
import java.util.Date

trait BlogPostDAOComponent {

  def blogPostDAO: BlogPostDAO

  trait BlogPostDAO {

    def findByPermalink(permalink: String): Option[DBObject]

    def findByDateDescending(limit: Int): Seq[DBObject]

    def findByTagDateDescending(tag: String): Seq[DBObject]

    def addPost(title: String, body: String, tags: Seq[String], username: String): String

    def addPostComment(name: String, email: Option[String], body: String, permalink: String): Unit

  }

}

trait MongoBlogPostDAOComponent extends BlogPostDAOComponent {

  override val blogPostDAO = new MongoBlogPostDAO(mongoCollection("posts"))

  class MongoBlogPostDAO(posts: MongoCollection) extends BlogPostDAO {

    // Return a single post corresponding to a permalink 
    override def findByPermalink(permalink: String): Option[DBObject] = posts.findOne(MongoDBObject("permalink" -> permalink))

    // Return a list of posts in descending order. Limit determines
    // how many posts are returned. 
    override def findByDateDescending(limit: Int): Seq[DBObject] =
      posts.find()
        .sort(MongoDBObject("date" -> -1))
        .limit(limit)
        .toSeq

    override def findByTagDateDescending(tag: String): Seq[DBObject] =
      posts.find(MongoDBObject("tags" -> tag))
        .sort(MongoDBObject("date" -> -1))
        .limit(10)
        .toSeq

    override def addPost(title: String, body: String, tags: Seq[String], username: String): String = {
      println("inserting blog entry " + title + " " + body)

      val permalink = title
        .replaceAll("\\s", "_") // whitespace becomes _
        .replaceAll("\\W", "") // get rid of non alphanumeric 
        .toLowerCase()

      val post = MongoDBObject.newBuilder

      post += "title" -> title
      post += "author" -> username
      post += "body" -> body
      post += "permalink" -> permalink
      post += "tags" -> tags
      post += "comments" -> MongoDBList.empty
      post += "date" -> new Date

      // Build the post object and insert it 
      posts += post.result

      permalink
    }

    // Append a comment to a blog post 
    override def addPostComment(name: String, email: Option[String], body: String, permalink: String): Unit = {

      val comment = MongoDBObject.newBuilder
      comment += "author" -> name
      comment += "body" -> body
      email foreach { comment += "email" -> _ }

      posts.update(
        MongoDBObject("permalink" -> permalink),
        MongoDBObject("$push" -> MongoDBObject("comments" -> comment.result)))
    }

  }
}

