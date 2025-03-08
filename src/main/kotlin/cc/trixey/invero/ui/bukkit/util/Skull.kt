package cc.trixey.invero.ui.bukkit.util

import org.bukkit.inventory.ItemStack
import taboolib.library.xseries.XMaterial
import taboolib.platform.util.BukkitSkull

fun requestHead(identifier: String, response: (ItemStack) -> Unit) {
    val itemStack = XMaterial.PLAYER_HEAD.parseItem()!!
    BukkitSkull.applySkull(itemStack, identifier).also(response)
}

//private fun requestCustomTextureHead(texture: String) =
//    cachedSkulls
//        .computeIfAbsent(texture) {
//            defaultHead.clone().modifyHeadTexture(texture)
//        }
//        .clone()
//
//private fun requestPlayerHead(name: String, response: (ItemStack) -> Unit) {
//    if (name in cachedSkulls.keys) {
//        cachedSkulls[name]?.also(response)
//    } else {
//        val player = Bukkit.getPlayerExact(name)
//        val playerTexture = player?.getPlayerTexture()
//        if (player != null && playerTexture != null) {
//            requestCustomTextureHead(playerTexture).also(response)
//        } else {
//            response(defaultHead.modifyMeta<ItemMeta> { setDisplayName("§8...") })
//
//            submitAsync {
//                val profile = JsonParser().parse(fromURL("${mojangAPI[0]}$name")) as? JsonObject
//                if (profile == null) {
////                    console().sendMessage("§c[I] §7MOJANGAPI Can not found offline player [$name]")
//                } else {
//                    val uuid = profile["id"].asString
//                    (JsonParser().parse(fromURL("${mojangAPI[1]}$uuid")) as JsonObject)
//                        .getAsJsonArray("properties")
//                        .any {
//                            if ("textures" == it.asJsonObject["name"].asString) {
//                                requestCustomTextureHead(it.asJsonObject["value"].asString).also { head ->
//                                    response(head)
//                                    cachedSkulls[name] = head
//                                }
//                                true
//                            } else false
//                        }
//                }
//            }
//        }
//    }
//}