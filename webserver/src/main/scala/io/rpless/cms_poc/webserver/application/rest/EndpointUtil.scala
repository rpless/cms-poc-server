package io.rpless.cms_poc.webserver.application.rest

import com.twitter.finagle.http.Status
import io.finch._

trait EndpointUtil {
  protected def optionalOutput[A](opt: Option[A]): Output[A] =
    opt.fold(Output.empty[A](Status.NotFound))(Ok)
}
