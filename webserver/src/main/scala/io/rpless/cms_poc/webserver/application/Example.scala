package io.rpless.cms_poc.webserver.application

import com.twitter.finagle.Http
import com.twitter.util.Await
import io.finch._
import io.finch.circe.dropNullKeys._
import io.circe.generic.auto._
import io.rpless.cms_poc.webserver.application.graphql.GraphQlEndpoints
import io.rpless.cms_poc.webserver.application.rest.ContentEndpoints
import io.rpless.cms_poc.webserver.domain.{Content, Information}
import io.rpless.cms_poc.webserver.infrastructure.{ContentRepositoryInMemory, ContentServiceInterpreter}

object Example extends App {
  val taterbase = scala.collection.mutable.Map(
    "1" -> Content("1", List.empty),
    "2" -> Content("2", List(Information("1", "Hi Dan!")))
  )
  val contentRepo = new ContentRepositoryInMemory(taterbase)

  val graphql = new GraphQlEndpoints(ContentServiceInterpreter, contentRepo)
  val rest    = new ContentEndpoints(ContentServiceInterpreter, contentRepo)

  val endpoints = graphql.routes :+: rest.routes

  Await.ready(Http.server.serve(":8081", endpoints.toServiceAs[Application.Json]))
}
