package cc.trixey.invero.ui.bukkit.api.dsl

import cc.trixey.invero.ui.bukkit.PanelContainer
import cc.trixey.invero.ui.bukkit.panel.*
import cc.trixey.invero.ui.common.Pos
import cc.trixey.invero.ui.common.Scale
import cc.trixey.invero.ui.common.panel.PanelWeight
import cc.trixey.invero.ui.common.scroll.ScrollDirection
import cc.trixey.invero.ui.common.scroll.ScrollTail

/**
 * Invero
 * cc.trixey.invero.ui.bukkit.api.dsl.Panel
 *
 * @author Arasple
 * @since 2022/12/22 20:28
 */
inline fun PanelContainer.standard(
    scale: Pair<Int, Int> = inheritParentScale(),
    locate: Pair<Int, Int> = firstAvailablePositionForPanel(),
    weight: PanelWeight = PanelWeight.NORMAL,
    block: StandardPanel.() -> Unit
) = StandardPanel(this, weight, Scale(scale), Pos(locate)).also {
    block(it)
    this += it
}

inline fun PanelContainer.pagedNetesed(
    scale: Pair<Int, Int> = inheritParentScale(),
    locate: Pair<Int, Int> = firstAvailablePositionForPanel(),
    weight: PanelWeight = PanelWeight.NORMAL,
    defaultPage: Int = 0,
    block: PagedNetesedPanel.() -> Unit
) = PagedNetesedPanel(this, weight, Scale(scale), Pos(locate), defaultPage).also {
    block(it)
    this += it
}

inline fun PanelContainer.freeformPanel(
    scale: Pair<Int, Int> = inheritParentScale(),
    locate: Pair<Int, Int> = firstAvailablePositionForPanel(),
    weight: PanelWeight = PanelWeight.NORMAL,
    block: FreeformStandardPanel.() -> Unit
) = FreeformStandardPanel(this, weight, Scale(scale), Pos(locate)).also {
    block(it)
    this += it
}

inline fun PanelContainer.freeformNetesed(
    scale: Pair<Int, Int> = inheritParentScale(),
    locate: Pair<Int, Int> = firstAvailablePositionForPanel(),
    weight: PanelWeight = PanelWeight.NORMAL,
    block: FreeformNetesedPanel.() -> Unit
) = FreeformNetesedPanel(this, weight, Scale(scale), Pos(locate)).also {
    block(it)
    this += it
}

inline fun PanelContainer.group(
    scale: Pair<Int, Int> = inheritParentScale(),
    locate: Pair<Int, Int> = firstAvailablePositionForPanel(),
    weight: PanelWeight = PanelWeight.NORMAL,
    block: PanelGroup.() -> Unit
) = PanelGroup(this, weight, Scale(scale), Pos(locate)).also {
    block(it)
    this += it
}

inline fun PanelContainer.scroll(
    scale: Pair<Int, Int> = inheritParentScale(),
    locate: Pair<Int, Int> = firstAvailablePositionForPanel(),
    direction: ScrollDirection = ScrollDirection.VERTICAL,
    tail: Int = scale.second,
    weight: PanelWeight = PanelWeight.NORMAL,
    block: ScrollStandardPanel.() -> Unit
) = ScrollStandardPanel(this, weight, Scale(scale), Pos(locate), direction, ScrollTail(tail)).also {
    block(it)
    this += it
}

inline fun <T> PanelContainer.generatorPaged(
    scale: Pair<Int, Int> = inheritParentScale(),
    locate: Pair<Int, Int> = firstAvailablePositionForPanel(),
    weight: PanelWeight = PanelWeight.NORMAL,
    block: PagedGeneratorPanel<T>.() -> Unit
) = PagedGeneratorPanel<T>(this, weight, Scale(scale), Pos(locate)).also {
    block(it)
    this += it
}

inline fun <T> PanelContainer.generatorScroll(
    scale: Pair<Int, Int> = inheritParentScale(),
    locate: Pair<Int, Int> = firstAvailablePositionForPanel(),
    weight: PanelWeight = PanelWeight.NORMAL,
    block: ScrollGeneratorPanel<T>.() -> Unit
) = ScrollGeneratorPanel<T>(this, weight, Scale(scale), Pos(locate)).also {
    block(it)
    this += it
}

inline fun PanelContainer.craftingIOPanel(
    scale: Pair<Int, Int> = inheritParentScale(),
    locate: Pair<Int, Int> = firstAvailablePositionForPanel(),
    weight: PanelWeight = PanelWeight.NORMAL,
    block: CraftingPanel.() -> Unit
) = CraftingPanel(this, weight, Scale(scale), Pos(locate)).also {
    block(it)
    this += it
}