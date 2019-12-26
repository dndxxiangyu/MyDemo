package com.seu.test.extension.rerports

import io.gitlab.arturbosch.detekt.api.Detektion
import org.jetbrains.kotlin.com.intellij.openapi.util.Key

@Suppress("UNCHECKED_CAST", "DEPRECATION")
fun qualifiedNamesReport(detektion: Detektion): String? {
    // referencing the original key 'fqNamesKey' does not retrieve the stored values
    // using the deprecated method seems to work for unknown reasons
    val key = Key.findKeyByName("FQNames") as Key<Set<String>>
    var fqNames = detektion.getData(key)
    println("fqNames: " + fqNames)
    if (fqNames == null || fqNames.isEmpty()) {
        fqNames = HashSet<String>().apply {
            add("wuxiangyu, test")
        }
    }
    return with(StringBuilder()) {
        fqNames.forEach {
            append(it + "\n")
        }
        toString()
    }
}
