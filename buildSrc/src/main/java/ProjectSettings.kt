object ProjectSettings {
    private const val mainNamespace = "com.hcdisat"
    private const val namespace = "$mainNamespace.disatnews"
    const val compileSdk = 34
    const val applicationId = namespace
    const val minSdk = 24
    const val targetSdk = 34
    const val versionCode = 1
    const val versionName = "1.0"

    object Versions {
        const val kotlinCompiler = "1.5.8"
        const val jvmTarget = "17"
    }

    fun namespace(moduleName: String) = "$mainNamespace.$moduleName"

    enum class Modules(val path: String) {
        AppDomain(":app:domain"),

        CoreDataAccessDataStore(":core:dataaccess:datastore"),
        CoreCommon(":core:common"),
        CorePresentation(":core:presentation"),
        CoreApi(":core:api"),

        FeaturesOnboarding(":features:onboarding"),
    }
}