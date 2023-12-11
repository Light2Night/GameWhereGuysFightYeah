package ui.screens

import Game.Units.Characters.Barbarian
import Game.Units.Characters.GameUnit
import Game.Units.Characters.Healer
import Game.Units.Characters.Magician
import Game.Effects.Effectable
import Game.Effects.Healing
import Game.Effects.Poisoning
import Game.Events.Arguments.Actions.ActionInfo
import Game.Actions
import Game.Game
import Game.PlayerTypes
import gamedata.GameData
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import bigText
import biggerPadding
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
import kotlinx.coroutines.launch
import lang
import longAnimationDuration
import normalAnimationDuration
import normalText
import org.jetbrains.skiko.currentNanoTime
import padding
import properties.user.recruit.Recruit
import smallBorder
import smallCorners
import smallText
import transparency
import transparencyLight
import transparencySecond
import ui.action.Action
import ui.action.ActionFabric
import ui.Side
import ui.composable.*
import ui.composable.shaders.MedievalShape
import ui.composable.shaders.StandardBackgroundBrush
import ui.screens.gameScreen.BeamArea
import kotlin.random.Random
import kotlin.random.nextInt

@Composable
fun GameScreen(
    game: Game,
    gameData: GameData,
    onEnd: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val coroutineScope = rememberCoroutineScope()

    var actions by remember { mutableStateOf(ActionFabric(game, gameData).createActions()) }
    var executedAction by remember { mutableStateOf<ActionInfo?>(null) }
    val positions = remember { mutableStateMapOf<Int, Offset>() }

    LaunchedEffect(gameData.currentUnit) {
        actions = ActionFabric(game, gameData).createActions()

        if (gameData.currentUnit?.team?.playerType == PlayerTypes.AI) {
            executedAction = gameData.currentUnit?.moveAI()
            delay(normalAnimationDuration.toLong() * 2 + 1000L)
            executedAction = null

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
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxSize(),
        )

        UnitList(
            gameData = gameData,
            units = gameData.enemies,
            positions = positions,
            selectedUnitID = gameData.selectedUnit?.id ?: 0,
            currentUnitID = gameData.currentUnit?.id ?: 0,
            side = Side.Down,
            onSelect = { game.setSelectedUnitIndex(it) },
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
            positions = positions,
            selectedUnitID = gameData.selectedUnit?.id ?: 0,
            currentUnitID = gameData.currentUnit?.id ?: 0,
            side = Side.Up,
            onSelect = { game.setSelectedUnitIndex(it) },
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
            if (unit != null && executedAction == null) {
                Actions(
                    actions = actions,
                    onAction = { action ->
                        coroutineScope.launch {
                            executedAction = action.action()
                            delay(normalAnimationDuration.toLong() * 2 + 1000L)
                            executedAction = null

                            game.next()
                            gameData.gameResult?.let { onEnd() }

                            if (game.getUnitById(gameData.selectedUnit?.id ?: 0) == null) {
                                game.setSelectedUnitIndex(
                                    gameData.enemies.lastOrNull()?.id ?: gameData.allies.firstOrNull()?.id ?: 0
                                )
                            }
                        }
                    },
                )
            }
        }

        executedAction?.let { action ->
            when(action.action) {
                Actions.Attack -> {
                    if (action.actor is Magician) {
                        BeamArea(
                            color = Color.White,
                            particleColor = Color.LightGray,
                            startPoint = positions[action.actor.id] ?: Offset(0F, 0F),
                            endPoint = positions[action.target.id] ?: Offset(0F, 0F),
                            duration = longAnimationDuration,
                            modifier = Modifier.fillMaxWidth(),
                        )
                    } else {
                        MedievalText("NO!", fontSize = hugeText)
                    }
                }
                else -> MedievalText("NO!", fontSize = hugeText)
            }
        }
    }
}

