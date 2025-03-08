package cc.trixey.invero.core.script

import cc.trixey.invero.core.script.loader.InveroKetherParser
import taboolib.module.kether.actionNow
import taboolib.module.kether.expects
import taboolib.module.kether.scriptParser

/**
 * Invero
 * cc.trixey.invero.core.script.ActionType
 *
 * @author Arasple
 * @since 2023/6/11 10:20
 */
object ActionType {


    private val operators = mutableMapOf<String, (Any?) -> Boolean>().apply {
        put("INT") { it.toString().toIntOrNull() != null }
        put("DOUBLE") { it.toString().toDoubleOrNull() != null }
        put("BOOLEAN") { it.toString().toBoolean() }
        put("STRING") { it is String }
        put("LIST") { it is List<*> }
        put("MAP") { it is Map<*, *> }
        put("OBJECT") { it != null }
    }

    // typecheck <type> <value>
    @InveroKetherParser(["typecheck"])
    fun shift() = scriptParser {
        val operator = operators[it.expects(*operators.keys.toTypedArray())]
        val value = it.nextParsedAction()

        actionNow {
            val insert = newFrame(value).run<Any?>().getNow(null) ?: return@actionNow false
            operator?.invoke(insert) ?: false
        }
    }

}