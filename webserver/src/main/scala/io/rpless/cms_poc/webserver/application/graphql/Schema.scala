package io.rpless.cms_poc.webserver.application.graphql

import io.rpless.cms_poc.webserver.domain.{Content, ContentRepository, ContentService, Information}
import sangria.execution.deferred.{Deferred, DeferredResolver}
import sangria.macros.derive._
import sangria.schema._

import scala.concurrent.{ExecutionContext, Future}

trait Schema {
  import io.rpless.cms_poc.webserver.util.syntax._

  case class DeferContent(contentService: ContentService, contentRepo: ContentRepository) extends Deferred[List[Content]]
  class ContentResolver extends DeferredResolver[Any] {
    override def resolve(deferred: Vector[Deferred[Any]], ctx: Any, queryState: Any)(implicit ec: ExecutionContext): Vector[Future[Any]] = {
      deferred.map {
        case DeferContent(srv, repo) => srv.allContent()(contentRepo).asScala
      }
    }
  }

  val contentRepo: ContentRepository
  implicit val executionContext: ExecutionContext

  implicit val InformationType = deriveObjectType[Unit, Information](
    ObjectTypeDescription("Information for the content"),
    DocumentField("id", "The information's identifier"),
    DocumentField("text", "The actual information")
  )

  val ContentType = deriveObjectType[Unit, Content](
    ObjectTypeDescription("User created content"),
    DocumentField("id", "The content's identifier"),
    DocumentField("information", "The actual information")
  )

  val resolveContent = (contentService: ContentService) => {
    contentService.allContent()(contentRepo).asScala
  }

  val resolveSingle = (contentService: ContentService, id: String) => {
    contentService.readContent(id)(contentRepo).asScala
  }

  val Query = ObjectType("Query", fields[ContentService, Unit](
    Field(
      "content",
      OptionType(ContentType),
      arguments = Argument("id", StringType) :: Nil,
      resolve = ctx => resolveSingle(ctx.ctx, ctx.args.arg[String]("id"))
    ),
    Field(
      "contents",
      ListType(ContentType),
      resolve = { c => DeferContent(c.ctx, contentRepo) }
    )
  ))

  val schema = sangria.schema.Schema(Query)
}
