package io.rpless.cms_poc.webserver.application.graphql

import java.io.File

import com.twitter.finagle.http.Response
import com.twitter.io.Reader
import io.finch._

object Development {
  val graphiql: Endpoint[Response] = get(/).mapAsync({ _ =>
    val file = new File(this.getClass.getClassLoader.getResource("graphiql.html").getFile)
    val reader: Reader = Reader.fromFile(file)
    Reader.readAll(reader).map({ buf =>
      val rep = Response()
      rep.content = buf
      rep.contentType = "text/html"
      rep
    })
  })
}
