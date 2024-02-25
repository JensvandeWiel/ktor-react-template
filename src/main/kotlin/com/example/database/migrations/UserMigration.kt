package com.example.database.migrations

import com.example.database.Database
import com.example.database.Migration
import com.example.database.MigrationName
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

@MigrationName("2024_02_24_195301_create_users_table")
class UserMigration : Migration() {
    override fun up(db: Database) {
        transaction(db.db) {
            exec("CREATE TABLE IF NOT EXISTS users (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(50))")
        }
    }

    override fun down(db: Database) {
        transaction(db.db) {
            exec("DROP TABLE users")
        }
    }
}