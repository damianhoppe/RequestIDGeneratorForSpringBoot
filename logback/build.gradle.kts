import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.plugin.spring") version("1.9.20")
}

group = "pl.damianhoppe"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("org.springframework:spring-test:6.1.1")
    testImplementation("ch.qos.logback:logback-classic:1.4.7")

    implementation(project(":config"))
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.apache.tomcat.embed:tomcat-embed-core:10.1.16")
    implementation("org.springframework:spring-context:6.1.1")
    implementation("org.slf4j:jul-to-slf4j:2.0.9")
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