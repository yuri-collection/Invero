package cc.trixey.invero.core.script

import cc.trixey.invero.core.script.loader.InveroKetherParser
import cc.trixey.invero.ui.common.panel.FreeformPanel
import cc.trixey.invero.ui.common.panel.ScrollPanel
import taboolib.module.kether.ParserHolder
import taboolib.module.kether.combinationParser

/**
 * Invero
 * cc.trixey.invero.core.script.kether.ActionScroll
 *
 * @author Arasple
 * @since 2023/2/10 14:06
 */
object ActionScroll {

    @InveroKetherParser(["shift"])
    fun shift() = combinationParser {
        it.group(
            command("by", "with", then = int().and(int())).option().defaultsTo(0 to 0)
        ).apply(it) { shiftValue ->
            val (x, y) = shiftValue

            now {
                val freeform = findNearstPanelRecursively<FreeformPanel>() ?: return@now "<PANEL NOT FOUND>"
                return@now freeform.shift(x, y)
            }
        }
    }

    /*
    scroll index | get
    scroll next | right | down
    scroll previous | left | up
    scroll reset
     */
    @InveroKetherParser(["scroll"])
    fun parserScroll() = parserScroll(null)

    fun parserScroll(ref: ScrollPanel?) = combinationParser {
        it.group(symbol()).apply(it) { operator ->
            runScroll(ref, operator)
        }
    }

    private fun ParserHolder.runScroll(panel: ScrollPanel?, operator: String) =
        now {
            val scroll = panel ?: findNearstPanelRecursively() ?: return@now "<PANEL NOT FOUND>"

            when (operator.lowercase()) {
                "index", "get" -> scroll.viewport.let { if (it.x > 0) it.x else it.y }
                "next", "right", "down" -> scroll.scroll(1)
                "previous", "left", "up" -> scroll.scroll(-1)
                "reset" -> scroll.resetViewport()
                else -> "<ERROR OPERATOR: $operator>"
            }
        }

}