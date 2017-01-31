package io.rpless.cms_poc.webserver.domain

import java.util.UUID

object Entity {
  type Id = String

  def generateId: Entity.Id = UUID.randomUUID().toString
}
