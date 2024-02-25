# Models
Models are data classes for the defined entities in the database. They are used to represent the data in the database and are used to interact with the database.
## Example
```kotlin
data class User(val id: Long, val name: String, val email: String)
```