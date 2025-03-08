package cc.trixey.invero.ui.bukkit.api.dsl

import cc.trixey.invero.ui.common.Pos

/**
 * Invero
 * cc.trixey.invero.ui.bukkit.api.dsl.Positioning
 *
 * @author Arasple
 * @since 2023/1/10 22:08
 */
fun at(x: Int = 0, y: Int = 0) = x to y

fun pos(x: Int = 0, y: Int = 0) = Pos(at(x, y))