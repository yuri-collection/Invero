package cc.trixey.invero.common.util

import java.io.File

/**
 * Invero
 * cc.trixey.invero.core.util.File
 *
 * @author Arasple
 * @since 2023/1/15 21:48
 */
fun File.listRecursively(): List<File> {
    val result = mutableListOf<File>()

    if (!isDirectory) {
        result += this
        return result
    } else {
        listFiles()?.forEach { result += it.listRecursively() }
    }

    return result
}