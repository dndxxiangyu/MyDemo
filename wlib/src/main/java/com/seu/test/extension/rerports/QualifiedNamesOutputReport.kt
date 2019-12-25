package com.seu.test.extension.rerports

import io.gitlab.arturbosch.detekt.api.Detektion
import io.gitlab.arturbosch.detekt.api.OutputReport

/**
 * @author Artur Bosch
 * @author Marvin Ramin
 */
class QualifiedNamesOutputReport : OutputReport() {

//    var fileName: String = "fqNames"
    override val ending: String = "txt"

    override fun render(detektion: Detektion): String? {
        println("wxy, QualifiedNamesOutputReport")
        return qualifiedNamesReport(detektion)
    }
}
