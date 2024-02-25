# Repositories
Repositories ers classes to interact with Models and the Database

## Async Example
```kotlin
class UserRepository(private val database: Database) : UserRepository {
    suspend fun getAllUsers(): List<User> = ...
    suspend fun getUserById(id: Long): User? = ...
    suspend fun createUser(user: User): User = ...
}
```
## Example
```kotlin
class UserRepository(private val database: Database) : UserRepository {
    fun getAllUsers(): List<User> = ...
    fun getUserById(id: Long): User? = ...
    fun createUser(user: User): User = ...
}
```

