# Database
The base directory contains a class to handle connecting to the database. The subdirectory `tables` contains all the table definitions, these tables should be created using the migrations and not using exposed.

# Migrations
Migrations are located in the `migrations` directory. You must register all your migrations in `RegisterMigrations` in the `migrations` directory. The migrations are run in the order of the name given by the annotation `@MigrationName(<name>)`.

### Creating a migration example:
You should follow the name format `YYYY_MM_DD_HHMMSS_[action]_[table_name]`, but you can use anything you like as long as they are in alphabetical order.
You get the freedom to migrate the database in any way you like using the db object provided in the `up` and `down` methods.
```kotlin
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
```