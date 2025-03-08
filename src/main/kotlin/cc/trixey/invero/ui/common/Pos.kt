package cc.trixey.invero.ui.common

/**
 * Invero
 * cc.trixey.invero.ui.common.Pos
 *
 * @author Arasple
 * @since 2023/1/4 22:41
 *
 * 槽位的准确坐标
 * 采用 (x,y) 计法
 *
 * 相对任意 Scale 运算可得到一个 Slot 值
 * 真实的 Slot 值只有最高层（Window）需要利用
 */
@JvmInline
value class Pos(val value: Pair<Int, Int>) : Comparable<Pos> {

    val x: Int
        get() = value.first

    val y: Int
        get() = value.second

    constructor(x: Int, y: Int) : this(x to y)

    constructor(slot: Int, scale: Scale) : this(scale.convertToPosition(slot).value)

    fun convertToSlot(scale: Scale, index: Pos = Pos(0, 0)): Int {
        return scale.convertToSlot(x, y, index)
    }

    fun convertToParent(currentLocate: Pos = NIL): Pos {
        return currentLocate + this
    }

    operator fun minus(pos: Pos): Pos {
        return Pos(x - pos.x, y - pos.y)
    }

    operator fun plus(pos: Pos): Pos {
        return Pos(x + pos.x, y + pos.y)
    }

    operator fun plus(pos: Pair<Int, Int>): Pos {
        return Pos(x + pos.first, y + pos.second)
    }

    operator fun unaryMinus(): Pos {
        return Pos(-x, -y)
    }

    override fun compareTo(other: Pos): Int {
        return (x + y * 9) - (other.x + other.y * 9)
    }

    override fun toString(): String {
        return "($x,$y)"
    }

    companion object {

        val NIL = Pos(0, 0)

    }

}