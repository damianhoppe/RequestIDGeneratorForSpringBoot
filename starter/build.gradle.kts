import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.kotlin.jvm")
}

group = "pl.damianhoppe"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

dependencies {
    api(project(":common"))
    api(project(":config"))
    api(project(":core"))
    api(project(":logback"))

    implementation("org.jetbrains.kotlin:kotlin-reflect")
}

tasks.withType(KotlinCompile::class.java) {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}