@Composable
private fun SmokeArea(
    amount: Int = 10,
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
    units: List<GameUnit>,
    positions: SnapshotStateMap<Int, Offset>,
    side: Side,
    onSelect: (Int) -> Unit,
    modifier: Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(padding),
        modifier = modifier,
    ) {
        units.forEach { unit ->
            UnitCard(
                unit = unit,
                recruit = gameData.getRecruitByID(unit.id) ?: return@forEach,
                side = side,
                isCurrent = unit.id == currentUnitID,
                isSelected = unit.id == selectedUnitID,
                onSelect = { onSelect(unit.id) },
                onPositionChanged = { pos ->
                    positions[unit.id] = pos
                },
                modifier = Modifier.fillMaxHeight(),
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun UnitCard(
    unit: GameUnit,
    recruit: Recruit,
    side: Side,
    isCurrent: Boolean,
    isSelected: Boolean,
    onSelect: () -> Unit,
    onPositionChanged: (Offset) -> Unit,
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current.density

    val animatedOffset by animateDpAsState(
        targetValue = if (isCurrent) if (side == Side.Down) (iconSize + padding) else -(iconSize + padding) else 0.dp,
        animationSpec = tween(
            durationMillis = normalAnimationDuration,
        )
    )

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
        Box(
            modifier = Modifier
                .offset(0.dp, animatedOffset)
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
                .clip(MedievalShape(smallCorners))
                .onGloballyPositioned {
                    onPositionChanged(it.positionInRoot().copy(
                        y = (it.positionInRoot().y + (it.size.height / 2)) * density,
                        x = (it.positionInRoot().x + (it.size.width / 2)) * density,
                    ))
                },
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

                var showInfo by remember { mutableStateOf(false) }
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxHeight()
                        .onPointerEvent(PointerEventType.Enter) {
                            showInfo = true
                        }
                        .onPointerEvent(PointerEventType.Exit) {
                            showInfo = false
                        },
                ) {
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

                    AppearDisappearAnimation(
                        showInfo,
                    ) {
                        UnitTextData(
                            unit = unit,
                            modifier = Modifier
                                .background(StandardBackgroundBrush(), alpha = transparency)
                                .padding(start = padding, end = padding, top = biggerPadding, bottom = biggerPadding)
                                .fillMaxWidth(),
                        )
                    }

                    Box(
                        contentAlignment = Alignment.Center,
                    ) {
                        MedievalProgressbar(
                            value = unit.hp,
                            min = 0,
                            max = unit.maxHp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(16.dp),
                        )

                        MedievalText(
                            text = "${unit.hp}/${unit.maxHp}",
                            fontSize = smallText,
                            color = colorTextLight,
                        )
                    }
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
private fun UnitTextData(
    unit: GameUnit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        when (unit) {
            is Barbarian -> {
                val damageText = lang.damage
                    .replace("<amount>", "${unit.damage - unit.damageDelta}-${unit.damage}")
                    .replaceFirstChar { it.uppercaseChar() }
                MedievalText(damageText)
            }

            is Magician -> {
                val damageText = lang.damage
                    .replace("<amount>", "${unit.damage - unit.damageDelta}-${unit.damage}")
                    .replaceFirstChar { it.uppercaseChar() }
                MedievalText(damageText)

                val effect = unit.magicalEffect as Poisoning
                val effectText = lang.poison
                    .replace("<amount>", effect.damage.toString())
                    .replace("<turns>", effect.durationInCycles.toString())
                    .replaceFirstChar { it.uppercaseChar() }
                MedievalText(effectText)
            }

            is Healer -> {
                val healText = lang.heal
                    .replace("<amount>", unit.heal.toString())
                    .replaceFirstChar { it.uppercaseChar() }
                MedievalText(healText)

                val effect = unit.healingEffect as Healing
                val effectText = lang.poison
                    .replace("<amount>", effect.heal.toString())
                    .replace("<turns>", effect.durationInCycles.toString())
                    .replaceFirstChar { it.uppercaseChar() }
                MedievalText(effectText)
            }
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
    onAction: (Action) -> Unit,
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
                    onClick = { onAction(action) },
                    modifier = Modifier.padding(start = padding, bottom = padding),
                )
            }
        }
    }
}