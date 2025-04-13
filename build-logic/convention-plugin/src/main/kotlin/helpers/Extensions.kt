package helpers

import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.the
import org.jetbrains.compose.ComposeExtension
import org.jetbrains.compose.resources.ResourcesExtension

internal val Project.libs get() = the<LibrariesForLibs>()

internal fun ComposeExtension.resources(action: ResourcesExtension.() -> Unit) {
    extensions.configure(action)
}

internal fun String.transformPathToComponentFormat() = this
    .replace("-", "_")
    .substringBefore("component")
    .split(":")
    .joinToString(".").removePrefix(".")