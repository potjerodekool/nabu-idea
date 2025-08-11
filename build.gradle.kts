import org.jetbrains.intellij.platform.gradle.IntelliJPlatformType
import org.jetbrains.intellij.platform.gradle.TestFrameworkType
import org.jetbrains.intellij.platform.gradle.models.ProductRelease
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "2.2.0-Beta1"
    id("org.jetbrains.intellij.platform") version "2.5.0"
    //id("antlr")
}

group = "io.github.potjerodekool"
version = "1.0-SNAPSHOT"

// Include the generated files in the source set
sourceSets {
    main {
        java {
            srcDirs(
                "src/main/gen"//,
                //"build/generated-src/antlr/main"
            )
        }
    }
    test {
        java {
            //srcDirs("build/generated-src/antlr/test")
        }
    }
}

repositories {
    mavenLocal()
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin.html
dependencies {
    intellijPlatform {
        create("IC", "2025.1")
        //testFramework(org.jetbrains.intellij.platform.gradle.TestFrameworkType.Platform)
        testFramework(TestFrameworkType.Platform)
        //testFramework(TestFrameworkType.Plugin.Java)

        // Add necessary plugin dependencies for compilation here, example:
        bundledPlugin("com.intellij.java")

        testImplementation("junit:junit:4.13.2")
    }
    /*
    antlr("org.antlr:antlr4:4.13.2") {
        exclude(group = "com.ibm.icu", module = "icu4j")
    }
    */

    implementation("io.github.potjerodekool:nabu-compiler:1.0-SNAPSHOT")

    implementation("org.antlr:antlr4-intellij-adaptor:0.1")

    testImplementation("junit:junit:4.13.2")
    testImplementation(kotlin("test"))
    testImplementation("org.mockito:mockito-core:5.11.0")
}

tasks.named<Test>("test") {
    maxHeapSize = "1G"

    testLogging {
        events("passed")
    }
}

intellijPlatform {
    pluginVerification {
        ides {
            ide(IntelliJPlatformType.IntellijIdeaCommunity, "2025.1.3")
            local(file("C:\\Program Files (x86)\\JetBrains\\IntelliJ IDEA Community Edition 2025.1.3"))
            recommended()
            select {
                types = listOf(IntelliJPlatformType.IntellijIdeaCommunity)
                channels = listOf(ProductRelease.Channel.RELEASE)
                sinceBuild = "251"
                untilBuild = "251.*"
            }
        }
    }

    pluginConfiguration {
        ideaVersion {
            sinceBuild = "242"
        }

        changeNotes = """
      Initial version
    """.trimIndent()
    }
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "21"
        targetCompatibility = "21"
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        compilerOptions.jvmTarget.set(JvmTarget.JVM_21)
    }
}

/*
tasks.generateGrammarSource {
    maxHeapSize = "64m"
    outputDirectory = File("build/generated-src/antlr/main/io/github/potjerodekool/nabu")
}

val generateGrammarSourceTask by tasks.generateGrammarSource
val generateTestGrammarSourceTask by tasks.generateTestGrammarSource
val compileKotlinTask by tasks.compileKotlin
val compileTestKotlinTask by tasks.compileTestKotlin

compileKotlinTask.dependsOn(generateGrammarSourceTask)
compileTestKotlinTask.dependsOn (generateTestGrammarSourceTask)
*/