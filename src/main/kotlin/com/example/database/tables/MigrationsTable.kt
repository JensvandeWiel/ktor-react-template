package com.example.database.tables

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime.now

object MigrationsTable : Table() {
    val id = integer("id").autoIncrement()
    val migration = varchar("migration", 255).uniqueIndex()
    val appliedAt = datetime("applied_at").defaultExpression(CurrentDateTime)
    val batch = integer("batch").default(1)
    override val primaryKey = PrimaryKey(id, name = "PK_Migrations_ID")
}