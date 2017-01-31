package io.rpless.cms_poc.webserver.domain

case class Content private (id: Entity.Id, information: List[Information])

object Content {
  def apply(information: List[Information]): Content = Content(Entity.generateId, information)
}

case class Information(id: Entity.Id, text: String)

object Information {
  def apply(text: String): Information = Information(Entity.generateId, text)
}
