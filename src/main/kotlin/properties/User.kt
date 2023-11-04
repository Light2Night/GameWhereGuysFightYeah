package properties

import androidx.compose.ui.graphics.ImageBitmap
import getImageBitmap
import kotlinx.serialization.Serializable

@Serializable
data class User(
    var name: String = "Test_Name",
) {
    val profileImage: ImageBitmap? get() =
        getImageBitmap("data/user/user.webp")
            ?: getImageBitmap("data/user/user.png")
}
