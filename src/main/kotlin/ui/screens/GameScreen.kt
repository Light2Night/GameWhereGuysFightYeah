package ui.screens

import Game.Units.Characters.Barbarian
import Game.Units.Characters.GameUnit
import Game.Units.Characters.Healer
import Game.Units.Characters.Magician
import Game.Effects.Effectable
import Game.Effects.Healing
import Game.Effects.Poisoning
import Game.Game
import Game.PlayerTypes
import gamedata.GameData
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import bigText
import border
import colorBackground
import colorBorder
import colorSelectedBorder
import colorTextLight
import colorTextLighter
import emptyImageBitmap
import getImageBitmap
import hugeText
import iconSize
import imageHeight
import imageWidth
import kotlinx.coroutines.delay
import normalAnimationDuration
import normalText
import org.jetbrains.skiko.currentNanoTime
import padding
import properties.user.recruit.Recruit
import settings
import smallBorder
import smallCorners
import transparency
import transparencyLight
import transparencySecond
import ui.action.Action
import ui.action.ActionFabric
import ui.Side
import ui.composable.*
import ui.composable.shaders.MedievalShape
import ui.composable.shaders.StandardBackgroundBrush
import kotlin.random.Random
import kotlin.random.nextInt

@Composable
fun GameScreen(
    game: Game,
    gameData: GameData,
    onEnd: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var actions by remember { mutableStateOf(ActionFabric(game, gameData).createActions()) }

    LaunchedEffect(gameData.currentUnit) {
        actions = ActionFabric(game, gameData).createActions()

        if (gameData.currentUnit?.team?.playerType == PlayerTypes.AI) {
            gameData.currentUnit?.moveAI()
            delay(normalAnimationDuration.toLong() * 2 + 1000L)

            game.next()
            gameData.gameResult?.let { onEnd() }

            if (game.getUnitById(gameData.selectedUnit?.id ?: 0) == null) {
                game.setSelectedUnitIndex(
                    gameData.enemies.lastOrNull()?.id ?: gameData.allies.firstOrNull()?.id ?: 0
                )
            }
        }
    }

    Box(
        modifier = modifier
    ) {
        SmokeArea(
            amount = 10,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxSize(),
        )

        UnitList(
            gameData = gameData,
            units = gameData.enemies,
            selectedUnitID = gameData.selectedUnit?.id ?: 0,
            currentUnitID = gameData.currentUnit?.id ?: 0,
            actions = actions,
            side = Side.Down,
            onSelect = { game.setSelectedUnitIndex(it) },
            onAction = {},
            modifier = Modifier
                .padding(top = padding)
                .fillMaxHeight(0.35F)
                .align(Alignment.TopCenter),
        )

        Divider(
            orientation = Orientation.Horizontal,
            Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
        )

        UnitList(
            gameData = gameData,
            units = gameData.allies,
            selectedUnitID = gameData.selectedUnit?.id ?: 0,
            currentUnitID = gameData.currentUnit?.id ?: 0,
            actions = actions,
            side = Side.Up,
            onSelect = { game.setSelectedUnitIndex(it) },
            onAction = {
                game.next()
                gameData.gameResult?.let { onEnd() }

                if (game.getUnitById(gameData.selectedUnit?.id ?: 0) == null) {
                    game.setSelectedUnitIndex(
                        gameData.enemies.lastOrNull()?.id ?: gameData.allies.firstOrNull()?.id ?: 0
                    )
                }
            },
            modifier = Modifier
                .padding(bottom = padding)
                .fillMaxHeight(0.35F)
                .align(Alignment.BottomCenter),
        )

        Crossfade(
            gameData.currentUnit,
            animationSpec = tween(normalAnimationDuration),
            modifier = Modifier.align(Alignment.Center),
        ) { unit ->
            if (unit != null) {
                if (settings.attack_buttons == 1) {
                    Actions(
                        actions = actions,
                        onAction = {
                            game.next()
                            gameData.gameResult?.let { onEnd() }

                            if (game.getUnitById(gameData.selectedUnit?.id ?: 0) == null) {
                                game.setSelectedUnitIndex(
                                    gameData.enemies.lastOrNull()?.id ?: gameData.allies.firstOrNull()?.id ?: 0
                                )
                            }
                        },
                    )
                } else if (settings.attack_buttons == 2) {
                    ActionIcons(
                        actions = actions,
                        onAction = {
                            game.next()
                            gameData.gameResult?.let { onEnd() }

                            if (game.getUnitById(gameData.selectedUnit?.id ?: 0) == null) {
                                game.setSelectedUnitIndex(
                                    gameData.enemies.lastOrNull()?.id ?: gameData.allies.firstOrNull()?.id ?: 0
                                )
                            }
                        },
                    )
                }
            }
        }
    }
}

