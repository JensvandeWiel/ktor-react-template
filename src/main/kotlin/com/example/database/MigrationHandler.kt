package com.example.database

import com.example.database.tables.MigrationsTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction




class MigrationHandler(private val dbHandler: Database, private var migrations: List<Migration>) {
    init {
        // sort the migrations by name
        migrations = migrations.sortedBy { it.getMigrationName() }


        transaction(dbHandler.db) {
            SchemaUtils.create(MigrationsTable)
        }
    }

    @Suppress("unused")
    fun runMigrations() {
        transaction(dbHandler.db) {
            val lastBatch = MigrationsTable.slice(MigrationsTable.batch).selectAll()
                .maxOfOrNull { it[MigrationsTable.batch] } ?: 0

            val currentBatch = lastBatch + 1


            val appliedMigrations = MigrationsTable.selectAll().map { it[MigrationsTable.migration] }
            migrations.forEach { migration ->
                val migrationName = migration.getMigrationName()
                if (migrationName !in appliedMigrations) {
                    migration.up(dbHandler)
                    MigrationsTable.insert {
                        it[MigrationsTable.migration] = migrationName
                        it[batch] = currentBatch
                    }
                }
            }
        }
    }

    @Suppress("unused")
    fun rollbackMigrations() {
        transaction(dbHandler.db) {
            val lastBatch = MigrationsTable.slice(MigrationsTable.batch).selectAll()
                .maxOfOrNull { it[MigrationsTable.batch] } ?: 0

            val migrationsToRollback = MigrationsTable.select { MigrationsTable.batch eq lastBatch }
                .orderBy(MigrationsTable.id to SortOrder.DESC)
                .map { it[MigrationsTable.migration] }

            migrationsToRollback.forEach { migrationName ->
                val migration = migrations.find { it.getMigrationName() == migrationName }
                migration?.down(dbHandler)
                MigrationsTable.deleteWhere { MigrationsTable.migration eq migrationName }
            }
        }
    }
}