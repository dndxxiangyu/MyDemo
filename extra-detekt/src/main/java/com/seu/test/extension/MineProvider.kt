package com.seu.test.extension

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.RuleSet
import io.gitlab.arturbosch.detekt.api.RuleSetProvider

/**
 * @author Artur Bosch
 */
class MineProvider : RuleSetProvider {

    override val ruleSetId: String = "customer"

    override fun instance(config: Config): RuleSet {
        return RuleSet(
            ruleSetId, listOf(
                com.seu.test.extension.rules.TooManyFunctionsMe(),
                com.seu.test.extension.rules.CommentOverPrivateFunction(config)
            )
        )
    }
}
