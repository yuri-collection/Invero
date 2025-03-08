package cc.trixey.invero.core

import cc.trixey.invero.core.serialize.LayoutSerializer
import cc.trixey.invero.core.util.SPECIAL_GROUP
import cc.trixey.invero.ui.common.Pos
import cc.trixey.invero.ui.common.Positions
import cc.trixey.invero.ui.common.Scale
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * Invero
 * cc.trixey.invero.core.Layout
 *
 * @author Arasple
 * @since 2023/1/15 22:35
 */
@Serializable(with = LayoutSerializer::class)
class Layout(val raw: List<String>) {

    @Transient
    private val scale = raw.let { list ->
        list.maxBy { it.length }.length to list.size
    }

    @Transient
    internal val mapped = mutableMapOf<String, Positions>().apply {
        raw.forEachIndexed { rows, rawLine ->
            val line = rawLine.ifBlank { buildString { repeat(scale.first) { append(' ') } } }
            split(line).forEachIndexed { index, key ->
                computeIfAbsent(key) { Positions() } += Pos(index to rows)
            }
        }
    }

    @Transient
    private val coerecedScale = mapped.values.flatMap { it.values }.let { posList ->
        posList.maxBy { it.x }.x + 1 to posList.maxBy { it.y }.y + 1
    }

    fun getScale() = Scale(scale)

    fun findRectangle(search: String = " "): Pair<Pos, Scale>? {
        mapped.forEach { (key, value) ->
            if (key == search) {
                val positions = value.values

                val start = positions.min()
                val width = positions.maxBy { it.x }.x - positions.minBy { it.x }.x + 1
                val height = positions.max().y - positions.min().y + 1

                return start to Scale(width to height)
            }
        }
        return null
    }


    fun getCoerecedScale(): Pair<Int, Int> {
        mapped.maxBy { it.value.values.maxBy { it.y } }
        return scale
    }

    fun search(key: String?): Set<Pos> {
        return mapped[key]?.values ?: setOf()
    }

    companion object {

        private fun split(input: String): List<String> {
            val matcher = SPECIAL_GROUP.matcher(input)
            if (!matcher.find()) return input.map { it.toString() }

            val result = mutableListOf<String>()
            var lastStart = 0

            do {
                result += input.substring(lastStart, matcher.start()).map { it.toString() }
                result += matcher.group(1)
                lastStart = matcher.end()
            } while (matcher.find())

            result += input.substring(lastStart, input.length).map { it.toString() }

            return result
        }

    }

}