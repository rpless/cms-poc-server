package io.rpless.cms_poc.webserver.application.graphql

import com.twitter.util.Future
import io.circe.Json
import io.circe.generic.auto._
import io.finch._
import io.finch.circe.dropNullKeys._
import io.rpless.cms_poc.webserver.domain.{ContentRepository, ContentService}
import sangria.ast.Document
import sangria.execution.Executor
import sangria.marshalling.circe._
import sangria.parser.QueryParser

import scala.util.{Failure, Success}

class GraphQlEndpoints(contentService: ContentService, override val contentRepo: ContentRepository) extends Schema {

  override implicit val executionContext = scala.concurrent.ExecutionContext.global

  private val graphqlRoot = "graphql"

  val routes =
    post("graphqlRoot" :: jsonBody[GraphQlRequest])(fetch _) :+:
    Development.graphiql

  def fetch(request: GraphQlRequest): Future[Output[Json]] = {
    attemptQuery(request).map(Ok).handle({ case ex: Exception => BadRequest(ex) })
  }

  def attemptQuery(request: GraphQlRequest): Future[Json] = {
    QueryParser.parse(request.query) match {
      case Success(document) => executeQuery(document)
      case Failure(ex)       => Future.exception(ex)
    }
  }

  def executeQuery(document: Document): Future[Json] = {
    import io.rpless.cms_poc.webserver.util.syntax._
    Executor.execute(
      schema, document, userContext = contentService, deferredResolver = new ContentResolver
    ).asTwitter
  }
}
