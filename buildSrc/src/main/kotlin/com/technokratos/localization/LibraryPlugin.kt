package com.technokratos.localization

import com.android.build.gradle.api.AndroidSourceSet
import com.android.build.gradle.api.BaseVariant
import org.gradle.api.DomainObjectSet
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.attributes.Attribute
import java.io.File
import java.util.Locale

open class LibraryExtension {
    var text: String = ""
}

class BuildVariant(
    private val project: Project,
    private val androidVariant: BaseVariant
) {
    val name: String get() = androidVariant.name
    fun getSourceSetNameList(): List<String> = androidVariant.sourceSets.map { it.name }
    fun getSourceDirectories() = getSourceSetNameList().map { project.projectDir.path + File.separator + "src" + File.separator + it}
}

abstract class BasePlugin : Plugin<Project> {

    abstract val Project.buildVariants: DomainObjectSet<BaseVariant>

    abstract val Project.androidSourceSets: NamedDomainObjectContainer<AndroidSourceSet>

    abstract fun checkAndroidPlugin(project: Project)

    final override fun apply(target: Project) {
        checkAndroidPlugin(target)
//        val variants = target.objects.domainObjectSet(BuildVariant::class.java)
//        target.afterEvaluate {
//            variants.addAll(target.buildVariants.map { BuildVariant(target, it) })
//        }
//        variants.all {
//            println("$name: " + getSourceDirectories())
//        }
        target.buildVariants.all {
            val flavorDirName = if (flavorName.isNotEmpty()) { "$flavorName/" } else { "" }
            val outDir = "${target.buildDir}/generated/source/l10n/${flavorDirName}${buildType.name}"
            val resources = target.androidSourceSets.getByName(name).resources
            resources.setSrcDirs(resources.srcDirs + listOf(outDir))

            val runtimeClasspath = "${name}RuntimeClasspath"
            val conf = target.configurations.getByName(runtimeClasspath)

            val runtimeClasspathArtifactFiles = conf.incoming.artifactView {
                attributes {
                    attribute(Attribute.of("artifactType", String::class.java), "android-java-res")
                }
            }.artifacts.artifactFiles

            val variantNameCapitalized = name.capitalize(Locale.US)

            val t = target.tasks.register("generateL10n$variantNameCapitalized") {
                dependsOn(runtimeClasspathArtifactFiles)
                doLast {
                    val genDir = target.file(outDir)
                    genDir.mkdirs()
                    val gen = target.file(File(genDir, "L10n.txt"))
                    if (!gen.exists()) {
                        gen.createNewFile()
                    }
                    gen.printWriter().use { writer ->
                        writer.println("$name l10n task output")
                        writer.println()
                        runtimeClasspathArtifactFiles.reversed().forEach {
                            writer.println("Content of L10n.txt from ${it.path}")
                            target.zipTree(it.path).files.find { it.name == "L10n.txt" }?.also {
                                writer.println("\t${it.path}")
                                it.readLines().forEach {
                                    writer.println("\t$it")
                                }
                                writer.println()
                            }
                        }
                        writer.flush()
                    }
                }
            }

            target.afterEvaluate {
                processJavaResourcesProvider.configure {
                    dependsOn(t.get())
                }
            }
        }
    }

}

class LibraryPlugin : BasePlugin() {

    val Project.android get() = this.extensions.getByType(com.android.build.gradle.LibraryExtension::class.java)

    override val Project.androidSourceSets: NamedDomainObjectContainer<AndroidSourceSet>
        get() = android.sourceSets

    override val Project.buildVariants: DomainObjectSet<BaseVariant>
        get() = android.libraryVariants as DomainObjectSet<BaseVariant>

    override fun checkAndroidPlugin(project: Project) {
        if (!project.pluginManager.hasPlugin("com.android.library")) {
            throw IllegalStateException("Android Library plugin not applied!")
        }
    }
}

class ApplicationPlugin : BasePlugin() {

    val Project.android get() = this.extensions.getByType(com.android.build.gradle.AppExtension::class.java)

    override val Project.androidSourceSets: NamedDomainObjectContainer<AndroidSourceSet>
        get() = android.sourceSets

    override val Project.buildVariants: DomainObjectSet<BaseVariant>
        get() = android.applicationVariants as DomainObjectSet<BaseVariant>

    override fun checkAndroidPlugin(project: Project) {
        if (!project.pluginManager.hasPlugin("com.android.application")) {
            throw IllegalStateException("Android Application plugin not applied!")
        }
    }
}