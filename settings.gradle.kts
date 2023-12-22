pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "RequestIDGeneratorForSpringBoot"
include("common")
include("config")
include("core")
include("openfeign")
include("logback")
include("sample:EurekaServer")
findProject(":sample:EurekaServer")?.name = "EurekaServer"
include("sample:MainServer")
findProject(":sample:MainServer")?.name = "MainServer"
include("sample:SecondServer")
findProject(":sample:SecondServer")?.name = "SecondServer"
include("starter")
