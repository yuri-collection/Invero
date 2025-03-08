package cc.trixey.invero.core.api

import cc.trixey.invero.common.Invero
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import org.bukkit.Bukkit
import java.net.URL

/**
 * Invero
 * cc.trixey.invero.core.api.JavaScriptUtils
 *
 * @author Arasple
 * @since 2023/2/23 11:16
 */
class JavaScriptUtils {

    private val serializer: Json
        get() = Invero
            .API
            .getMenuManager()
            .getJsonSerializer<Json>()

    /**
     * 读 URL 内容
     */
    fun fromURL(url: String) = try {
        String(URL(url).openStream().readBytes())
    } catch (t: Throwable) {
        "<ERROR: ${t.localizedMessage}>"
    }

    /**
     * JSON 处理
     */
    fun asJsonElemenet(json: String): JsonElement = serializer.decodeFromString(json)

    fun asJsonObject(json: String): JsonObject = serializer.decodeFromString(json)

    fun asJsonArray(json: String): JsonArray = serializer.decodeFromString(json)

    /**
     * Bukkit Player 相关
     */
    fun getPlayer(name: String) = Bukkit.getPlayerExact(name)


}