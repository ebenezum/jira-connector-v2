package jiraconnector

import com.typesafe.config.ConfigFactory


object Main {
  def main(args: Array[String]) {
    val host = ConfigFactory.load().getString("jira.PRP_SERVER_URL")
    val user = ConfigFactory.load().getString("jira.JIRA_USER")
    val pass = ConfigFactory.load().getString("jira.JIRA_API_TOKEN")
    val efekt = jiraconnector.Connection.newConnection(host, user, pass)
    println(efekt)
  }
}
