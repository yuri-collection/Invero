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

/**
 * 获取当前字符串中目标字符的所有索引并执行相应的操作
 */
@JvmOverloads
fun String.allIndexOf(target: String, startIndex: Int = 0, ignoreCase: Boolean = false, action: (Int) -> Unit) {
    var index = indexOf(target, startIndex, ignoreCase)
    while (index > -1) {
        action(index); index = indexOf(target, index + 1, ignoreCase)
    }
}

/**
 * 替换非转义字符
 * @see String.replace
 */
@JvmOverloads
fun String.replaceNonEscaped(
    oldValue: String,
    newValue: String,
    ignoreCase: Boolean = false,
    startIndex: Int = 0,
    escapeChar: String = "\\",
): String = buildString {
    // 索引记录
    var lastIndex = 0
    // 匹配字符串
    allIndexOf(oldValue, startIndex, ignoreCase) { index ->
        // 从上次找到的位置到当前找到的位置之前的字符串
        val segment = this@replaceNonEscaped.substring(lastIndex, index)
        // 检查转义字符串
        if (this@replaceNonEscaped.startsWith(escapeChar, index - escapeChar.length))
            append(segment.dropLast(escapeChar.length)).append(oldValue)
        else append(segment).append(newValue)
        // 更新索引
        lastIndex = index + oldValue.length
    }
    append(this@replaceNonEscaped.substring(lastIndex)) // 尾处理
}