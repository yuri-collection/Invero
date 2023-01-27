package cc.trixey.invero.script.kether

import cc.trixey.invero.common.Panel
import taboolib.common5.cint
import taboolib.module.kether.*

/**
 * Invero
 * cc.trixey.invero.script.kether.ActionPanel
 *
 * @author Arasple
 * @since 2023/1/19 21:20
 */
object ActionPanel {

    /*
    panel {at 0 at 1 at 2} <handler>

    handlers:
    - page
    - scroll
    - shift
    - filter
    - icon
     */
    @KetherParser(["panel"], namespace = "invero", shared = true)
    fun parser() = scriptParser {
        val indexs = mutableListOf<Int>()

        actionNow {
            while (it.hasNext()) {
                when (it.expects("at", "page", "icon")) {
                    "at" -> indexs += newFrame(it.nextParsedAction()).run<Any>().getNow(null).cint
                    "page" -> {
                        ActionPage.parser(locatePanel(indexs)).reader.invoke(it)
                        break
                    }

                    "icon" -> {
                        ActionIcon.parser(locatePanel(indexs)).reader.invoke(it)
                        break
                    }
                }
            }
        }
    }

    private fun <T : Panel> ScriptFrame.locatePanel(indexs: List<Int>): T? {
        return if (indexs.isEmpty()) null else findPanelAt(indexs)
    }

}