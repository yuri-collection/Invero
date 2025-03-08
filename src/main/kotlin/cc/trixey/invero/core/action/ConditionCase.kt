package cc.trixey.invero.core.action

import cc.trixey.invero.core.Context
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames
import java.util.concurrent.CompletableFuture

/**
 * Invero
 * cc.trixey.invero.core.action.ConditionCase
 *
 * @author Arasple
 * @since 2023/1/15 22:42
 */
@OptIn(ExperimentalSerializationApi::class)
@Serializable
class ConditionCase(
    val case: ScriptKether,
    @SerialName("when")
    val response: Map<Comparator, Action>,
    @JsonNames("else")
    val default: Action?
) : Action() {

    override fun run(context: Context): CompletableFuture<Boolean> = case.invoke(context).thenCompose { switchBy ->
        val case = switchBy.toString()

        response.forEach {
            if (it.key.compare(case)) {
                return@thenCompose it.value.run(context)
            }
        }
        default?.let { return@thenCompose it.run(context) }

        return@thenCompose CompletableFuture.completedFuture(true)
    }

}