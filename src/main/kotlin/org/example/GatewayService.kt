package org.example

import spark.Spark.*

class GatewayService {
    companion object {
        private val allowedHeaders = listOf("Content-Type", "Authorization", "X-Guest-UUID", "X-Alias-UUID,Accept")

        @JvmStatic
        fun main(args: Array<String>) {
            port(5000)
            defineRoutes()
        }

        private fun defineRoutes() {
            initExceptionHandler { e ->
                System.exit(100)
            }

            get("/hello") { _, _ -> "Hello, World" }

            val graphQLHandler = GraphQLHandler()

            post("/graphqlBatch") { request, response ->
                try {
                    graphQLHandler.handleBatch(request, response)
                } catch (e: java.lang.Exception) {
                    response.status(500)
                    response.body("Server Error")
                }
            }

            post("/graphqlAsync") { request, response ->
                try {
                    graphQLHandler.handleAsync(request, response)
                } catch (e: java.lang.Exception) {
                    response.status(500)
                    response.body("Server Error")
                }
            }

            options("*") { _, _ -> "ok" }

            before("*") { request, response ->
                val requestOrigin = request.headers("Origin")
                response.header("Access-Control-Allow-Origin", requestOrigin)
                response.header("Access-Control-Allow-Methods", "GET,POST,OPTIONS")
                response.header("Access-Control-Allow-Headers", allowedHeaders.joinToString(","))
            }
        }
    }
}
