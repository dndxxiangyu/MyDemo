package com.seu.test.extension.rerports

import io.gitlab.arturbosch.detekt.api.ConsoleReport
import io.gitlab.arturbosch.detekt.api.Detektion

/**
 * @author Artur Bosch
 */
class QualifiedNamesConsoleReport : ConsoleReport() {

    override fun render(detektion: Detektion): String? {
        println("wxy, QualifiedNamesConsoleReport")
        return qualifiedNamesReport(detektion)
    }
}
