package cc.trixey.invero.common.util

import java.io.File
import java.util.jar.JarEntry
import java.util.jar.JarFile

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

/**
 * 在Jar文件中查找
 */
fun findInJar(jar: JarFile, filter: (JarEntry) -> Boolean) = jar.entries().asSequence().filter(filter).map { it to jar.getInputStream(it) }

fun findInJar(srcFile: File, filter: (JarEntry) -> Boolean) = findInJar(JarFile(srcFile), filter)