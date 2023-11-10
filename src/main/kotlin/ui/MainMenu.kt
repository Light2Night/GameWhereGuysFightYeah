package ui

import Game.Characters.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.unit.dp
import bigText
import biggerPadding
import border
import colorBackground
import colorBackgroundDarker
import colorBorder
import colorText
import colorTextError
import colorTextLight
import colorTextLighter
import colorTextSecond
import corners
import emptyImageBitmap
import getImageBitmap
import hugePadding
import iconSize
import imageHeight
import imageWidth
import lang
import org.jetbrains.skiko.currentNanoTime
import padding
import properties.Properties
import properties.user.recruit.*
import properties.user.request.GuildRequest
import reallyHugePadding
import resourceWidth
import settings
import smallBorder
import smallCorners
import smallIconSize
import smallText
import transparencySecond
import ui.composable.*
import ui.composable.shaders.*
import user
import kotlin.random.Random

@Composable
fun MainMenu(
    onStart: (List<UnitTypes>, List<UnitTypes>) -> Unit,
    modifier: Modifier = Modifier,
) {
    val tabs by remember {
        mutableStateOf(
            CheckManager(
                CheckValue(MainMenuTab.Guild, true),
                CheckValue(MainMenuTab.Party, false),
                CheckValue(MainMenuTab.World, false),
                CheckValue(MainMenuTab.Settings, false),
            )
        )
    }

    Column(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(StandardBackgroundBrush())
        ) {
            Tabs(
                tabs = tabs,
                modifier = Modifier.fillMaxWidth(),
            )

            Divider(
                orientation = Orientation.Horizontal,
                modifier = Modifier.fillMaxWidth()
            )
        }

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
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            tabs.getKeys().forEach { tab ->
                Tab(
                    tab = tab,
                    selected = tabs.getChecked() == tab,
                    onClick = { tabs.check(tab) },
                    modifier = Modifier.height(iconSize)
                )
            }

            UserResourcesInfo(modifier = Modifier.padding(start = biggerPadding))
        }

        UserInfo()
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
            color = colorTextLighter,
            fontSize = bigText,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(resource = "fonts/cambria.ttc")),
            modifier = Modifier.padding(biggerPadding),
        )
    }
}

