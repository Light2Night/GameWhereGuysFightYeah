package properties.textures

import androidx.compose.ui.graphics.ImageBitmap
import emptyImageBitmap
import getImageBitmap
import java.io.File

object Textures {
    private val textures = mutableMapOf<String, ImageBitmap?>()

    fun load() {
        val folder = File("textures")
        if (!folder.exists()) return

        val files = folder
            .walkTopDown()
            .filter { it.isFile && (it.extension == "png" || it.extension == "webp") }
            .toList()

        files.forEach {
            println(it.path.substringAfter("\\"))
            textures[it.path.substringAfter("\\")] = getImageBitmap(it)
        }
    }

    operator fun get(path: String): ImageBitmap = run {
        textures[path.replace("/", "\\")] ?: emptyImageBitmap
    }
}