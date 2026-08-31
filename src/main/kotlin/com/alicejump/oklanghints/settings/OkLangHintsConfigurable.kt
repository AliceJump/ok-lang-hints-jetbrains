package com.alicejump.oklanghints.settings

import com.alicejump.oklanghints.OkLangHintsBundle
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.project.Project
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.components.JBTextField
import com.intellij.ui.dsl.builder.AlignX
import com.intellij.ui.dsl.builder.panel
import javax.swing.JComponent

class OkLangHintsConfigurable(private val project: Project) : Configurable {
    private val langDirectory = JBTextField()
    private val poDirectory = JBTextField()
    private val poDomains = JBTextField()
    private val displayLocale = JBTextField()
    private val featureAliases = JBTextField()
    private val effectsFile = JBTextField()
    private val enablePoData = JBCheckBox(OkLangHintsBundle.message("settings.enablePoData"))
    private val enableInlayHints = JBCheckBox(OkLangHintsBundle.message("settings.enableInlayHints"))
    private val enableTemplateGallery = JBCheckBox(OkLangHintsBundle.message("settings.templateGallery"))

    override fun getDisplayName(): String = OkLangHintsBundle.message("settings.displayName")

    override fun createComponent(): JComponent = panel {
        group(OkLangHintsBundle.message("settings.paths")) {
            row(OkLangHintsBundle.message("settings.langDirectory")) {
                cell(langDirectory).align(AlignX.FILL)
            }
            row(OkLangHintsBundle.message("settings.poDirectory")) {
                cell(poDirectory).align(AlignX.FILL)
            }
            row(OkLangHintsBundle.message("settings.poDomains")) {
                cell(poDomains).align(AlignX.FILL)
            }
            row(OkLangHintsBundle.message("settings.effectsFile")) {
                cell(effectsFile).align(AlignX.FILL)
            }
        }
        group(OkLangHintsBundle.message("settings.editor")) {
            row(OkLangHintsBundle.message("settings.displayLocale")) {
                cell(displayLocale).align(AlignX.FILL)
            }
            row(OkLangHintsBundle.message("settings.featureAliases")) {
                cell(featureAliases).align(AlignX.FILL)
            }
            row { cell(enablePoData) }
            row { cell(enableInlayHints) }
            row { cell(enableTemplateGallery) }
        }
    }.also { reset() }

    override fun isModified(): Boolean {
        val state = OkLangHintsSettings.getInstance(project).state
        return langDirectory.text != state.langDirectory.orEmpty() ||
            poDirectory.text != state.poDirectory.orEmpty() ||
            splitList(poDomains.text) != state.poDomains ||
            displayLocale.text != state.displayLocale.orEmpty() ||
            splitList(featureAliases.text) != state.featureAliases ||
            effectsFile.text != state.effectsFile.orEmpty() ||
            enablePoData.isSelected != state.enablePoData ||
            enableInlayHints.isSelected != state.enableInlayHints ||
            enableTemplateGallery.isSelected != state.enableTemplateGallery
    }

    override fun apply() {
        val settings = OkLangHintsSettings.getInstance(project)
        settings.state.langDirectory = langDirectory.text.trim()
        settings.state.poDirectory = poDirectory.text.trim()
        settings.state.poDomains = splitList(poDomains.text).toMutableList()
        settings.state.displayLocale = displayLocale.text.trim()
        settings.state.featureAliases = splitList(featureAliases.text).toMutableList()
        settings.state.effectsFile = effectsFile.text.trim()
        settings.state.enablePoData = enablePoData.isSelected
        settings.state.enableInlayHints = enableInlayHints.isSelected
        settings.state.enableTemplateGallery = enableTemplateGallery.isSelected
    }

    override fun reset() {
        val state = OkLangHintsSettings.getInstance(project).state
        langDirectory.text = state.langDirectory.orEmpty()
        poDirectory.text = state.poDirectory.orEmpty()
        poDomains.text = state.poDomains.joinToString(", ")
        displayLocale.text = state.displayLocale.orEmpty()
        featureAliases.text = state.featureAliases.joinToString(", ")
        effectsFile.text = state.effectsFile.orEmpty()
        enablePoData.isSelected = state.enablePoData
        enableInlayHints.isSelected = state.enableInlayHints
        enableTemplateGallery.isSelected = state.enableTemplateGallery
    }

    private fun splitList(value: String): List<String> = value
        .split(',', ';', '\n')
        .map(String::trim)
        .filter(String::isNotEmpty)
}
