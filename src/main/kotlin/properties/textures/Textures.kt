package properties.textures

import androidx.compose.ui.graphics.ImageBitmap
import emptyImageBitmap
import getImageBitmap
import java.io.File

object Textures {
    private val folder = File("textures")
    private val textures = mutableMapOf<String, ImageBitmap?>()

    fun load() {
        if (!folder.exists()) return

        val files = folder
            .walkTopDown()
            .filter { it.isFile && (it.extension == "png" || it.extension == "webp") }
            .toList()

        files.forEach {
            textures[it.path.substringAfter("\\")] = getImageBitmap(it)
        }
    }

    operator fun get(path: String): ImageBitmap = textures[path.replace("/", "\\")] ?: emptyImageBitmap

    fun getOrNull(path: String): ImageBitmap? = textures[path.replace("/", "\\")]

    fun loadLoadingScreen() {
        val background = File("textures/background/2.png")
        val progressBar = File("textures/background/button.png")

        if (background.exists()) {
            textures["background/2.png"] = getImageBitmap(background)
        }

        if (progressBar.exists()) {
            textures["background/button.png"] = getImageBitmap(progressBar)
        }
    }
}