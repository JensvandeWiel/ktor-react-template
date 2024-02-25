package com.example.database

@Target(AnnotationTarget.CLASS)
annotation class MigrationName(val name: String)

abstract class Migration {
    abstract fun up(db : Database)
    abstract fun down(db : Database)
}

fun Migration.getMigrationName() : String {
    return this::class.annotations.find { it is MigrationName }?.let { it as MigrationName }?.name ?: throw IllegalArgumentException("MigrationName annotation not found on class ${this::class.simpleName}")
}