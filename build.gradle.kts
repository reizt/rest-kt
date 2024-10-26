import org.gradle.api.tasks.testing.Test
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "2.0.21"
    id("org.springframework.boot") version "3.3.5"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.jlleitschuh.gradle.ktlint") version "12.1.1"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:2.0.21")
    implementation("org.jetbrains.kotlin:kotlin-reflect:2.0.21")
    testImplementation("org.jetbrains.kotlin:kotlin-test:2.0.21")

    testImplementation("org.junit.jupiter:junit-jupiter:5.11.3")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.11.3")

    implementation("org.springframework.boot:spring-boot-starter:3.3.5")
    implementation("org.springframework.boot:spring-boot-starter-web:3.3.5")
    implementation("org.springframework.security:spring-security-web:6.3.4")
    developmentOnly("org.springframework.boot:spring-boot-devtools:3.3.5")
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.3.5")

    implementation("com.google.code.gson:gson:2.11.0")
    implementation("org.postgresql:postgresql:42.7.4")
    implementation("org.ktorm:ktorm-core:4.1.1")
    implementation("com.auth0:java-jwt:4.4.0")
    implementation("com.sendgrid:sendgrid-java:4.10.3")
}

tasks.withType<KotlinCompile> {
    compilerOptions.jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
}

application {
    mainClass.set("com.example.api.RestApiKt")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
