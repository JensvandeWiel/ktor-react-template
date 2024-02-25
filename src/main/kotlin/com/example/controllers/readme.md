# Controllers
Controllers are used for any business logic and to handle the requests from the client. They are used to interact with the database and to perform any business logic.
## Example
```kotlin
class ExampleController {
    suspend fun index(call: ApplicationCall) {
        call.respondText("Hello, world!")
    }
}
```