package com.alicejump.okscripttoolkit.editor

import com.intellij.lang.documentation.AbstractDocumentationProvider
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile

class OkDocumentationProvider : AbstractDocumentationProvider() {
    override fun getCustomDocumentationElement(
        editor: Editor,
        file: PsiFile,
        contextElement: PsiElement?,
        targetOffset: Int,
    ): PsiElement? {
        return if (OkEditorSupport.referenceAt(editor.document, targetOffset, file.project) != null) {
            file.findElementAt(targetOffset.coerceAtMost(file.textLength - 1))
        } else {
            null
        }
    }

    override fun generateDoc(element: PsiElement, originalElement: PsiElement?): String? {
        val source = originalElement ?: element
        val file = source.containingFile ?: return null
        val reference = OkEditorSupport.referenceAt(file.viewProvider.document, source.textOffset, file.project) ?: return null
        return OkEditorSupport.documentation(reference, file.project)
    }

    override fun generateHoverDoc(element: PsiElement, originalElement: PsiElement?): String? =
        generateDoc(element, originalElement)
}
