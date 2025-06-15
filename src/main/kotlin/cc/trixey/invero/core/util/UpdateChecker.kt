package cc.trixey.invero.core.util

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import taboolib.common.platform.function.adaptCommandSender
import taboolib.common.platform.function.adaptPlayer
import taboolib.module.lang.sendLang
import taboolib.platform.util.bukkitPlugin
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.regex.Pattern

/**
 * 更新检查器，负责检查插件是否有更新
 */
class UpdateChecker(private val repository: String = "8aka-Team/Invero") {
    
    private val currentVersion = bukkitPlugin.description.version
    private var latestVersion: String? = null
    private val apiUrl = "https://api.github.com/repos/$repository/releases/latest"
    
    /**
     * 检查更新
     * @param callback 回调函数，参数为 (是否有更新, 最新版本)
     */
    fun checkForUpdates(callback: (Boolean, String) -> Unit) {
        // 在异步线程中执行
        Bukkit.getScheduler().runTaskAsynchronously(bukkitPlugin, Runnable {
            try {
                // 获取最新版本信息
                val connection = URL(apiUrl).openConnection()
                connection.connectTimeout = 5000
                connection.readTimeout = 5000
                connection.setRequestProperty("Accept", "application/vnd.github.v3+json")
                connection.setRequestProperty("User-Agent", "Invero UpdateChecker")
                
                val reader = BufferedReader(InputStreamReader(connection.getInputStream()))
                val content = StringBuilder()
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    content.append(line)
                }
                reader.close()
                
                // 解析版本号
                val jsonContent = content.toString()
                val tagPattern = Pattern.compile("\"tag_name\"\\s*:\\s*\"(.*?)\"")
                val matcher = tagPattern.matcher(jsonContent)
                
                if (matcher.find()) {
                    latestVersion = matcher.group(1).replace("v", "")
                    
                    // 在主线程中执行回调
                    Bukkit.getScheduler().runTask(bukkitPlugin, Runnable {
                        val hasUpdate = compareVersions(currentVersion, latestVersion!!) < 0
                        callback(hasUpdate, latestVersion!!)
                    })
                } else {
                    // 无法解析版本号
                    Bukkit.getScheduler().runTask(bukkitPlugin, Runnable {
                        callback(false, currentVersion)
                    })
                }
            } catch (e: Exception) {
                // 发生异常
                bukkitPlugin.logger.warning("检查更新时发生错误: ${e.message}")
                Bukkit.getScheduler().runTask(bukkitPlugin, Runnable {
                    callback(false, currentVersion)
                })
            }
        })
    }

    /**
     * 向玩家发送更新信息
     * @param player 接收信息的玩家
     */
    fun sendUpdateInfo(player: Player) {
        // 发送检查更新消息
        adaptPlayer(player).sendLang("update-checking")
        
        checkForUpdates { isUpdateAvailable, newVersion ->
            if (isUpdateAvailable) {
                // 发送有更新可用的消息
                adaptPlayer(player).sendLang("update-available", currentVersion, newVersion)
                adaptPlayer(player).sendLang("update-url", currentVersion, newVersion)
            } else {
                // 发送已是最新版本的消息
                adaptPlayer(player).sendLang("up-to-date")
            }
        }
    }
    
    /**
     * 向控制台发送更新信息
     */
    fun sendUpdateInfoToConsole() {
        // 发送检查更新消息
        adaptCommandSender(Bukkit.getConsoleSender()).sendLang("update-checking")
        
        checkForUpdates { isUpdateAvailable, newVersion ->
            if (isUpdateAvailable) {
                // 发送有更新可用的消息
                adaptCommandSender(Bukkit.getConsoleSender()).sendLang("update-available", currentVersion, newVersion)
                adaptCommandSender(Bukkit.getConsoleSender()).sendLang("update-url", currentVersion, newVersion)
            } else {
                // 发送已是最新版本的消息
                adaptCommandSender(Bukkit.getConsoleSender()).sendLang("up-to-date")
            }
        }
    }
    
    /**
     * 比较版本号
     * @return 如果v1 < v2返回负数，v1 > v2返回正数，v1 = v2返回0
     */
    private fun compareVersions(v1: String, v2: String): Int {
        val parts1 = v1.split(".")
        val parts2 = v2.split(".")
        val maxLength = maxOf(parts1.size, parts2.size)
        
        for (i in 0 until maxLength) {
            val part1 = if (i < parts1.size) parts1[i].toIntOrNull() ?: 0 else 0
            val part2 = if (i < parts2.size) parts2[i].toIntOrNull() ?: 0 else 0
            
            if (part1 < part2) {
                return -1
            } else if (part1 > part2) {
                return 1
            }
        }
        
        return 0
    }
}
