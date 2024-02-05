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
include(":core:presentation")
include(":features:onboarding")
include(":features:onboarding:data")
include(":features:onboarding:domain")
include(":core:dataaccess:networking")
include(":features:news")
include(":features:news:domain")
include(":features:news:data")
