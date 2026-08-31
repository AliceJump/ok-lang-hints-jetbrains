package com.alicejump.oklanghints.core

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class OkProjectDataServiceTest {
    @Test
    fun `parse PO entries and multiline values`() {
        val entries = OkProjectDataService.parsePo(
            """
            msgid "体力.*"
            msgstr "体力[0-9]+"

            msgid "借 款 "
            "金 额"
            msgstr "Loan "
            "Amount"
            """.trimIndent(),
        ).toMap()

        assertEquals("体力[0-9]+", entries["体力.*"])
        assertEquals("Loan Amount", entries["借 款 金 额"])
    }

    @Test
    fun `parse effects categories and descriptions`() {
        val effects = OkProjectDataService.parseEffects(
            """
            class EffectType(Enum):
                # 元素附着
                ATTACH_COLD = "ATTACH_COLD"

            # 效果描述映射
            EFFECT_DESCRIPTIONS = {
                EffectType.ATTACH_COLD: "敌人被施加寒冷元素",
            }
            """.trimIndent(),
        )

        val cold = effects.getValue("ATTACH_COLD")
        assertEquals("元素附着", cold.category)
        assertEquals("敌人被施加寒冷元素", cold.description)
        assertTrue(effects.size == 1)
    }
}
