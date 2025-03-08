package cc.trixey.invero.core.util

import taboolib.common.LifeCycle
import taboolib.common.platform.Awake

/**
 * @author 寒雨
 * @since 2022/2/24 23:12
 **/
object BitBlank {

    internal val cache = mutableMapOf<Int, String>()

    private val specifiedNum = listOf(1, 2, 3, 4, 5, 6, 7, 8, 16, 32, 64, 128)

    private val registry = hashMapOf(
        "-1" to '\uf801',
        "-2" to '\uf802',
        "-3" to '\uf803',
        "-4" to '\uf804',
        "-5" to '\uf805',
        "-6" to '\uf806',
        "-7" to '\uf807',
        "-8" to '\uf808',
        "-16" to '\uf809',
        "-32" to '\uf80a',
        "-64" to '\uf80b',
        "-128" to '\uf80c',
        "+1" to '\uf821',
        "+2" to '\uf822',
        "+3" to '\uf823',
        "+4" to '\uf824',
        "+5" to '\uf825',
        "+6" to '\uf826',
        "+7" to '\uf827',
        "+8" to '\uf828',
        "+16" to '\uf829',
        "+32" to '\uf82a',
        "+64" to '\uf82b',
        "+128" to '\uf82c',
    )

    @Awake(LifeCycle.LOAD)
    fun cache() {
        (-256..256).forEach { spaceBy(it) }
    }

    fun replace(value: String): String {
        if (value.isEmpty()) {
            return value
        }
        val chars = value.toCharArray()
        val builder = StringBuilder(value.length)
        var i = 0
        while (i < chars.size) {
            val mark = i
            if (chars[i] == '{') {
                val alias = StringBuilder()
                while (i + 1 < chars.size && chars[i + 1] != '}') {
                    i++
                    alias.append(chars[i])
                }
                if (i != mark && i + 1 < chars.size && chars[i + 1] == '}') {
                    i++
                    if (alias.isNotEmpty()) {
                        val key = alias.toString()
                        if (registry.containsKey(key)) {
                            builder.append(registry[key])
                        } else {
                            // 如果是不存在的数字，将会拆解成指定的数字相加
                            val intOrNull = key.toIntOrNull()
                            if (intOrNull != null) {
                                // 负数
                                if (key.startsWith('-')) {
                                    splitNum(-intOrNull).map { registry["-$it"] ?: "/-$it/" }
                                        .forEach { builder.append(it) }
                                } else {
                                    splitNum(intOrNull).map { registry["+$it"] ?: "/+$it/" }
                                        .forEach { builder.append(it) }
                                }
                            } else {
                                // 无效的别名
                                builder.append('{').append(alias).append('}')
                            }
                        }
                    }
                } else {
                    i = mark
                }
            }
            if (mark == i) {
                builder.append(chars[i])
            }
            i++
        }
        return builder.toString()
    }

    /**
     * 将数字拆解成指定的数字相加
     */
    private fun splitNum(num: Int): List<Int> {
        val list = mutableListOf<Int>()
        var temp = num
        while (temp > 0) {
            val max = specifiedNum.filter { it <= temp }.maxOrNull() ?: 1
            list.add(max)
            temp -= max
        }
        return list
    }

}

fun spaceBy(value: Int): String {
    return BitBlank.cache.computeIfAbsent(value) {
        "{$value}".replaceBitmapBlank()
    }
}

fun String.fixBy(space: Int): String {
    return this + spaceBy(space)
}

fun String.replaceBitmapBlank(): String = BitBlank.replace(this)