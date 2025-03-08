package cc.trixey.invero.ui.common

/**
 * Invero
 * cc.trixey.invero.ui.common.Positions
 *
 * @author Arasple
 * @since 2023/1/4 22:54
 */
@JvmInline
value class Positions(val values: MutableSet<Pos> = mutableSetOf()) {

    constructor(values: Collection<Pos>) : this(values.toMutableSet())

    fun add(pos: Pos) {
        values += pos
    }

    operator fun plusAssign(pos: Pos) {
        values += pos
    }

    operator fun contains(pos: Pos): Boolean {
        return pos in values
    }

    operator fun minus(positions: Positions?): Set<Pos> {
        return if (positions == null) values
        else values - positions.values
    }

}