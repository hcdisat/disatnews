pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "DisatNews"
include(":app")
include(":core:presentation")
include(":features:onboarding")
include(":core:dataaccess:datastore")
include(":core:common")
include(":app:domain")
include(":core:api")
