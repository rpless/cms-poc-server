package io.rpless.cms_poc.webserver.infrastructure

import com.twitter.util.Future
import io.rpless.cms_poc.webserver.domain.{Content, ContentRepository, Entity}

class ContentRepositoryInMemory(backingStore: collection.mutable.Map[Entity.Id, Content])
  extends ContentRepository {

  override def storeContent(content: Content): Future[Entity.Id] = {
    backingStore += (content.id -> content)
    Future.value(content.id)
  }

  override def removeContent(contentId: Entity.Id): Future[Unit] = {
    backingStore -= contentId
    Future.Unit
  }

  override def contentById(contentId: Entity.Id): Future[Option[Content]] = {
    Future.value(backingStore.get(contentId))
  }

  override def changeContent(contentId: Entity.Id, informationId: Entity.Id, rawContent: String): Future[Option[Content]] = {
    val content = for {
      content     <- backingStore.get(contentId)
      information <- content.information.find(_.id == informationId)
    } yield {
      val updated = content.information.map(i => if (i.id == informationId) i.copy(text = rawContent) else i)
      content.copy(information = updated)
    }
    Future.value(content)
  }

  override def content(): Future[List[Content]] = {
    Future.value(backingStore.values.toList)
  }
}
