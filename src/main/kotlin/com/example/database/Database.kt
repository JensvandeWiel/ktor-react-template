package com.example.database

import org.jetbrains.exposed.sql.Database


enum class DatabaseType {
    MYSQL,
    POSTGRESQL,
    SQLITE,
}


class Database(val type: DatabaseType, val host: String, val port: Int, val database: String, val username: String, val password: String) {
    private val driver = when (type) {
        DatabaseType.MYSQL -> "com.mysql.cj.jdbc.Driver"
        DatabaseType.POSTGRESQL -> "org.postgresql.Driver"
        DatabaseType.SQLITE -> "org.sqlite.JDBC"
        else -> throw IllegalArgumentException("Unsupported database type")
    }

    private val url = when (type) {
        DatabaseType.MYSQL -> "jdbc:mysql://$host:$port/$database"
        DatabaseType.POSTGRESQL -> "jdbc:postgresql://$host:$port/$database"
        DatabaseType.SQLITE -> "jdbc:sqlite:$database"
        else -> throw IllegalArgumentException("Unsupported database type")
    }



    val db = Database.connect(url, driver, username, password)
}
