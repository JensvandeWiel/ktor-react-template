
# Ktor React Template
This is a react template for ktor projects which is "batteries included" the biggest things it has is an orm nice package layout and migrations without flyway

For more info you can check all the markdown files in the project

# How to run
Use gradle tasks `run` or `buildFatJar` these have building the frontend. You can also create or change it to your own liking.

## What's not yet included
- [ ] Tests
- [ ] Dockerfile
- [ ] Automatic migration file creation
- [ ] Hot reload

# How to rename (In IntelliJ IDEA)
1. Rename the root folder to your project name
2. File -> Project Structure -> Project -> Name -> Change the name to your project name
3. settings.gradle.kts -> Change the rootProject.name to your project name
4. build.gradle.kts -> group to your project group
5. rename the package directories to your project group
6. Edit application.conf module to your project group
7. (optional) Find and replace all instances of `com.example` with your project group


## Contributing
If you want to contribute to this project you can do so by creating a pull request or an issue.
I would love to see this project grow and become a great starting point for ktor projects, maybe even it's project generator.