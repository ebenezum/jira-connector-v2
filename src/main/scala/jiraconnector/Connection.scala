package jiraconnector

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.headers.BasicHttpCredentials
import scala.concurrent.Future
import scala.util.{Failure, Success}

object Connection {

  def newConnection(targetServer: String, user: String, pass: String) {
    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()
    implicit val executionContext = system.dispatcher

    val authorization = headers.Authorization(BasicHttpCredentials(user, pass))

    val responseFuture: Future[HttpResponse] =
      Http().singleRequest(
        HttpRequest(
          HttpMethods.GET,
          uri = targetServer+"/rest/api/3/project",
          headers = List(authorization)
        )
      )

    responseFuture
      .onComplete {
        case Success(res) => println(res.entity.discardBytes())
        case Failure(_)   => sys.error("something wrong")
      }
  }
}
