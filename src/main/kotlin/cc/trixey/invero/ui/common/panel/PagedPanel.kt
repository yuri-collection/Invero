package cc.trixey.invero.ui.common.panel

import cc.trixey.invero.ui.common.Panel

/**
 * Invero
 * cc.trixey.invero.ui.common.panel.PagedPanel
 *
 * @author Arasple
 * @since 2023/1/5 23:11
 */
interface PagedPanel : Panel {

    var pageChangeCallback: PagedPanel.(fromPage: Int, toPage: Int) -> Unit

    val maxPageIndex: Int

    var pageIndex: Int

    fun onPageChanging(block: PagedPanel.(fromPage: Int, toPage: Int) -> Unit) {
        pageChangeCallback = block
    }

    fun nextPage(value: Int = 1) {
        (pageIndex + value).assertSwitch()
    }

    fun previousPage(value: Int = 1) {
        (pageIndex - value).assertSwitch()
    }

    fun switchPage(index: Int) {
        pageIndex = index
    }

    fun Int.assertSwitch() {
        if (this in 0..maxPageIndex) {
            switchPage(this)
        }
    }

}