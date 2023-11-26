pluginManagement {
    repositories {
        maven {
            url = uri("https://nexus.partdp.ir/repository/part-android/")
            artifactUrls("https://nexus.partdp.ir/repository/part-android/")
            credentials {
                username = "android-user"
                password = "EL2BB+*wkXEaydY=/2>2Kx-tV4CV-%"
            }
        }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven {
            url = uri("https://nexus.partdp.ir/repository/part-android/")
            artifactUrls("https://nexus.partdp.ir/repository/part-android/")
            credentials {
                username = "android-user"
                password = "EL2BB+*wkXEaydY=/2>2Kx-tV4CV-%"
            }
        }
    }
}

rootProject.name = "CoroutineFlowAdvance"
include(":app")
 