@Composable
private fun SmokeArea(
    amount: Int,
    modifier: Modifier = Modifier,
) {
    val animations = mutableListOf<InfiniteTransition>()
    repeat(amount) { animations.add(rememberInfiniteTransition()) }

    val xValues = remember { mutableStateListOf<State<Float>>() }
    repeat(amount) { index ->
        val direction = Random(currentNanoTime()).nextBoolean()
        val initial = if (direction) 0F else 1F
        val target = if (direction) 1F else 0F
        val duration = Random(currentNanoTime()).nextInt(30000..60000)

        val x = animations[index].animateFloat(
            initialValue = initial,
            targetValue = target,
            animationSpec = infiniteRepeatable(
                animation = tween(duration, easing = LinearEasing),
                initialStartOffset = StartOffset(
                    offsetMillis = Random(currentNanoTime()).nextInt(0..60000),
                    offsetType = StartOffsetType.FastForward,
                ),
                repeatMode = RepeatMode.Reverse
            )
        )

        xValues.add(x)
    }

    val backgroundImage by remember {
        mutableStateOf(
            if (Random(currentNanoTime().toInt()).nextBoolean()) {
                getImageBitmap("textures/background/forest1.png") ?: emptyImageBitmap
            } else {
                getImageBitmap("textures/background/forest2.png") ?: emptyImageBitmap
            }
        )
    }

    val smokeImage by remember {
        mutableStateOf(getImageBitmap("textures/assets/fog.png") ?: emptyImageBitmap)
    }

    Canvas(
        modifier = modifier
    ) {
        drawImage(
            image = backgroundImage,
            dstOffset = IntOffset.Zero,
            dstSize = IntSize(size.width.toInt(), size.height.toInt()),
        )

        val imageRatio = smokeImage.width.toFloat() / smokeImage.height
        val newImageHeight = (size.height / 1.5).toInt()
        val newImageWidth = (newImageHeight * imageRatio).toInt()
        val startX = -smokeImage.width - 100
        val pathLength = startX + size.width + smokeImage.width + 50

        repeat(amount) { index ->
            drawImage(
                image = smokeImage,
                dstOffset = IntOffset(
                    (startX + pathLength * xValues[index].value).toInt(),
                    (size.height - newImageHeight + Random(index).nextInt(0..50)).toInt(),
                ),
                dstSize = IntSize(newImageWidth, newImageHeight),
                alpha = transparencyLight,
            )
        }
    }
}

@Composable
private fun UnitList(
    gameData: GameData,
    selectedUnitID: Int,
    currentUnitID: Int,
    actions: List<Action>,
    units: List<GameUnit>,
    side: Side,
    onSelect: (Int) -> Unit,
    onAction: () -> Unit,
    modifier: Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(padding),
        modifier = modifier,
    ) {
        units.forEach { unit ->
            UnitInfo(
                unit = unit,
                recruit = gameData.getRecruitByID(unit.id) ?: return@forEach,
                side = side,
                isCurrent = unit.id == currentUnitID,
                actions = actions,
                isSelected = unit.id == selectedUnitID,
                onSelect = { onSelect(unit.id) },
                onAction = onAction,
                modifier = Modifier.fillMaxHeight(),
            )
        }
    }
}

