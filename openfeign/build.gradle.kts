import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.plugin.spring") version("1.9.20")
    id("io.spring.dependency-management") version("1.1.4")
}

group = "pl.damianhoppe"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("io.github.openfeign:feign-mock:13.1")

    implementation(project(":config"))
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.apache.tomcat.embed:tomcat-embed-core:10.1.16")
    implementation("org.springframework:spring-beans:6.1.1")
    implementation("org.springframework:spring-context:6.1.1")
    implementation("org.springframework:spring-web:6.1.1")
    implementation("org.springframework.security:spring-security-web:6.2.0")
    implementation("org.springframework.boot:spring-boot:3.2.0")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
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