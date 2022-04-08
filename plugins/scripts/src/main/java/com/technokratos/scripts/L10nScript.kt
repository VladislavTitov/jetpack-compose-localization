package com.technokratos.scripts

import kotlin.script.experimental.annotations.KotlinScript
import com.technokratos.localization.core.localizationMap
import kotlin.script.experimental.api.EvaluationResult
import kotlin.script.experimental.api.ResultWithDiagnostics
import kotlin.script.experimental.api.ScriptCompilationConfiguration
import kotlin.script.experimental.api.ScriptEvaluationConfiguration
import kotlin.script.experimental.api.implicitReceivers
import kotlin.script.experimental.host.toScriptSource
import kotlin.script.experimental.jvm.dependenciesFromCurrentContext
import kotlin.script.experimental.jvm.jvm
import kotlin.script.experimental.jvmhost.BasicJvmScriptingHost

@KotlinScript(
    fileExtension = "l10n.kts",
    compilationConfiguration = L10nScriptCompilationConfiguration::class,
    evaluationConfiguration = L10nScriptEvaluationConfiguration::class
)
abstract class L10nScript(val args: Array<String>)

object L10nScriptCompilationConfiguration: ScriptCompilationConfiguration({
    implicitReceivers(LocalizationBuilder::class)
    jvm {
        dependenciesFromCurrentContext(wholeClasspath = true)
    }
})

object L10nScriptEvaluationConfiguration: ScriptEvaluationConfiguration({
    implicitReceivers(LocalizationBuilder)
})

fun eval(): ResultWithDiagnostics<EvaluationResult> {
    val script = """
        import java.util.Locale
        
        val RUSSIAN = Locale("ru")
        val TATAR = Locale("tt")

        val supportedLocalesNow = registerLocales(RUSSIAN, TATAR)

        Translatable(
            "localesHeader",
            "Locales",
            hashMapOf(
                RUSSIAN to "Локали",
                TATAR to "Локальләштерүләр"
            )
        )
    """.trimIndent()
    return BasicJvmScriptingHost().eval(script.toScriptSource(), L10nScriptCompilationConfiguration, L10nScriptEvaluationConfiguration)
}

fun main() {
    val res = eval()
    println("localization map: $localizationMap")
    println(res)
}