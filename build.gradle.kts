import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.0"
    id("org.springframework.boot") version "3.0.0"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.jlleitschuh.gradle.ktlint") version "12.1.1"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter:3.3.4")
    implementation("org.springframework.boot:spring-boot-starter-web:3.3.4")
    implementation("org.springframework.security:spring-security-web:6.3.4")
    implementation("org.projectlombok:lombok:1.18.34")
    implementation("org.postgresql:postgresql:42.7.4")
    implementation("org.ktorm:ktorm-core:4.1.1")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:2.0.21")
    implementation("org.jetbrains.kotlin:kotlin-reflect:2.0.21")
    implementation("org.dbunit:dbunit:2.8.0")
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("com.google.code.gson:gson:2.11.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.18.0")
    implementation("com.google.code.gson:gson:2.7")
    implementation("com.auth0:java-jwt:4.4.0")
    implementation("com.sendgrid:sendgrid-java:4.10.3")
    developmentOnly("org.springframework.boot:spring-boot-devtools:3.3.4")
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.3.4")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "17"
    }
}

application {
    mainClass.set("com.example.api.RestApiKt")
}
