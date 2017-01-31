package io.rpless.cms_poc.webserver.application.rest

import io.finch._
import io.rpless.cms_poc.webserver.domain.{ContentRepository, ContentService}

class ContentEndpoints(contentService: ContentService, contentRepo: ContentRepository) extends EndpointUtil {

  private val contentRoot = "api" :: "content"

  val routes =
    get(contentRoot :: string)((id: String) => contentService.readContent(id)(contentRepo).map(optionalOutput)) :+:
    get(contentRoot)({ () => contentService.allContent()(contentRepo).map(Ok) })
}
