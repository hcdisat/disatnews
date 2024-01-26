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
include(":core:dataaccess:datastore")
include(":core:common")
include(":core:api")
include(":core:presentation")
include(":features:onboarding")
include(":features:onboarding:domain")
include(":core:dataaccess:networking")
include(":core:dataaccess:datasource")
