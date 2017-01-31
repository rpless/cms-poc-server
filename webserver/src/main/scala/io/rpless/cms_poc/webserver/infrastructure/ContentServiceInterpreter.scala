package io.rpless.cms_poc.webserver.infrastructure

import io.rpless.cms_poc.webserver.domain.Entity._
import io.rpless.cms_poc.webserver.domain.{Content, ContentRepository, ContentService, Information}

object ContentServiceInterpreter extends ContentService {

  override def author(rawContent: List[String]): PCOperation[Id] = { repo: ContentRepository =>
    val information = rawContent.map(Information.apply)
    val content = Content(information)
    repo.storeContent(content)
  }

  override def removeContent(contentId: Id): PCOperation[Unit] = { repo: ContentRepository =>
    repo.removeContent(contentId)
  }

  override def readContent(contentId: Id): PCOperation[Option[Content]] = { repo: ContentRepository =>
    repo.contentById(contentId)
  }

  override def allContent(): PCOperation[List[Content]] = { repo: ContentRepository =>
    repo.content()
  }

  override def changeInformation(contentId: Id, information: Id, rawContent: String): PCOperation[Option[Content]] = {
    repo: ContentRepository => repo.changeContent(contentId, information, rawContent)
  }
}
