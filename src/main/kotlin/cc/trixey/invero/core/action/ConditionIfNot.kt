package cc.trixey.invero.core.action

import cc.trixey.invero.core.Context
import cc.trixey.invero.core.serialize.ListScriptKetherSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.concurrent.CompletableFuture

/**
 * Invero
 * cc.trixey.invero.core.action.ConditionIfNot
 *
 * @author Arasple
 * @since 2023/1/24 21:07
 */
@Serializable
class ConditionIfNot(
    @SerialName("if_not")
    @Serializable(with = ListScriptKetherSerializer::class)
    override val conditions: List<ScriptKether>,
    override val then: Action?,
    override val `else`: Action?
) : ActionTernary, Action() {

    init {
        require(then != null || `else` != null) { "At least one type of response is required for IF structure" }
    }

    override fun run(context: Context): CompletableFuture<Boolean> {
        return conditions.first().eval(context).thenCompose {
            (if (!it) then?.run(context)
            else `else`?.run(context)) ?: CompletableFuture.completedFuture(true)
        }
    }

}