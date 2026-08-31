package com.alicejump.oklanghints.editor

import com.alicejump.oklanghints.core.OkProjectDataService
import com.intellij.codeInsight.completion.CompletionContributor
import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.completion.CompletionType
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.components.service

class OkPythonCompletionContributor : CompletionContributor() {
    override fun fillCompletionVariants(parameters: CompletionParameters, result: CompletionResultSet) {
        if (parameters.completionType != CompletionType.BASIC) return
        val context = OkEditorSupport.completionContext(
            parameters.editor.document,
            parameters.offset,
            parameters.position.project,
        ) ?: return
        val data = parameters.position.project.service<OkProjectDataService>()
        val replacement = if (context.prefix.isEmpty()) result else result.withPrefixMatcher(context.prefix)

        when (context.kind) {
            CompletionKind.LANG_MODULE -> data.modules().forEach { module ->
                replacement.addElement(
                    LookupElementBuilder.create(module)
                        .withTypeText("${data.keys(module).size} keys", true),
                )
            }
            CompletionKind.LANG_KEY -> data.keys(context.module ?: return).forEach { key ->
                val entry = data.langEntry(context.module, key)
                val node = entry?.let(data::pick)
                replacement.addElement(
                    LookupElementBuilder.create(key)
                        .withTailText(node?.let { "  ${if (it.type == "pattern") "~${it.value}~" else "「${it.value}」"}" }, true),
                )
            }
            CompletionKind.FEATURE -> data.features().forEach { feature ->
                replacement.addElement(
                    LookupElementBuilder.create(feature.name)
                        .withTypeText("${feature.width}×${feature.height}", true),
                )
            }
            CompletionKind.EFFECT -> data.effectIds().forEach { id ->
                val effect = data.effect(id)
                replacement.addElement(
                    LookupElementBuilder.create(id)
                        .withTypeText(effect?.category, true)
                        .withTailText(effect?.description?.let { "  $it" }, true),
                )
            }
            CompletionKind.OCR -> data.poKeys("ocr").forEach { key ->
                val node = data.poEntry("ocr", key)?.let(data::pick)
                replacement.addElement(
                    LookupElementBuilder.create(key).withTailText(node?.value?.let { "  → $it" }, true),
                )
            }
        }
        if (context.prefix.isNotEmpty()) replacement.restartCompletionOnAnyPrefixChange()
    }
}

class OkJsonCompletionContributor : CompletionContributor() {
    override fun fillCompletionVariants(parameters: CompletionParameters, result: CompletionResultSet) {
        if (parameters.completionType != CompletionType.BASIC) return
        val context = OkEditorSupport.completionContext(
            parameters.editor.document,
            parameters.offset,
            parameters.position.project,
        ) ?: return
        if (context.kind != CompletionKind.EFFECT) return
        val data = parameters.position.project.service<OkProjectDataService>()
        val replacement = if (context.prefix.isEmpty()) result else result.withPrefixMatcher(context.prefix)
        data.effectIds().forEach { id ->
            val effect = data.effect(id)
            replacement.addElement(
                LookupElementBuilder.create(id)
                    .withTypeText(effect?.category, true)
                    .withTailText(effect?.description?.let { "  $it" }, true),
            )
        }
    }
}
