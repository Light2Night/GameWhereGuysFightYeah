package properties.style

import androidx.compose.ui.graphics.Color
import kotlinx.serialization.Serializable

@Serializable
data class Style(
    var corners: Int = 12,
    var small_corners: Int = 8,
    var padding: Int = 4,
    var bigger_padding: Int = 8,
    var huge_padding: Int = 16,
    var really_huge_padding: Int = 64,
    var border: Int = 2,
    var small_border: Int = 1,

    var image_width: Int = 120,
    var image_height: Int = 160,
    var big_icon_size: Int = 80,
    var icon_size: Int = 50,
    var small_icon_size: Int = 28,
    var tiny_icon_size: Int = 24,
    var resource_width: Int = 100,
    var menu_width: Int = 300,
    var menu_item_height: Int = 36,
    var scrollbar_thickness: Int = 12,

    var transparency: Float = 0.9F,
    var transparency_second: Float = 0.6F,
    var transparency_light: Float = 0.2F,

    var tiny_text: Int = 14,
    var small_text: Int = 16,
    var normal_text: Int = 20,
    var big_text: Int = 26,
    var huge_text: Int = 36,

    var shortAnimationDuration: Int = 200,
    var normalAnimationDuration: Int = 300,
    var longAnimationDuration: Int = 500,

    var color_border: AColor = Color(123, 95, 54).toAColor(),
    var color_selected_border: AColor = Color(143, 115, 74).toAColor(),

    var color_background_darker: AColor = Color(30, 30, 30).toAColor(),
    var color_background: AColor = Color(40, 40, 40).toAColor(),
    var color_background_lighter: AColor = Color(70, 70, 70).toAColor(),
    var color_background_second: AColor = Color(0, 70, 70).toAColor(),
    var color_background_second_lighter: AColor = Color(0, 90, 90).toAColor(),
    var color_background_delete: AColor = Color.Red.toAColor(),

    var color_text_light: AColor = Color(240, 240, 240).toAColor(),
    var color_text: AColor = Color(140, 140, 140).toAColor(),
    var color_text_lighter: AColor = Color(180, 180, 180).toAColor(),
    var color_text_second: AColor = Color(143, 115, 74).toAColor(),
    var color_text_error: AColor = Color.Red.toAColor(),
    var color_text_success: AColor = Color.Green.toAColor(),
    var color_text_italic: AColor = Color(130, 130, 130).toAColor(),
    var color_text_bold: AColor = Color(130, 130, 130).toAColor(),

    var color_no_connection: AColor = Color.Red.toAColor(),
    var color_check_connection: AColor = Color.Yellow.toAColor(),
    var color_connection: AColor = Color.Green.toAColor(),
)