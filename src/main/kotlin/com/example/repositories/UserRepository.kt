package com.example.repositories


import com.example.database.Database
import com.example.database.tables.Users
import com.example.models.User
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class UserRepository(private val database: Database) {
    fun getAllUsers(): List<User> {
        return transaction(database.db) {
            Users.selectAll().map { User(it[Users.id], it[Users.name]) }
        }
    }

    fun createUser(name: String): User {
        return transaction(database.db) {
            val id = Users.insert {
                it[Users.name] = name
            } get Users.id
            User(id, name)
        }
    }
}