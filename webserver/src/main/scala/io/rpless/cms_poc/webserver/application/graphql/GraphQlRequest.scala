package io.rpless.cms_poc.webserver.application.graphql

case class GraphQlRequest(query: String, operationName: Option[String], variables: Option[Map[String, String]])
