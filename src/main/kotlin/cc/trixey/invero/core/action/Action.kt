package cc.trixey.invero.core.action

import cc.trixey.invero.core.Context
import cc.trixey.invero.core.serialize.SelectorAction
import kotlinx.serialization.Serializable
import java.util.concurrent.CompletableFuture

/**
 * Invero
 * cc.trixey.invero.core.ActionStructure
 *
 * @author Arasple
 * @since 2023/1/14 12:18
 */
@Serializable(with = SelectorAction::class)
abstract class Action {

    abstract fun run(context: Context): CompletableFuture<Boolean>

}