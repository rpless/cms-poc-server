package io.rpless.cms_poc.webserver.domain

import com.twitter.util.Future

trait ContentService {
  type ContentOperation[A] = Future[A]
  type PCOperation[A] = ContentRepository => ContentOperation[A]

  def author(rawContent: List[String]): PCOperation[Entity.Id]

  def readContent(contentId: Entity.Id): PCOperation[Option[Content]]

  def allContent(): PCOperation[List[Content]]

  def changeInformation(contentId: Entity.Id, information: Entity.Id, rawContent: String): PCOperation[Option[Content]]

  def removeContent(contentId: Entity.Id): PCOperation[Unit]
}