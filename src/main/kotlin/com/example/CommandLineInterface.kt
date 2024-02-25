package com.example

import com.example.database.Database
import com.example.database.DatabaseType
import com.example.database.MigrationHandler
import com.example.database.migrations.registeredMigrations
import com.typesafe.config.ConfigFactory
import picocli.CommandLine
import picocli.CommandLine.Command
import picocli.CommandLine.Option
import picocli.CommandLine.ParentCommand

@Command(name = "root", mixinStandardHelpOptions = true, description = ["Run ktor server"], subcommands = [MigrateCommand::class, RollbackCommand::class])
class RootCommand(val args: Array<String>) : Runnable {
        override fun run() {
                io.ktor.server.netty.EngineMain.main(args)
        }
}

class CommonOptions {

        val config = ConfigFactory.load()

        @Option(names = ["-h", "--host"], description = ["Database host"])
        var host: String = config.getString("ktor.db.host")

        @Option(names = ["-p", "--port"], description = ["Database port"])
        var port: Int = config.getInt("ktor.db.port")

        @Option(names = ["-d", "--database"], description = ["Database name"])
        var database: String = config.getString("ktor.db.database")

        @Option(names = ["-u", "--user"], description = ["Database user"])
        var user: String = config.getString("ktor.db.user")

        @Option(names = ["-pw", "--password"], description = ["Database password"])
        var password: String = config.getString("ktor.db.password")

        @Option(names = ["-t", "--type"], description = ["Database type"])
        var type: String = config.getString("ktor.db.type")
}

@Command(name = "rollback", mixinStandardHelpOptions = true, description = ["Rollback database"])
class RollbackCommand : Runnable {

        @CommandLine.Mixin
        var commonOptions: CommonOptions = CommonOptions()

        override fun run() {
                val dbType = when (commonOptions.type) {
                        "MYSQL" -> DatabaseType.MYSQL
                        "POSTGRESQL" -> DatabaseType.POSTGRESQL
                        "SQLITE" -> DatabaseType.SQLITE
                        else -> throw IllegalArgumentException("Unsupported database type")
                }

                val db = Database(dbType, commonOptions.host, commonOptions.port, commonOptions.database, commonOptions.user, commonOptions.password)
                val migrationHandler = MigrationHandler(db, registeredMigrations)
                migrationHandler.rollbackMigrations()
        }
}

@Command(name = "migrate", mixinStandardHelpOptions = true, description = ["Migrate database"])
class MigrateCommand : Runnable {

        @CommandLine.Mixin
        var commonOptions: CommonOptions = CommonOptions()

        override fun run() {
                val dbType = when (commonOptions.type) {
                        "MYSQL" -> DatabaseType.MYSQL
                        "POSTGRESQL" -> DatabaseType.POSTGRESQL
                        "SQLITE" -> DatabaseType.SQLITE
                        else -> throw IllegalArgumentException("Unsupported database type")
                }

                val db = Database(dbType, commonOptions.host, commonOptions.port, commonOptions.database, commonOptions.user, commonOptions.password)
                val migrationHandler = MigrationHandler(db, registeredMigrations)
                migrationHandler.runMigrations()
        }
}