@Composable
private fun UserResourcesInfo(
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(padding),
            modifier = Modifier.width(resourceWidth),
        ) {
            Image(
                bitmap = getImageBitmap("textures/assets/coin.png") ?: emptyImageBitmap,
                contentDescription = "coin icon",
                modifier = Modifier.size(smallIconSize),
            )

            MedievalText(
                user.coins.toString(),
                fontSize = bigText,
                fontWeight = FontWeight.Bold,
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(padding),
            modifier = Modifier.width(resourceWidth),
        ) {
            Image(
                bitmap = getImageBitmap("textures/assets/crystal.png") ?: emptyImageBitmap,
                contentDescription = "crystal icon",
                modifier = Modifier.size(smallIconSize),
            )

            MedievalText(
                user.crystals.toString(),
                fontSize = bigText,
                fontWeight = FontWeight.Bold,
            )
        }
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
        Column(
            horizontalAlignment = Alignment.End,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(padding),
            ) {
                MedievalText(
                    "${lang.level_short.uppercase()}. ${user.lvl}",
                    color = colorTextSecond,
                )

                MedievalText(
                    user.name,
                    fontWeight = FontWeight.Bold,
                )
            }

            Box(
                contentAlignment = Alignment.Center,
            ) {
                MedievalProgressbar(
                    value = user.exp,
                    min = 0,
                    max = user.expTarget,
                    modifier = Modifier.size(256.dp, 16.dp),
                )

                MedievalText(
                    text = "${lang.exp_short.uppercase()}: ${user.exp}/${user.expTarget}",
                    fontSize = smallText,
                    color = colorTextLight,
                )
            }
        }

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
            .background(getImageBitmap("textures/background/guild1.png") ?: emptyImageBitmap)
    ) {
        Recruits(
            modifier = Modifier
                .fillMaxWidth(0.33F)
                .fillMaxHeight()
                .background(StandardBackgroundBrush())
        )

        RequestBoard(
            modifier = Modifier
                .fillMaxSize()
                .padding(reallyHugePadding)
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
                user.recruits.guildList.forEach { recruit ->
                    RecruitUnitCard(
                        recruit = recruit,
                        onClick = {
                            if (recruit.cost?.isAvailableToBuy == true) {
                                user.recruits.buyRecruit(recruit)
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
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
    recruit: Recruit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(padding)
            .background(StandardBackgroundBrush(), MedievalShape(smallCorners))
            .border(smallBorder, colorBorder, MedievalShape(smallCorners))
            .clip(MedievalShape(smallCorners)),
    ) {
        Image(
            bitmap = getImageBitmap(recruit.profileImage) ?: emptyImageBitmap,
            contentDescription = recruit.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(imageHeight)
                .width(imageWidth)
                .padding(padding)
                .background(colorBackground, MedievalShape(smallCorners))
                .border(smallBorder, colorBorder, MedievalShape(smallCorners))
                .clip(MedievalShape(smallCorners)),
        )

        Column {
            MedievalText(
                text = recruit.name,
                fontSize = bigText,
                fontWeight = FontWeight.Bold,
            )

            MedievalText(
                text = when (recruit.data.type) {
                    UnitTypes.BARBARIAN -> lang.barbarian_name.replaceFirstChar { it.uppercaseChar() }
                    UnitTypes.MAGICIAN -> lang.magician_name.replaceFirstChar { it.uppercaseChar() }
                    UnitTypes.HEALER -> lang.healer_name.replaceFirstChar { it.uppercaseChar() }
                },
                fontWeight = FontWeight.Bold,
            )

            RecruitUnitTextData(
                recruitData = recruit.data,
                modifier = Modifier,
            )

            recruit.cost?.let { cost ->
                MedievalText(
                    text = "${cost.coins} coins | ${cost.crystal} crystals",
                    color = if (cost.isAvailableToBuy) colorText else colorTextError,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

@Composable
private fun RecruitUnitTextData(
    recruitData: RecruitData,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        MedievalText("HP: ${recruitData.maxHP}")

        when (recruitData) {
            is BarbarianData -> {
                MedievalText("DMG: ${recruitData.damage - recruitData.damageDelta}-${recruitData.damage}")
            }

            is MageData -> {
                MedievalText("DMG: ${recruitData.damage - recruitData.damageDelta}-${recruitData.damage}")
                MedievalText("EFF: ${recruitData.magicalEffectDamage} (${recruitData.magicalEffectTurns} turns)")
            }

            is HealerData -> {
                MedievalText("HEAL: ${recruitData.heal}")
                MedievalText("EFF: ${recruitData.healingEffectHeal} (${recruitData.healingEffectTurns} turns)")
            }
        }
    }
}

@Composable
private fun RequestBoard(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .texture(getImageBitmap("textures/background/3.png") ?: emptyImageBitmap, MedievalShape(corners))
            .border(border, colorBorder, MedievalShape(corners))
            .clip(MedievalShape(corners)),
    ) {
        Row {
            repeat(3) { x ->
                Column(
                    modifier = Modifier
                        .weight(1F),
                ) {
                    repeat(2) { y ->
                        RequestCard(
                            request = user.requests.getRequestByPosition(x, y),
                            modifier = Modifier
                                .weight(1F)
                                .fillMaxWidth()
                                .padding(hugePadding)
                                .offset(
                                    Random(currentNanoTime()).nextInt(
                                        0,
                                        hugePadding.minus(2.dp).times(2).value.toInt()
                                    ).dp - hugePadding.minus(2.dp),
                                    Random(currentNanoTime()).nextInt(
                                        0,
                                        hugePadding.minus(2.dp).times(2).value.toInt()
                                    ).dp - hugePadding.minus(2.dp),
                                ),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun RequestCard(
    request: GuildRequest?,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        if (request != null) {
            MedievalBox(
                background = SolidColor(Color.Transparent),
                modifier = Modifier
                    .fillMaxSize()
                    .texture(
                        getImageBitmap("textures/background/4.png") ?: emptyImageBitmap,
                        MedievalShape(smallCorners),
                    )
            ) {
                Column {
                    MedievalText(request.name)
                    MedievalText(request.description)
                    MedievalText(request.progressString)
                }
            }
        }
    }
}

@Composable
private fun Party(
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.33F)
                .verticalScroll(rememberScrollState())
        ) {
            user.recruits.list.forEach { recruit ->
                RecruitUnitCard(
                    recruit = recruit,
                    onClick = {},
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Divider(modifier = Modifier.fillMaxHeight())
    }
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
            enabled = allies.isNotEmpty() && enemies.isNotEmpty(),
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