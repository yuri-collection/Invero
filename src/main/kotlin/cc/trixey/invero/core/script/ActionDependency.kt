package cc.trixey.invero.core.script

import cc.trixey.invero.core.script.loader.InveroKetherParser
import org.bukkit.Bukkit
import taboolib.common.platform.function.warning
import taboolib.module.kether.*

/**
 * Invero
 * cc.trixey.invero.core.script.ActionDependency
 * 
 * 提供依赖检查相关的Kether动作
 */
object ActionDependency {

    @InveroKetherParser(["depend"])
    fun parser() = scriptParser {
        val dependType = it.nextToken()?.lowercase() ?: error("缺少依赖类型参数 (plugin 或 papi)")
        
        when (dependType) {
            "plugin" -> {
                // 获取插件名称参数
                val pluginName = it.nextToken() ?: error("缺少插件名称参数")
                
                // 构建一个简单的返回布尔值的动作
                actionNow { 
                    // 检查插件是否加载，支持模糊匹配
                    Bukkit.getPluginManager().plugins.any { plugin ->
                        plugin.name.equals(pluginName, ignoreCase = true) || 
                        plugin.name.contains(pluginName, ignoreCase = true)
                    }
                }
            }
            
            "papi" -> {
                // 获取PAPI扩展名称参数
                val expansionName = it.nextToken() ?: error("缺少扩展名称参数")
                
                // 构建一个简单的返回布尔值的动作
                actionNow { 
                    // 先检查PlaceholderAPI本身是否加载
                    val papiPlugin = Bukkit.getPluginManager().getPlugin("PlaceholderAPI")
                    if (papiPlugin == null || !papiPlugin.isEnabled) {
                        return@actionNow false
                    }
                    
                    // 使用直接方法检查扩展是否已注册
                    try {
                        // 获取PlaceholderAPI类
                        val placeholderAPIClass = Class.forName("me.clip.placeholderapi.PlaceholderAPI")
                        
                        // 获取已注册的扩展标识符集合
                        val getRegisteredIdentifiersMethod = placeholderAPIClass.getMethod("getRegisteredIdentifiers")
                        val registeredIdentifiers = getRegisteredIdentifiersMethod.invoke(null) as Set<*>
                        
                        // 检查扩展是否在已注册列表中（不区分大小写）
                        registeredIdentifiers.any { identifier -> 
                            identifier.toString().equals(expansionName, ignoreCase = true)
                        }
                    } catch (e: Exception) {
                        warning("检查PAPI扩展失败: ${e.message}")
                        
                        // 备用方法：通过测试占位符返回值是否变化来检测
                        try {
                            val placeholderAPIClass = Class.forName("me.clip.placeholderapi.PlaceholderAPI")
                            val testPlaceholder = "%${expansionName}_test%"
                            val parsePlaceholders = placeholderAPIClass.getMethod("setPlaceholders", 
                                Class.forName("org.bukkit.entity.Player"), String::class.java)
                            val result = parsePlaceholders.invoke(null, null, testPlaceholder) as? String
                            
                            // 如果解析后的结果与原始占位符不同，则表示扩展可能存在
                            result != testPlaceholder
                        } catch (ex: Exception) {
                            warning("检查PAPI扩展备用方法失败: ${ex.message}")
                            false
                        }
                    }
                }
            }
            
            else -> {
                error("未知的依赖类型: $dependType. 支持的类型: plugin, papi")
            }
        }
    }
} 