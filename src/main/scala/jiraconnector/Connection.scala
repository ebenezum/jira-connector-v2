package jiraconnector

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.headers.BasicHttpCredentials
import akka.http.scaladsl.unmarshalling.Unmarshal
import scala.concurrent.Future
import scala.util.{Failure, Success}
import scala.concurrent.Await
import scala.concurrent.duration._

object Connection {

  def newConnection(targetServer: String, user: String, pass: String) :String = {
    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()
    implicit val executionContext = system.dispatcher

    val authorization = headers.Authorization(BasicHttpCredentials(user, pass))

    val responseFuture: Future[HttpResponse] =
      Http()
      .singleRequest(
        HttpRequest(
          HttpMethods.GET,
          uri = targetServer+"/rest/api/3/project",
          headers = List(authorization)
        )
      )
      
      val result: HttpResponse = Await.result(responseFuture, Duration.Inf)
      val fresult:Future[String] = Unmarshal(result.entity).to[String]
      val morefinalresult:String = Await.result(fresult, Duration.Inf)
      return morefinalresult
  }
}
