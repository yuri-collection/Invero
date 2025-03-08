package cc.trixey.invero.common

/**
 * Invero
 * cc.trixey.invero.common.ObjectElement
 *
 * @author Arasple
 * @since 2023/2/1 16:46
 */
class Object(val content: Map<String, Any>) {

    operator fun get(key: String): Any? {
        return content[key]
    }

    val variables: Map<String, Any> by lazy {
        content
            .map { "element_${it.key}" to it.value }
            .toMap() + mapOf("@element" to this)
    }

}

inline fun sourceObject(block: MutableMap<String, Any?>.() -> Unit): Object = buildMap {
    mutableMapOf<String, Any?>().also(block).forEach { (key, value) ->
        if (value != null) put(key, value)
    }
}.let { Object(it) }