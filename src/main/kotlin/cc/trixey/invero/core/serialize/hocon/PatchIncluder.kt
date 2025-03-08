package cc.trixey.invero.core.serialize.hocon

import taboolib.common.platform.function.info
import java.io.File

/**
 * Invero
 * cc.trixey.invero.serialize.HoconIncluderPatch
 *
 * @author Arasple
 * @since 2023/1/14 20:17
 */
object PatchIncluder {

    /*
    2023-1-14

    [taboolib-module-configuration]

    HOCON 配置实现依赖于 night-config 库，而其对 HOCON 的解析又依赖于 typesafe.config
    后者默认的 INCLUDER 对 Bukkit 平台的文件路径不能理想化兼容（需要从服务端根目录开始书写）
    显然对用户体验很差

    include "plugins/Invero/workspace/xxx.conf"

    尝试覆写过程中遇到很多奇怪的问题，无奈能力有限，只能出此下策
    等有缘人出手救我
     */

    /*
    下面通过正则匹配替换来实现我心目中理想的简写语法
    即：未经指定目录的情况下，默认同级目录开始

    * 参考 include 语法：https://github.com/lightbend/config/blob/master/HOCON.md#includes
    已知四类，且语句之间、引号和括号之间允许任意数量的空白

    resource type = file, classpath, url，我们只管 file 类别的

    include "conf.xxx" ---> 会被当 classPath，因此需要套 file()
    include file("www")
    include required("")
    include required(file("xxx\" "))

    此方法显而易见的局限性就是如果 HOCON 配置文件的内容中有任何被匹配到的字符串，都会被替换处理
    通常情况下来说是不会有这种应用场景的
    关闭 PATCH 的方法是文件第一行加 // IGNORE_HOCON_PATCH
     */

    private val PATTERNS = mapOf(
        Regex("include( +)\"(.+)?\"") to 2,
        Regex("include( +)(required|file)( +)?\\(( +)?\"(.+)?\"( +)?\\)") to 5,
        Regex("include( +)required( +)?\\(( +)?(file)( +)?\\(( +)?\"(.+)?\"( +)?\\)( +)?\\)") to 7,
    )

    /**
     * TODO
     *
     * include workspace("") -- 默认前缀 plugins/Invero/workspace
     */

    fun patchHoconFile(file: File): String {
        val path = file.parentFile.path.replace("\\", "/")
        var fileReaded = file.readText()

        if (fileReaded.startsWith("// IGNORE_HOCON_PATCH")) {
            info("[HOCON PATCH] [${file.name}] ignored hocon patch")
            return fileReaded
        }

        PATTERNS.forEach { (regex, groupIndex) ->
            fileReaded = fileReaded.replace(regex) { match ->
                val resource = match.groupValues[groupIndex]
                if (resource.shouldIgnore()) {
                    match.value
                } else {
                    "include file(\"$path/$resource\")".also {
//                        info("[HOCON PATCH] [${file.name}] Changed [ ${match.value} ] to [ $it ]")
                    }
                }
            }
        }

        return fileReaded
    }

    /**
     * PATCH 只处理同级单文件名的情况，任何存在路径的语句我们不会接管
     */
    private fun String.shouldIgnore(): Boolean {
        return startsWith("http") || sumOf { if (it == '/' || it == '\\') 1L else 0L } > 2 || contains(":")
    }

}