plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.20" apply false
    id("org.jetbrains.dokka") version "1.9.10"
    id("maven-publish")
}

allprojects {
    repositories {
        mavenCentral()
    }
}

val documentedSubprojects = arrayOf(
    "common",
    "config",
    "core",
    "logback",
    "openfeign",
    "starter",
)
val subprojectsToPublish = arrayOf(
    "common",
    "config",
    "core",
    "logback",
    "openfeign",
    "starter",
)

tasks.dokkaHtmlMultiModule {
    moduleName.set(project.name)
    includes.from("Module.md")

}

subprojects {
    version = "1.0.0"
    val project = this
    if(name in subprojectsToPublish) {
        apply(plugin = "org.jetbrains.kotlin.jvm")
        apply(plugin = "maven-publish")
        publishing {
            publications {
                register<MavenPublication>(project.name) {
                    this.groupId = project.group.toString()
                    this.artifactId = project.name
                    this.version = project.version.toString()
                    from(project.components.getByName("kotlin"))
                }
            }
        }
    }
    if(this.name in documentedSubprojects) {
        apply(plugin = "org.jetbrains.dokka")

        tasks.withType(org.jetbrains.dokka.gradle.DokkaTaskPartial::class.java) {
            dokkaSourceSets.configureEach {
                if(file("Module.md").exists())
                    includes.from("Module.md")
            }
        }
    }
}

extra["springCloudVersion"] = "2023.0.0"