import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.triggers.vcs

version = "2025.07"

project {
    description = "Random spawn and wild teleportation plugin"

    buildType(Build)
}

object Build : BuildType({
    name = "Build"

    artifactRules = "target/*.jar => ."

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {
        maven {
            name = "Maven Clean Package"
            goals = "clean package"
            runnerArgs = "-DskipTests"
            jdkHome = "%env.JDK_17_0%"
        }
    }

    triggers {
        vcs {
            branchFilter = ""
        }
    }
})
