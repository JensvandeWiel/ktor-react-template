package com.example.routes

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.apiRoutes() {
    route("/api") {
        get("/hello") {
            call.respondText("Hello, World!")
        }
    }
}