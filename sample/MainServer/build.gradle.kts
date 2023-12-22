import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.springframework.boot") version("3.2.0")
    id("io.spring.dependency-management") version("1.1.4")
    id("org.jetbrains.kotlin.plugin.spring") version("1.9.20")
}

group = "pl.damianhoppe"
version = "1.0.0"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation(project(":starter"))
    implementation(project(":openfeign"))
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

tasks.withType(KotlinCompile::class.java) {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.test {
    useJUnitPlatform()
}
