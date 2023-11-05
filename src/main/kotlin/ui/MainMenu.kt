package ui

import Game.Characters.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import bigText
import biggerPadding
import colorBackground
import colorBackgroundDarker
import colorBorder
import colorText
import colorTextSecond
import emptyImageBitmap
import getImageBitmap
import iconSize
import imageHeight
import imageWidth
import lang
import padding
import properties.Properties
import settings
import smallBorder
import smallCorners
import transparencySecond
import ui.composable.*
import ui.composable.shaders.ButtonBrush
import ui.composable.shaders.MedievalShape
import ui.composable.shaders.StandardBackgroundBrush
import user

@Composable
fun MainMenu(
    onStart: (List<UnitTypes>, List<UnitTypes>) -> Unit,
    modifier: Modifier = Modifier,
) {
    val tabs by remember { mutableStateOf(CheckManager(
        CheckValue(MainMenuTab.Guild, true),
        CheckValue(MainMenuTab.Party, false),
        CheckValue(MainMenuTab.World, false),
        CheckValue(MainMenuTab.Settings, false),
    )) }

    Column(
        modifier = modifier
    ) {
        Tabs(
            tabs = tabs,
            modifier = Modifier.fillMaxWidth(),
        )

        when (tabs.getChecked()) {
            MainMenuTab.Guild -> Guild(
                modifier = Modifier.fillMaxSize(),
            )
            MainMenuTab.Party -> Party(
                modifier = Modifier.fillMaxSize(),
            )
            MainMenuTab.World -> World(
                onStart = onStart,
                modifier = Modifier.fillMaxSize(),
            )
            MainMenuTab.Settings -> Settings(
                modifier = Modifier.fillMaxSize(),
            )
            null -> Text("<ERROR>")
        }
    }
}

@Composable
private fun Tabs(
    tabs: CheckManager<MainMenuTab>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(StandardBackgroundBrush())
            .background(colorBackgroundDarker.copy(alpha = transparencySecond))
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row {
                tabs.getKeys().forEach { tab ->
                    Tab(
                        tab = tab,
                        selected = tabs.getChecked() == tab,
                        onClick = { tabs.check(tab) },
                        modifier = Modifier.height(iconSize)
                    )
                }
            }

            UserInfo()
        }

        Divider(
            orientation = Orientation.Horizontal,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun Tab(
    tab: MainMenuTab,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clickable(onClick = onClick, enabled = !selected)
            .background(ButtonBrush())
            .background(if (selected) colorBackgroundDarker.copy(alpha = transparencySecond) else Color.Transparent)
            .border(smallBorder, colorBorder),
    ) {
        Text(
            tab.toString(),
            color = colorTextSecond,
            fontSize = bigText,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(resource = "fonts/cambria.ttc")),
            modifier = Modifier.padding(biggerPadding),
        )
    }
}

@Composable
private fun UserInfo(
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Text(
            user.name,
            color = colorText,
            fontSize = bigText,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(resource = "fonts/cambria.ttc")),
        )

        MedievalIcon(
            icon = user.profileImage ?: emptyImageBitmap,
            modifier = Modifier
                .size(iconSize)
                .padding(padding)
        )
    }
}

@Composable
private fun Guild(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .drawBehind {
                drawImage(
                    image = getImageBitmap("textures/background/guild1.png") ?: emptyImageBitmap,
                    dstOffset = IntOffset.Zero,
                    dstSize = IntSize(size.width.toInt(), size.height.toInt()),
                )
            }
    ) {
        Recruits(
            modifier = Modifier
                .fillMaxWidth(0.33F)
                .fillMaxHeight()
                .background(StandardBackgroundBrush())
        )
    }
}

