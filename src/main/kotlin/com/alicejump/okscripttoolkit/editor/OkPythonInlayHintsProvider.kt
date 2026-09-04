package com.alicejump.okscripttoolkit.editor

import com.alicejump.okscripttoolkit.settings.OkScriptToolkitSettings
import com.intellij.codeInsight.hints.declarative.InlayHintsCollector
import com.intellij.codeInsight.hints.declarative.InlayHintsProvider
import com.intellij.codeInsight.hints.declarative.InlayTreeSink
import com.intellij.codeInsight.hints.declarative.InlineInlayPosition
import com.intellij.codeInsight.hints.declarative.SharedBypassCollector
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile

class OkInlayHintsProvider : InlayHintsProvider {
    override fun createCollector(file: PsiFile, editor: Editor): InlayHintsCollector? {
        if (!OkScriptToolkitSettings.getInstance(file.project).state.enableInlayHints) return null
        return Collector(file, editor)
    }

    private class Collector(
        private val file: PsiFile,
        private val editor: Editor,
    ) : SharedBypassCollector {
        private val seen = mutableSetOf<Pair<Int, String>>()

        override fun collectFromElement(element: PsiElement, sink: InlayTreeSink) {
            if (element !== file) return
            val document = editor.document
            val references = OkEditorSupport.references(document, 0, document.textLength, file.project)
            for (reference in references) {
                val hint = OkEditorSupport.hint(reference, file.project) ?: continue
                val key = reference.hintOffset to hint
                if (!seen.add(key)) continue
                sink.addPresentation(
                    InlineInlayPosition(reference.hintOffset, true),
                    hasBackground = false,
                ) {
                    text(hint)
                }
            }
        }
    }
}
