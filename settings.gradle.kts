rootProject.name = "HiltBinder"

include(":hilt-binder")
include(":hilt-binder-compiler")
include(":sample-deps-javac")
include(":sample-deps-kapt")
include(":sample-deps-ksp")
include(":sample")

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
    }
}