@Composable
private fun UnitInfo(
    unit: GameUnit,
    recruit: Recruit,
    side: Side,
    isCurrent: Boolean,
    actions: List<Action>,
    isSelected: Boolean,
    onSelect: () -> Unit,
    onAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .background(StandardBackgroundBrush(), MedievalShape(smallCorners), transparencySecond)
            .drawBehind {
                drawOutline(
                    MedievalShape(smallCorners).createOutline(size, layoutDirection, this),
                    colorBorder,
                    style = Stroke(smallBorder.toPx()),
                )
            },
    ) {
        Box {
            if (isCurrent && settings.attack_buttons == 0) {
                ActionIcons(
                    actions = actions,
                    onAction = onAction,
                    modifier = Modifier.align(Alignment.BottomStart),
                )
            }

            Box(
                modifier = Modifier
                    .offset(0.dp, if (isCurrent) if (side == Side.Down) (iconSize + padding) else -(iconSize + padding) else 0.dp)
                    .clickable { onSelect() }
                    .fillMaxHeight()
                    .aspectRatio(imageWidth / imageHeight)
                    .padding(padding)
                    .background(StandardBackgroundBrush(), MedievalShape(smallCorners))
                    .border(
                        if (isSelected) border else smallBorder,
                        if (isSelected) colorSelectedBorder else colorBorder,
                        MedievalShape(smallCorners)
                    )
                    .clip(MedievalShape(smallCorners)),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(imageWidth / imageHeight)
                        .padding(padding)
                        .border(smallBorder, colorBorder, MedievalShape(smallCorners))
                        .clip(MedievalShape(smallCorners)),
                ) {
                    Image(
                        bitmap = getImageBitmap(
                            when (unit) {
                                is Barbarian -> "textures/characters/barbarian_placeholder.png"
                                is Magician -> "textures/characters/magician_placeholder.png"
                                is Healer -> "textures/characters/healer_placeholder.png"
                                else -> ""
                            }
                        ) ?: emptyImageBitmap,
                        contentDescription = unit.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(colorBackground, MedievalShape(smallCorners))
                            .border(smallBorder, colorBorder, MedievalShape(smallCorners))
                            .clip(MedievalShape(smallCorners)),
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(StandardBackgroundBrush(), MedievalShape(smallCorners), alpha = transparency)
                    ) {
                        MedievalBox(
                            background = StandardBackgroundBrush(),
                            modifier = Modifier
                                .fillMaxWidth(0.2F)
                                .aspectRatio(1F)
                                .align(Alignment.TopStart),
                        ) {
                            MedievalText(
                                text = recruit.level.toString(),
                                fontSize = bigText,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .align(Alignment.Center)
                            )
                        }

                        MedievalText(
                            text = "${unit.id} - ${unit.name.replaceFirstChar { it.uppercaseChar() }}",
                            fontSize = normalText,
                            color = colorTextLighter,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(start = padding)
                                .fillMaxWidth(0.8F)
                                .align(Alignment.CenterEnd)
                        )
                    }

                    MedievalProgressbar(
                        value = unit.hp,
                        min = 0,
                        max = unit.maxHp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(16.dp)
                            .align(Alignment.BottomCenter),
                    )
                }
            }
        }

        if (unit.effects.isNotEmpty()) {
            EffectsInfo(
                effects = unit.effects,
                modifier = Modifier.padding(top = padding, end = padding)
            )
        } else {
            Box(modifier = Modifier.padding(top = padding, end = padding).size(iconSize))
        }
    }
}

@Composable
private fun EffectsInfo(
    effects: List<Effectable>,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(padding),
        modifier = modifier,
    ) {
        effects.forEach { effect ->
            EffectIcon(
                effect = effect,
                modifier = Modifier.size(iconSize),
            )
        }
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
private fun EffectIcon(
    effect: Effectable,
    modifier: Modifier = Modifier,
) {
    MedievalBox(
        modifier = modifier,
    ) {
        Image(
            bitmap = when (effect) {
                is Healing -> getImageBitmap("textures/effect/healing.png")
                is Poisoning -> getImageBitmap("textures/effect/poison.png")
                else -> emptyImageBitmap
            } ?: emptyImageBitmap,
            contentDescription = "effect icon",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth(),
        )

        MedievalText(
            effect.cyclesLeft.toString(),
            fontSize = hugeText,
            color = colorTextLight,
            modifier = Modifier.align(Alignment.Center)
        )

        MedievalText(
            effect.cyclesLeft.toString(),
            fontSize = hugeText,
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

@Composable
private fun Actions(
    actions: List<Action>,
    onAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
    ) {
        actions.forEach { action ->
            Row (
                horizontalArrangement = Arrangement.spacedBy(padding),
            ) {
                MedievalButton(
                    icon = action.image ?: emptyImageBitmap,
                    text = action.name,
                    onClick = {
                        action.action()
                        onAction()
                    },
                    modifier = Modifier.padding(start = padding, bottom = padding),
                )
            }
        }
    }
}

@Composable
private fun ActionIcons(
    actions: List<Action>,
    onAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
    ) {
        actions.forEach { action ->
            MedievalBox(
                modifier = Modifier
                    .padding(start = padding, bottom = padding)
                    .clickable {
                        action.action()
                        onAction()
                    },
            ) {
                Image(
                    bitmap = action.image ?: emptyImageBitmap,
                    contentDescription = "coin icon",
                    modifier = Modifier.size(iconSize),
                )
            }
        }
    }
}