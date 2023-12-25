package raylras.zen.util

import java.math.BigInteger
import java.net.URI
import java.nio.charset.StandardCharsets
import java.nio.file.Path
import java.security.MessageDigest
import kotlin.io.path.exists
import kotlin.io.path.toPath

fun toPath(uri: String): Path {
    return URI.create(uri).toPath().toRealPath()
}

fun findUpwardsOrSelf(start: Path, targetName: String): Path {
    return findUpwards(start, targetName) ?: start
}

fun findUpwards(start: Path, targetName: String): Path? {
    var current: Path? = start
    while (current != null) {
        val target = current.resolve(targetName)
        if (target.exists()) {
            return target
        }
        current = current.parent
    }
    return null
}

fun toHash(path: Path): String {
    val sha1 = MessageDigest.getInstance("SHA-1")
    val bytes = path.toString().toByteArray(StandardCharsets.UTF_8)
    val hash = BigInteger(1, sha1.digest(bytes))
    val hex = hash.toString(16)
    return hex.padStart(40, '0')
}
