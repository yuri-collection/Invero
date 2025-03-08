package cc.trixey.invero.core.script

import cc.trixey.invero.core.script.loader.InveroKetherParser
import taboolib.common5.cdouble
import taboolib.module.kether.combinationParser
import taboolib.platform.util.getMetaFirstOrNull
import taboolib.platform.util.removeMeta
import taboolib.platform.util.setMeta

/**
 * Invero
 * cc.trixey.invero.core.script.kether.ActionContext
 *
 * @author Arasple
 * @since 2023/1/23 11:02
 */
object ActionContext {

    @InveroKetherParser(["context", "ctx"])
    fun parser() = combinationParser {
        it.group(
            // get, has, set, del, inc, dec
            symbol(),
            // key
            text().option(),
            // value
            command("to", "by", then = action()).option().defaultsTo(null)
        ).apply(it) { action, key, mod ->
            now {
                val value = if (mod != null) newFrame(mod).run<Any>().getNow(null) else null
                if (key == null || action == "update") {
                    return@now session()?.updateVariables()
                }

                when (action) {
                    "get" -> session()?.getVariable(key)
                    "has" -> session()?.hasValidVariable(key) == true
                    "no", "without" -> session()?.hasValidVariable(key) == false
                    "rem", "del", "delete" -> {
                        variables().remove(key)
                        session()?.removeVariable(key)
                    }

                    "set" -> {
                        (value ?: error("No valid value")).let {
                            variables().set(key, it)
                            session()?.setVariable(key, it)
                        }
                    }

                    "inc", "increase", "+=" -> {
                        (value ?: error("No valid value")).let {
                            session()?.apply {
                                val result = (getVariable(key).cdouble + it.cdouble).round()
                                variables().set(key, result)
                                setVariable(key, result)
                            }
                        }
                    }

                    "dec", "decrease", "-=" -> {
                        (value ?: error("No valid value")).let {
                            session()?.apply {
                                val result = (getVariable(key).cdouble - it.cdouble).round()
                                variables().set(key, result)
                                setVariable(key, result)
                            }
                        }
                    }

                    else -> error("Unknown action $action")
                }
            }
        }
    }

    @InveroKetherParser(["meta"])
    fun parserMeta() = combinationParser {
        it.group(
            symbol(),
            text().option(),
            command("to", "by", then = action()).option().defaultsTo(null)
        ).apply(it) { action, key, mod ->
            now {
                val value = if (mod != null) newFrame(mod).run<Any>().getNow(null) else null
                val player = player()
                if (key == null || action == "update") {
                    return@now session()?.updateVariables()
                }

                when (action) {
                    "get" -> {
                        player.getMetaFirstOrNull(key)?.value()
                    }

                    "has", "no", "without" -> {
                        player.getMetaFirstOrNull(key) != null
                    }

                    "rem", "del", "delete" -> {
                        player.removeMeta(key)
                    }

                    "set" -> {
                        if (value != null) {
                            player.setMeta(key, value)
                        } else {
                            "No valid value"
                        }
                    }

                    "inc", "increase", "+=" -> {
                        val result = (player.getMetaFirstOrNull(key)?.value()?.cdouble ?: 0.0) + value.cdouble
                        player.setMeta(key, result)
                    }

                    "dec", "decrease", "-=" -> {
                        val result = (player.getMetaFirstOrNull(key)?.value()?.cdouble ?: 0.0) - value.cdouble
                        player.setMeta(key, result)
                    }

                    else -> {
                        "Unknown action $action"
                    }
                }
            }
        }
    }

    private fun Double.round(): Any {
        return if (this - toInt() > 0) this else toInt()
    }

}