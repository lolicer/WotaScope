import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
}

kotlin {
    jvm("desktop")
    
    sourceSets {
        val desktopMain by getting
        
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtimeCompose)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
            implementation("androidx.datastore:datastore-preferences-core:1.1.7")

            implementation("uk.co.caprica:vlcj:4.7.0")
            implementation("org.jetbrains.skija:skija-windows:0.93.6")

            // 2025.7.1 约束布局尚未应用在跨平台项目中，故使用此依赖。链接如下：
            // https://github.com/Lavmee/constraintlayout-compose-multiplatform?tab=readme-ov-file
            implementation("tech.annexflow.compose:constraintlayout-compose-multiplatform:0.6.0")
        }
    }
}


compose.desktop {
    application {
        mainClass = "pers.lolicer.wotascope.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Msi)
            packageName = "pers.lolicer.wotascope"
            packageVersion = "1.0.0"

            appResourcesRootDir.set(project.layout.projectDirectory.dir("resources"))
            appResourcesRootDir.set(project.layout.projectDirectory.dir("temp_videos"))
        }
    }
}

// compose.resources {
//     publicResClass = false
//     packageOfResClass = "pers.lolicer."
//     generateResClass = auto
// }
