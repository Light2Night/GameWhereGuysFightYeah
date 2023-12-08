package ui.screens.mainMenu

import Game.Units.Characters.UnitTypes
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.onDrag
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.min
import bigText
import border
import colorBorder
import colorSelectedBorder
import colorText
import colorTextError
import corners
import emptyImageBitmap
import getImageBitmap
import iconSize
import lang
import org.jetbrains.skiko.currentNanoTime
import padding
import properties.user.recruit.RecruitFactory
import properties.user.worldMap.Location
import properties.user.worldMap.WorldMap
import reallyHugePadding
import smallCorners
import ui.composable.Divider
import ui.composable.MedievalButton
import ui.composable.MedievalIcon
import ui.composable.MedievalText
import ui.composable.shaders.MedievalShape
import ui.composable.shaders.StandardBackgroundBrush
import ui.composable.shaders.background
import ui.composable.shaders.texture
import user
import kotlin.random.Random

@Composable
fun WorldSubScreen(
    onStart: (List<UnitTypes>, Location) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .background(getImageBitmap("textures/background/worldmap1.png") ?: emptyImageBitmap)
    ) {
        var selected by remember { mutableStateOf<Location?>(null) }

        Box(modifier = Modifier
            .fillMaxWidth(0.33F)
            .fillMaxHeight()
            .background(StandardBackgroundBrush())
        ) {
            Crossfade(
                selected,
            ) { location ->
                if (location != null) {
                    LocationInfo(
                        location,
                        onStart = onStart,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }

        Divider(modifier = Modifier.fillMaxHeight())

        WorldMap(
            worldMap = user.worldMap,
            selected = selected,
            onSelect = { selected = it },
            modifier = Modifier.weight(1F),
        )
    }
}

@Composable
private fun LocationInfo(
    location: Location,
    onStart: (List<UnitTypes>, Location) -> Unit,
    modifier: Modifier = Modifier,
) {
    val enemies = remember {
        val recruitFactory = RecruitFactory()
        val randomEnemies = mutableStateListOf<UnitTypes>()

        repeat(Random(currentNanoTime()).nextInt(1, 5)) {
            recruitFactory.getPreset(location.enemyTypes.random())?.let { randomEnemies.add(it.data.type) }
        }

        if (randomEnemies.all { it == UnitTypes.HEALER }) {
            val replacement = recruitFactory.getPreset(location.enemyTypes.filterNot { it == 2 }.random())

            replacement?.let { randomEnemies.set(Random(currentNanoTime()).nextInt(0, randomEnemies.size), it.data.type) }
        }

        randomEnemies
    }

    Column(
        modifier = modifier,
    ) {
        MedievalText(
            text = location.name,
            fontSize = bigText,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )

        MedievalText(
            text = location.description,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )

        MedievalText(
            text = "Противники на цій локації:",
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
        UnitListMenu(
            units = enemies,
            modifier = Modifier.fillMaxWidth().weight(1F),
        )

        if (location.level > user.level) {
            MedievalText(
                text = lang.too_small_level.replaceFirstChar { it.uppercaseChar() },
                textAlign = TextAlign.Center,
                color = colorTextError,
                modifier = Modifier.fillMaxWidth(),
            )
        }

        if (user.recruits.selectedList.isEmpty()) {
            MedievalText(
                text = lang.no_recruits.replaceFirstChar { it.uppercaseChar() },
                textAlign = TextAlign.Center,
                color = colorTextError,
                modifier = Modifier.fillMaxWidth(),
            )
        }

        MedievalButton(
            text = lang.start_button.replaceFirstChar { it.uppercaseChar() },
            enabled = enemies.isNotEmpty() && user.recruits.selectedList.isNotEmpty(),
            onClick = { onStart(enemies, location) },
            modifier = Modifier
                .padding(padding)
                .fillMaxWidth()
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun WorldMap(
    worldMap: WorldMap,
    selected: Location?,
    onSelect: (Location) -> Unit,
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current.density
    val mapImage by remember { mutableStateOf(getImageBitmap("textures/background/map.png") ?: emptyImageBitmap) }

    var offsetX by remember { mutableStateOf(0.dp) }
    var offsetY by remember { mutableStateOf(0.dp) }

    var minX by remember { mutableStateOf(0.dp) }
    var minY by remember { mutableStateOf(0.dp) }

    Box(
        modifier = modifier
            .padding(reallyHugePadding)
            .texture(getImageBitmap("textures/background/4.png") ?: emptyImageBitmap, MedievalShape(corners))
            .border(border, colorBorder, MedievalShape(corners))
            .clip(MedievalShape(corners))
            .onDrag {
                offsetX = max(minX, min(0.dp, offsetX + (it.x * density).dp))
                offsetY = max(minY, min(0.dp, offsetY + (it.y * density).dp))
            },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset(offsetX, offsetY)
                .drawBehind {
                    val scaledImageWidth = mapImage.width * density
                    val scaledImageHeight = mapImage.height * density
                    drawImage(mapImage, dstSize = IntSize(scaledImageWidth.toInt(), scaledImageHeight.toInt()))

                    minX = -(mapImage.width * density - size.width * density).dp
                    minY = -(mapImage.height * density - size.height * density).dp
                }
        ) {
            worldMap.locations.forEach { location ->
                LocationMark(
                    location = location,
                    selected = selected?.id == location.id,
                    modifier = Modifier
                        .offset(location.x.dp - (iconSize / 2), location.y.dp - (iconSize / 2))
                        .clickable { onSelect(location) },
                )
            }
        }
    }
}

@Composable
private fun LocationMark(
    location: Location,
    selected: Boolean,
    modifier: Modifier = Modifier,
) {
    MedievalIcon(
        icon = getImageBitmap(location.image) ?: emptyImageBitmap,
        modifier = modifier
            .size(iconSize)
            .border(8.dp, if (selected) colorSelectedBorder else Color.Transparent, MedievalShape(smallCorners))
    )
}

@Composable
private fun UnitListMenu(
    units: List<UnitTypes>,
    modifier: Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        units.forEach { unit ->
            UnitInfoMenu(
                unitType = unit,
            )
        }
    }
}

@Composable
private fun UnitInfoMenu(
    unitType: UnitTypes,
) {
    when (unitType) {
        UnitTypes.BARBARIAN -> Text(
            lang.barbarian_name.replaceFirstChar { it.uppercaseChar() },
            color = colorText,
            fontSize = bigText,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(resource = "fonts/cambria.ttc")),
        )

        UnitTypes.MAGICIAN -> Text(
            lang.magician_name.replaceFirstChar { it.uppercaseChar() },
            color = colorText,
            fontSize = bigText,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(resource = "fonts/cambria.ttc")),
        )

        UnitTypes.HEALER -> Text(
            lang.healer_name.replaceFirstChar { it.uppercaseChar() },
            color = colorText,
            fontSize = bigText,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(resource = "fonts/cambria.ttc")),
        )
    }
}