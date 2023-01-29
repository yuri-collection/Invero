package cc.trixey.invero.core.serialize

import cc.trixey.invero.core.AgentPanel
import cc.trixey.invero.core.action.ScriptKether
import cc.trixey.invero.core.icon.Icon
import cc.trixey.invero.core.icon.Slot
import cc.trixey.invero.core.menu.CommandArgument
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonTransformingSerializer
import kotlinx.serialization.json.jsonArray

/**
 * Invero
 * cc.trixey.invero.serialize.TolerantListScoping
 *
 * @author Arasple
 * @since 2023/1/18 10:56
 */
internal object ListScriptKetherSerializer :
    JsonTransformingSerializer<List<ScriptKether>>(ListSerializer(ScriptKether.serializer())) {
    override fun transformSerialize(element: JsonElement) = element.tolerantListSerialize()
    override fun transformDeserialize(element: JsonElement) = element.tolerantListDeserialize()
}

internal object ListStringSerializer :
    JsonTransformingSerializer<List<String>>(listStringSerializer) {
    override fun transformSerialize(element: JsonElement) = element.tolerantListSerialize()
    override fun transformDeserialize(element: JsonElement) = element.tolerantListDeserialize()
}

internal object ListIconSerializer :
    JsonTransformingSerializer<List<Icon>>(ListSerializer(IconSerializer)) {
    override fun transformSerialize(element: JsonElement) = element.tolerantListSerialize()
    override fun transformDeserialize(element: JsonElement) = element.tolerantListDeserialize()
}

internal object ListCommandArgumentSerializer :
    JsonTransformingSerializer<List<CommandArgument>>(ListSerializer(CommandArgumentSerailizer)) {
    override fun transformSerialize(element: JsonElement) = element.tolerantListSerialize()
    override fun transformDeserialize(element: JsonElement) = element.tolerantListDeserialize()
}

internal object ListSlotSerializer :
    JsonTransformingSerializer<List<Slot>>(ListSerializer(Slot.serializer())) {
    override fun transformSerialize(element: JsonElement) = element.tolerantListSerialize()
    override fun transformDeserialize(element: JsonElement) = element.tolerantListDeserialize()
}

internal object ListAgentPanelSerializer :
    JsonTransformingSerializer<List<AgentPanel>>(ListSerializer(AgentPanel.serializer())) {
    override fun transformSerialize(element: JsonElement) = element.tolerantListSerialize()
    override fun transformDeserialize(element: JsonElement) = element.tolerantListDeserialize()
}

internal fun JsonElement.tolerantListSerialize() = jsonArray.singleOrNull() ?: this

internal fun JsonElement.tolerantListDeserialize() = if (this !is JsonArray) JsonArray(listOf(this)) else this
