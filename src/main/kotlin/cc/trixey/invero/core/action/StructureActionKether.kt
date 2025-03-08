package cc.trixey.invero.core.action

import cc.trixey.invero.core.Context
import cc.trixey.invero.core.serialize.ListStringSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.decodeFromJsonElement

/**
 * Invero
 * cc.trixey.invero.core.action.StructureActionKether
 *
 * @author Arasple
 * @since 2023/1/15 22:42
 */
@Serializable
class StructureActionKether(
    @Serializable(with = ListStringSerializer::class)
    val kether: List<String>
) : Action() {

    @Transient
    private val action: ActionKether =
        kether.singleOrNull()?.let {
            Json.decodeFromJsonElement(JsonPrimitive(it))
        } ?: Json.decodeFromJsonElement(JsonArray(kether.map { JsonPrimitive(it) }))

    override fun run(context: Context) = action.run(context)

}