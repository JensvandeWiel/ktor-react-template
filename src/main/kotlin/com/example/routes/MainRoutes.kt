package com.example.routes

import com.example.controllers.IndexController
import com.example.database.Database
import com.example.models.User
import com.example.repositories.UserRepository
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import jdk.jshell.spi.ExecutionControl.UserException
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.java.KoinJavaComponent.inject
import org.koin.ktor.ext.inject

fun Route.mainRoutes() {
    apiRoutes()

    val indexController: IndexController by inject()

    get("get") {
        indexController.index(call)
    }

    post("post") {
        indexController.post(call)
    }


    singlePageApplication {
        useResources = true
        filesPath = "frontend/dist"
        defaultPage = "index.html"
    }
}