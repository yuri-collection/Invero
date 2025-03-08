package cc.trixey.invero.core.util

import org.bukkit.entity.Player
import taboolib.common.platform.function.adaptPlayer
import taboolib.module.chat.HexColor
import taboolib.module.chat.component
import taboolib.module.nms.MinecraftVersion
import taboolib.platform.compat.replacePlaceholder

/**
 * Invero
 * cc.trixey.invero.common.util.TextDecoration
 *
 * @author Arasple
 * @since 2023/2/22 13:06
 */

/**
 * 常规文本格式替换
 *
 * 文本源中的 § 保留为[[COLOR_CHAR]]
 * 替换 kether inline 变量
 * 替换 placeholderAPI 变量
 * 翻译 miniMessage 格式
 * 翻译通用颜色格式
 * 还原 [[COLOR_CHAR]] 为 §
 */
fun String.translateFormattedMessage(
    player: Player,
    variables: Map<String, Any?> = emptyMap(),
    skipComp: Boolean = false
) =
    KetherHandler
        .parseInline(replace("§", "[[COLOR_CHAR]]"), player, variables)
        .replacePlaceholder(player)
        .replace("§", "[[COLOR_CHAR]]")
//        .parseMiniMessage()
        .colored()
        .replace("[[COLOR_CHAR]]", "§")
        .replaceBitmapBlank()
        .let {
            if (skipComp) it else it.component().build().toLegacyText()
        }

/**
 * （默认）发送格式化消息
 * 依次翻译
 * - Kether Inline
 * - Placeholder API
 * - TabooLib Colored
 * - MiniMessage Component Send (if supported) (else send noraml message)
 */
fun String.sendFormattedMiniMessageComponent(player: Player, variables: Map<String, Any> = emptyMap()) =
    KetherHandler
        .parseInline(this, player, variables)
        .replacePlaceholder(player)
        .colored()
//        .parseMiniMessageAndSend(player)

/**
 * 发送 TabooLib ComponentText
 * 依次翻译
 * - Kether Inline
 * - Placeholder API
 * - TabooLib Component (with colored)
 */
fun String.sendFormattedTabooComponent(player: Player, variables: Map<String, Any> = emptyMap()) =
    KetherHandler
        .parseInline(this, player, variables)
        .replacePlaceholder(player)
        .colored()
        .component()
        .build()
        .sendTo(adaptPlayer(player))

fun String.colored() =
    HexColor
        .translate(this)
        .let {
            if (patchDragonCore) {
                it.replace("&#", "§#")
            } else {
                it
            }
        }

private val patchDragonCore: Boolean = MinecraftVersion.majorLegacy in 11903 until 11300