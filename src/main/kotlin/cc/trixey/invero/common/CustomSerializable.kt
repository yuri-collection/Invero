package cc.trixey.invero.common

import kotlinx.serialization.json.JsonElement

/**
 * Invero
 * cc.trixey.invero.common.CustomSerializable
 *
 * @author Arasple
 * @since 2023/2/25 13:56
 */
interface CustomSerializable<T> {

    fun deserialize(element: JsonElement): T

    fun serialize(activator: T): JsonElement

}