package io.rpless.cms_poc.webserver.domain

import com.twitter.util.Future

trait ContentRepository {
  def storeContent(content: Content): Future[Entity.Id]
  def contentById(contentId: Entity.Id): Future[Option[Content]]
  def content(): Future[List[Content]]
  def changeContent(contentId: Entity.Id, informationId: Entity.Id, rawContent: String): Future[Option[Content]]
  def removeContent(contentId: Entity.Id): Future[Unit]
}
