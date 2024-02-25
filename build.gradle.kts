import com.github.gradle.node.npm.task.NpmTask


val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val koin_version: String by project

val exposed_version: String by project
val h2_version: String by project
plugins {
    kotlin("jvm") version "1.9.22"
    id("io.ktor.plugin") version "2.3.8"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.22"
    id("com.github.node-gradle.node") version "7.0.2"
}

group = "com.example"
version = "0.0.1"

application {
    mainClass.set("${group}.MainKt")
    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=true")

}

getTasksByName("run", true).forEach {
    it.dependsOn("buildFrontend")
}

getTasksByName("buildFatJar", true).forEach {
    it.dependsOn("buildFrontend")
}

sourceSets {
    main {
        resources.exclude("frontend/node_modules")
    }
}



repositories {
    mavenCentral()
}

node {
    version = "20.11.0"
    workDir = file("src/main/resources/frontend")
}

//create a task to run nrm run build in frontend dir in resources
task<NpmTask>("buildFrontend") {
    dependsOn("npmInstall")
    args.set(arrayListOf("run", "build"))
    workingDir = file("src/main/resources/frontend")
}

//create a task to run nrm run build in frontend dir in resources
task<NpmTask>("devFrontend") {
    dependsOn("npmInstall")
    args.set(arrayListOf("run", "dev"))
    workingDir = file("src/main/resources/frontend")
}



dependencies {
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-resources")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposed_version")
    implementation("io.github.classgraph:classgraph:4.8.165")
    implementation("org.postgresql:postgresql:42.7.2")
    implementation("mysql:mysql-connector-java:8.0.33")
    implementation("info.picocli:picocli:4.7.5")
    implementation("org.xerial:sqlite-jdbc:3.44.1.0")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.insert-koin:koin-core:$koin_version")
    implementation("io.insert-koin:koin-ktor:$koin_version")
    implementation("io.insert-koin:koin-logger-slf4j:$koin_version")
    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}


