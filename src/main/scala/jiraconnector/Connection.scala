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

  def returnProjectsList(targetServer: String, user: String, pass: String) :String = {
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
      
      val morefinalresult = for {
        result  <- responseFuture
        fresult <- Unmarshal(result.entity).to[String]
      } yield (fresult)

      return Await.result(morefinalresult, Duration.Inf)
  }
}
