import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
}

kotlin {
    jvm()
    
    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)

            implementation("uk.co.caprica:vlcj:4.7.0")
            implementation("org.jetbrains.skija:skija-windows:0.93.6")

            // 2025.7.1 约束布局尚未应用在跨平台项目中，故使用此依赖。链接如下：
            // https://github.com/Lavmee/constraintlayout-compose-multiplatform?tab=readme-ov-file
            implementation("tech.annexflow.compose:constraintlayout-compose-multiplatform:0.6.0")

            // Kotlin Multiplatform没有DataStore，故使用此依赖作为设置。链接如下：
            // https://github.com/russhwolf/multiplatform-settings
            implementation("com.russhwolf:multiplatform-settings:1.3.0")
        }
    }
}


compose.desktop {
    application {
        jvmArgs += listOf(
            // 必须添加的模块开放参数
            "--add-opens=java.base/java.nio=ALL-UNNAMED",
            "--add-opens=java.base/sun.nio.ch=ALL-UNNAMED",

            // 针对Java 16+的额外参数
            "--add-exports=java.base/sun.nio.ch=ALL-UNNAMED",
            "--add-exports=java.base/sun.misc=ALL-UNNAMED",

            // 禁用JNA的系统库缓存
            "-Djna.nosys=false"
        )
        mainClass = "pers.lolicer.wotascope.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Msi)
            packageName = "WotaScope"
            packageVersion = "1.0.17"

            modules("java.instrument", "java.prefs", "jdk.unsupported")
            jvmArgs += mutableListOf(
                "--add-exports", "jdk.unsupported/sun.misc=ALL-UNNAMED"
            )

            appResourcesRootDir.set(project.layout.projectDirectory.dir("resources"))

            windows{
                shortcut = true
                dirChooser = true
                iconFile.set(project.file("resources\\windows\\compose-multiplatform.ico"))
            }
        }
    }
}
