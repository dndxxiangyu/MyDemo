package com.seu.test.extension

import org.jetbrains.kotlin.psi.KtDeclaration
import org.jetbrains.kotlin.psi.psiUtil.isPrivate

internal fun KtDeclaration.hasCommentInPrivateMember() = docComment != null && isPrivate()
