package ui.screens

import Game.Units.Characters.UnitTypes
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.onDrag
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.min
import bigText
import biggerPadding
import border
import colorBackground
import colorBackgroundDarker
import colorBorder
import colorSelectedBorder
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
import longAnimationDuration
import org.jetbrains.skiko.currentNanoTime
import padding
import properties.Properties
import properties.resources.Cost
import properties.user.recruit.*
import properties.user.quest.Quest
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
import properties.user.worldMap.Location
import properties.user.worldMap.WorldMap
import ui.CheckManager
import ui.CheckValue
import ui.MainMenuTab
import user
import kotlin.math.ceil
import kotlin.random.Random

@Composable
fun MainMenu(
    onStart: (List<UnitTypes>, Location) -> Unit,
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

        Crossfade(
            targetState = tabs.getChecked(),
            animationSpec = tween(longAnimationDuration),
            modifier = Modifier.fillMaxSize(),
        ) { checked ->
            when (checked) {
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

    DebugOptions()
}

@Composable
private fun DebugOptions() {
    Column(
        modifier = Modifier
            .background(Color.Black.copy(0.7F))
    ) {
        var isDebugOpen by remember { mutableStateOf(false) }

        MedievalText(
            text = "debug options",
            color = colorTextError,
            modifier = Modifier.clickable { isDebugOpen = !isDebugOpen },
        )

        if (isDebugOpen) {
            MedievalText(
                text = "add test recruit ${user.recruits.selectedList.size}/5",
                color = colorTextError,
                modifier = Modifier
                    .clickable {
                        val recruit = user.recruits.createNewRecruitToGuild()
                        user.resources.coins += recruit.cost?.coins ?: 0
                        user.resources.crystals += recruit.cost?.crystals ?: 0
                        user.recruits.buyRecruit(user.recruits.guildList.last(), replace = false)
                    },
            )

            MedievalText(
                text = "complete quest",
                color = colorTextError,
                modifier = Modifier
                    .clickable {
                        user.quests.list.random().forceComplete()
                        user.quests.checkAllQuests()
                    },
            )

            MedievalText(
                text = "+ 100 exp",
                color = colorTextError,
                modifier = Modifier.clickable { user.resources.exp += 100 }
            )

            MedievalText(
                text = "- 100 exp",
                color = colorTextError,
                modifier = Modifier.clickable { user.resources.exp -= 100 }
            )

            MedievalText(
                text = "+ 100 coins",
                color = colorTextError,
                modifier = Modifier.clickable { user.resources.coins += 100 }
            )

            MedievalText(
                text = "- 100 coins",
                color = colorTextError,
                modifier = Modifier.clickable { user.resources.coins -= 100 }
            )

            MedievalText(
                text = "+ 100 crystals",
                color = colorTextError,
                modifier = Modifier.clickable { user.resources.crystals += 100 }
            )

            MedievalText(
                text = "- 100 crystals",
                color = colorTextError,
                modifier = Modifier.clickable { user.resources.crystals -= 100 }
            )
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
                user.resources.coins.toString(),
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
                user.resources.crystals.toString(),
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
                    "${lang.level_short.uppercase()}. ${user.level}",
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
                    value = user.resources.exp,
                    min = 0,
                    max = user.requiredExp,
                    modifier = Modifier.size(256.dp, 16.dp),
                )

                MedievalText(
                    text = "${lang.exp_short.uppercase()}: ${user.resources.exp}/${user.requiredExp}",
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

        Divider(modifier = Modifier.fillMaxHeight())

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
        Box(
            modifier = Modifier
                .height(imageHeight)
                .width(imageWidth)
                .padding(padding)
                .background(colorBackground, MedievalShape(smallCorners))
                .border(smallBorder, colorBorder, MedievalShape(smallCorners))
                .clip(MedievalShape(smallCorners)),
        ) {
            Image(
                bitmap = getImageBitmap(recruit.profileImage) ?: emptyImageBitmap,
                contentDescription = recruit.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )

            Stars(
                amount = recruit.stars,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Brush.verticalGradient(listOf(
                        Color.Transparent,
                        colorBackground.copy(alpha = transparencySecond),
                        colorBackground,
                    )))
                    .align(Alignment.BottomCenter),
            )
        }

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
                Cost(cost)
            }
        }
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
private fun Stars(
    amount: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(padding, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        repeat(3) {
            Box {
                if (it < amount) {
                    MedievalText(
                        "★",
                        fontSize = bigText,
                        color = colorTextSecond,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                MedievalText(
                    "★",
                    fontSize = bigText,
                    color = Color.Black,
                    style = TextStyle.Default.copy(
                        drawStyle = Stroke(
                            miter = 1f,
                            width = 1f,
                            join = StrokeJoin.Round
                        )
                    ),
                    modifier = Modifier.align(Alignment.Center)
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
private fun Cost(
    cost: Cost,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        if (cost.coins > 0) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(padding),
            ) {
                Image(
                    bitmap = getImageBitmap("textures/assets/coin.png") ?: emptyImageBitmap,
                    contentDescription = "coin icon",
                    modifier = Modifier.size(smallIconSize),
                )

                MedievalText(
                    cost.coins.toString(),
                    color = if (user.resources.coins >= cost.coins) colorText else colorTextError,
                    fontSize = bigText,
                    fontWeight = FontWeight.Bold,
                )
            }
        }

        if (cost.crystals > 0) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(padding),
            ) {
                Image(
                    bitmap = getImageBitmap("textures/assets/crystal.png") ?: emptyImageBitmap,
                    contentDescription = "crystal icon",
                    modifier = Modifier.size(smallIconSize),
                )

                MedievalText(
                    cost.crystals.toString(),
                    color = if (user.resources.crystals >= cost.crystals) colorText else colorTextError,
                    fontSize = bigText,
                    fontWeight = FontWeight.Bold,
                )
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
                            request = user.quests.getQuestByPosition(x, y),
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
    request: Quest?,
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
    Row(
        modifier = modifier
            .fillMaxSize()
            .background(getImageBitmap("textures/background/party1.png") ?: emptyImageBitmap)
    ) {
        PartyList(modifier = Modifier
            .fillMaxWidth(0.33F)
            .fillMaxHeight()
            .background(StandardBackgroundBrush()),
        )
        Divider(modifier = Modifier.fillMaxHeight())
        RecruitedList(modifier = Modifier.fillMaxSize())
    }
}

@Composable
private fun PartyList(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        user.recruits.selectedList.forEach { recruit ->
            RecruitUnitCard(
                recruit = recruit,
                onClick = {
                    user.recruits.deselectRecruit(recruit)
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun RecruitedList(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        val recruits = user.recruits.list

        repeat(ceil(recruits.size / 2.0).toInt()) { index1 ->
            Row {
                repeat(2) { index2 ->
                    if (index1 * 2 + index2 < recruits.size) {
                        RecruitUnitCard(
                            recruit = recruits[index1 * 2 + index2],
                            onClick = {
                                user.recruits.selectRecruit(recruits[index1 * 2 + index2])
                            },
                            modifier = Modifier.weight(1F)
                        )
                    }

                    if (index1 * 2 + index2 == recruits.lastIndex && recruits.size % 2 == 1) {
                        Box(modifier = Modifier.weight(1F))
                    }
                }
            }
        }
    }
}

@Composable
private fun World(
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
                offsetX = max(minX, min(0.dp, offsetX + it.x.dp))
                offsetY = max(minY, min(0.dp, offsetY + it.y.dp))
            },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset(offsetX, offsetY)
                .drawBehind {
                    drawImage(mapImage)

                    minX = -(mapImage.width - size.width).dp
                    minY = -(mapImage.height - size.height).dp
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

        var attackButtons by remember { mutableStateOf(settings.attack_buttons) }
        MedievalButton(
            text = "Action Buttons: $attackButtons",
            onClick = {
                settings.attack_buttons = if (attackButtons == 0) 1 else if (attackButtons == 1) 2 else 0
                attackButtons = settings.attack_buttons
                Properties.saveSettings()
                Properties.loadLanguage(settings.language)
            },
            modifier = Modifier.padding(top = biggerPadding),
        )
    }
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
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun UnitInfoMenu(
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