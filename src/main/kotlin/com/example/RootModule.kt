package com.example

import com.example.controllers.IndexController
import com.example.database.Database
import com.example.database.DatabaseType
import com.example.repositories.UserRepository
import com.example.routes.mainRoutes
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.resources.*
import io.ktor.server.routing.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger


fun Application.module() {
    val port = environment.config.propertyOrNull("ktor.db.port")?.getString()?.toInt() ?: 3306
    val host = environment.config.propertyOrNull("ktor.db.host")?.getString() ?: "localhost"
    val databaseName = environment.config.propertyOrNull("ktor.db.database")?.getString() ?: ""
    val user = environment.config.propertyOrNull("ktor.db.user")?.getString() ?: "root"
    val password = environment.config.propertyOrNull("ktor.db.password")?.getString() ?: ""
    val type = environment.config.propertyOrNull("ktor.db.type")?.getString()?.let { DatabaseType.valueOf(it) } ?: DatabaseType.MYSQL

    val db = Database(type, host, port, databaseName, user, password)

    install(Koin) {
        slf4jLogger()
        modules(
            module {
                single<Database>(createdAtStart = true) { db }
                single<UserRepository> { UserRepository(get()) }
                single<IndexController> { IndexController(get()) }
            }
        )
    }

    configureSerialization()

    configureRouting()
}

private fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()
    }
}

private fun Application.configureRouting() {
    install(Resources)

    routing {
        mainRoutes()
    }
}
