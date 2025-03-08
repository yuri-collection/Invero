package cc.trixey.invero.core.icon

import cc.trixey.invero.core.Layout
import cc.trixey.invero.ui.common.Pos
import cc.trixey.invero.ui.common.Scale
import kotlinx.serialization.*
import kotlinx.serialization.encoding.*
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonPrimitive
import taboolib.common5.cint

/**
 * Invero
 * cc.trixey.invero.core.icon.Slot
 *
 * @author Arasple
 * @since 2023/1/16 10:49
 *

 */
@Serializable
data class Slot(private val raw: String) {

    @Transient
    private val uncalculatedSlots = mutableSetOf<Int>()

    @Transient
    private val locations = run {

        /*
        Layout in SLOT
         */
        if ("\n" in raw) {
            return@run Layout(raw.split("\n"))
                .mapped
                .filter { it.key.isNotBlank() }
                .minBy { it.value.values.size }
                .value
                .values.toList()
        }

        /*
        RANGE: 1~10   2..15
        SLOT: 1 ; 2 ; 3 ; 4
        POS: 0x2 ; 3,4
         */
        raw.split(";").mapNotNull { str ->
            val exp = str.replace(" ", "")
            if ('~' in exp || '-' in exp || ".." in exp) {
                val (from, to) = exp.split("~", "-", "..")
                uncalculatedSlots += (from.toInt()..to.toInt()).toList().filter { it >= 0 }
            } else if ('x' in exp || ',' in exp) {
                val (x, y) = exp.split('x', ',')
                return@mapNotNull Pos(x.toInt() to y.toInt())
            } else {
                (exp.toIntOrNull() ?: -1).let { slot -> if (slot >= 0) uncalculatedSlots += slot }
            }
            return@mapNotNull null
        }

    }

    fun release(scale: Scale): List<Pos> {
        return uncalculatedSlots.map { scale.convertToPosition(it) } + locations
    }

    override fun toString(): String {
        return raw
    }

    @OptIn(ExperimentalSerializationApi::class)
    @Serializer(forClass = Slot::class)
    companion object : KSerializer<Slot> {

        override fun serialize(encoder: Encoder, value: Slot) {
            if (value.raw.toIntOrNull() != null) {
                encoder.encodeInt(value.raw.cint)
            } else {
                encoder.encodeString(value.raw)
            }
        }

        override fun deserialize(decoder: Decoder): Slot {
            return when (val element = (decoder as JsonDecoder).decodeJsonElement()) {
                is JsonPrimitive -> Slot(element.content)
                else -> error("Unsupported slot type: ${element.javaClass.simpleName}")
            }
        }

    }

}