@Composable
private fun Recruits(
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        Column(modifier = Modifier.weight(1F)) {
            Row {
                Chest(modifier = Modifier.weight(1F))
                Chest(modifier = Modifier.weight(1F))
                Chest(modifier = Modifier.weight(1F))
            }

            Divider(orientation = Orientation.Horizontal, modifier = Modifier.fillMaxWidth())

            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                RecruitUnitCard(
                    unit = Magician(null, null, 0),
                    onClick = {},
                    modifier = Modifier.fillMaxWidth()
                )

                RecruitUnitCard(
                    unit = Barbarian(null, null, 1),
                    onClick = {},
                    modifier = Modifier.fillMaxWidth()
                )

                RecruitUnitCard(
                    unit = Healer(null, null, 2),
                    onClick = {},
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Divider(modifier = Modifier.fillMaxHeight())
    }
}

@Composable
private fun Chest(
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(padding),
    ) {
        MedievalIcon(
            icon = getImageBitmap("textures/assets/chest1.png") ?: emptyImageBitmap,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1F),
        )

        MedievalText(
            text = "Chest",
            fontSize = bigText,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
private fun RecruitUnitCard(
    unit: GameUnit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier
            .clickable(onClick = onClick)
            .padding(padding)
            .background(StandardBackgroundBrush(), MedievalShape(smallCorners.value))
            .border(smallBorder, colorBorder, MedievalShape(smallCorners.value))
            .clip(MedievalShape(smallCorners.value)),
    ) {
        Image(
            bitmap = getImageBitmap(
                when (unit) {
                    is Barbarian -> { "textures/characters/barbarian_placeholder.png" }
                    is Magician -> { "textures/characters/magician_placeholder.png" }
                    is Healer -> { "textures/characters/healer_placeholder.png" }
                    else -> ""
                }
            ) ?: emptyImageBitmap,
            contentDescription = unit.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(imageHeight)
                .width(imageWidth)
                .padding(padding)
                .background(colorBackground, MedievalShape(smallCorners.value))
                .border(smallBorder, colorBorder, MedievalShape(smallCorners.value))
                .clip(MedievalShape(smallCorners.value)),
        )

        Column {
            MedievalText(
                text = "${unit.id} - ${
                    when (unit) {
                        is Barbarian -> lang.barbarian_name.replaceFirstChar { it.uppercaseChar() }
                        is Magician -> lang.magician_name.replaceFirstChar { it.uppercaseChar() }
                        is Healer -> lang.healer_name.replaceFirstChar { it.uppercaseChar() }
                        else -> ""
                    }
                }",
                fontSize = bigText,
                fontWeight = FontWeight.Bold,
            )

            RecruitUnitTextData(
                unit = unit,
                modifier = Modifier,
            )
        }
    }
}

@Composable
private fun RecruitUnitTextData(
    unit: GameUnit,
    modifier: Modifier = Modifier,
) {
    Column (modifier = modifier) {
        MedievalText("HP: ${unit.hp}/${unit.maxHp}",)

        when (unit) {
            is Barbarian -> {
                MedievalText("DMG: ${unit.damage - unit.damageDelta}-${unit.damage}")
            }

            is Magician -> {
                MedievalText("DMG: ${unit.damage - unit.damageDelta}-${unit.damage}")

                val effect = unit.magicalEffect
                MedievalText("EFF: 15 (2 turns)")
            }

            is Healer -> {
                MedievalText("HEAL: ${unit.heal}")

                val effect = unit.healingEffect
                MedievalText("EFF: 10 (3 turns)")
            }
        }
    }
}

@Composable
private fun Party(
    modifier: Modifier = Modifier,
) {

}

@Composable
private fun World(
    onStart: (List<UnitTypes>, List<UnitTypes>) -> Unit,
    modifier: Modifier = Modifier,
) {
    val allies = remember { mutableStateListOf<UnitTypes>() }
    val enemies = remember { mutableStateListOf<UnitTypes>() }

    Row(
        modifier = modifier
    ) {
        UnitList(
            units = allies,
            onAddUnit = { allies.add(it) },
            modifier = Modifier.weight(1F),
        )

        Divider(modifier = Modifier.fillMaxHeight())

        MedievalButton(
            text = lang.start_button.replaceFirstChar { it.uppercaseChar() },
            onClick = { onStart(allies, enemies) },
            modifier = Modifier.fillMaxWidth(0.33F)
        )

        Divider(modifier = Modifier.fillMaxHeight())

        UnitList(
            units = enemies,
            onAddUnit = { enemies.add(it) },
            modifier = Modifier.weight(1F),
        )
    }
}

@Composable
private fun Settings(
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        MedievalButton(
            text = "Українська",
            onClick = {
                settings.language = "ua"
                Properties.saveSettings()
                Properties.loadLanguage(settings.language)
            },
        )

        MedievalButton(
            text = "English",
            onClick = {
                settings.language = "en"
                Properties.saveSettings()
                Properties.loadLanguage(settings.language)
            },
        )
    }
}

@Composable
private fun UnitList(
    units: List<UnitTypes>,
    onAddUnit: (UnitTypes) -> Unit,
    modifier: Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        MedievalButton(
            text = "Додати варвара",
            onClick = { onAddUnit(UnitTypes.BARBARIAN) },
        )

        MedievalButton(
            text = "Додати мага",
            onClick = { onAddUnit(UnitTypes.MAGICIAN) },
        )

        MedievalButton(
            text = "Додати цілитель",
            onClick = { onAddUnit(UnitTypes.HEALER) },
        )

        units.forEach { unit ->
            UnitInfo(
                unitType = unit,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun UnitInfo(
    unitType: UnitTypes,
    modifier: Modifier = Modifier,
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