package cc.trixey.invero.common.util

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive

/**
 * Invero
 * cc.trixey.invero.common.util.String
 *
 * @author Arasple
 * @since 2023/2/25 13:23
 */

/*
Example usage: "a=b;c=d;e=f".parseMappedArguments()
Or JsonObject format
 */
fun String.parseMappedArguments(): Map<String, String> {
    if (startsWith("{")) {
        try {
            standardJson
                .decodeFromString<JsonObject>(this)
                .map { it.key to it.value.jsonPrimitive.content }
                .toMap()
        } catch (e: Exception) {
            return emptyMap()
        }
    }

    val result = mutableMapOf<String, String>()
    var key = ""
    var value = ""
    var isKey = true
    var isEscape = false
    for (char in this) {
        if (isEscape) {
            if (isKey) key += char
            else value += char
            isEscape = false
            continue
        }
        when (char) {
            '=' -> isKey = false
            '\\' -> isEscape = true
            ';' -> {
                result[key] = value
                key = ""
                value = ""
                isKey = true
            }

            else -> {
                if (isKey) key += char
                else value += char
            }
        }
    }
    if (key.isNotEmpty()) result[key] = value
    return result.filterNot { it.key.isBlank() }
}