@file:OptIn(ExperimentalSerializationApi::class)

package cc.trixey.invero.core.action

import cc.trixey.invero.core.Context
import cc.trixey.invero.core.serialize.ListScriptKetherSerializer
import cc.trixey.invero.ui.bukkit.util.proceed
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames
import java.util.concurrent.CompletableFuture

/**
 * Invero
 * cc.trixey.invero.core.action.ConditionAny
 *
 * @author Arasple
 * @since 2023/1/26 22:42
 */
@Serializable
class ConditionAny(
    @SerialName("any")
    @Serializable(with = ListScriptKetherSerializer::class)
    override val conditions: List<ScriptKether>,
    @JsonNames("amount", "at_least")
    private val atLeast: Int = 1,
    override val then: Action?,
    override val `else`: Action?
) : ActionTernary, Action() {

    init {
        require(then != null || `else` != null) { "At least one type of response is required for IF structure" }
    }

    override fun run(context: Context): CompletableFuture<Boolean> {
        var amount = 0
        return if (conditions.any { it.evalInstant(context, false).proceed { amount++ } || amount >= atLeast }) {
            then?.run(context)
        } else {
            `else`?.run(context)
        } ?: CompletableFuture.completedFuture(false)
    }

}