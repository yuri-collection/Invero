package cc.trixey.invero.core.command

import cc.trixey.invero.common.Invero
import cc.trixey.invero.common.Menu
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.CommandContext
import taboolib.common.platform.command.component.CommandComponent
import taboolib.common.platform.command.component.CommandComponentDynamic
import taboolib.common.platform.command.component.CommandComponentLiteral
import taboolib.platform.util.asLangText

val CommandContext<*>.menu: Menu?
    get() = Invero.API.getMenuManager().getMenu(this["menu"], true)

val CommandContext<*>.player: Player?
    get() = Bukkit.getPlayerExact(this["player"])

fun CommandComponentDynamic.suggestMenuIds(uncheck: Boolean = false) {
    suggestion<CommandSender>(uncheck = uncheck) { _, _ -> Invero.API.getMenuManager().getMenus().map { it.id!! } }
}

/**
 * modified from
 *
 * https://github.com/TabooLib/taboolib/blob/master/expansion/expansion-command-helper
 */
internal fun CommandComponent.createHelper() = execute<ProxyCommandSender> { sender, context, _ ->
    val command = context.command
    val builder = StringBuilder("§7Usage: §f/${command.name}")
    var newline = false

    fun print(
        compound: CommandComponent,
        index: Int,
        size: Int,
        offset: Int = 8,
        level: Int = 0,
        end: Boolean = false,
        optional: Boolean = false
    ) {
        var option = optional
        var comment = 0
        when (compound) {
            is CommandComponentLiteral -> {
                if (size == 1) {
                    builder.append(" ").append("§7${compound.aliases[0]}")
                } else {
                    newline = true
                    builder.appendLine()
                    builder.append(space(offset))
                    if (level > 1) builder.append(if (end) " " else "§8│")
                    builder.append(space(level))
                    if (index + 1 < size) builder.append("§8├── ")
                    else builder.append("§8└── ")
                    builder.append("§7${compound.aliases[0]}")
                }
                option = false
                comment = compound.aliases[0].length
            }

            is CommandComponentDynamic -> {
                val value = if (compound.comment.startsWith("@")) {
                    sender.cast<CommandSender>().asLangText(compound.comment.substring(1))
                } else {
                    compound.comment
                }
                comment = if (compound.optional || option) {
                    option = true
                    builder.append(" ").append("§f[<$value>]")
                    compound.comment.length + 4
                } else {
                    builder.append(" ").append("§f<$value>")
                    compound.comment.length + 2
                }
            }
        }
        if (level > 0) comment += 1
        compound.children.forEachIndexed { i, children ->
            // 因 literal 产生新的行
            if (newline) {
                print(children, i, compound.children.size, offset, level + comment, end, option)
            } else {
                val length = if (offset == 8) command.name.length + 1 else comment + 1
                print(children, i, compound.children.size, offset + length, level, end, option)
            }
        }
    }

    val size = context.commandCompound.children.size
    context.commandCompound.children.forEachIndexed { index, children ->
        print(children, index, size, end = index + 1 == size)
    }
    sender.sendMessage(builder.toString())
}

private fun space(space: Int) = (1..space).joinToString("") { " " }
