package ui

import Game.Units.Characters.Barbarian
import Game.Units.Characters.GameUnit
import Game.Units.Characters.Healer
import Game.Units.Characters.Magician
import Game.Effects.Effectable
import Game.Effects.Healling
import Game.Effects.Poisoning
import Game.Game
import GameData
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import bigText
import border
import colorBackground
import colorBorder
import colorSelectedBorder
import colorTextLight
import emptyImageBitmap
import getImageBitmap
import hugeText
import iconSize
import imageHeight
import imageWidth
import lang
import normalAnimationDuration
import org.jetbrains.skiko.currentNanoTime
import padding
import smallBorder
import smallCorners
import transparencyLight
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
    Box(
        modifier = modifier
    ) {
        SmokeArea(
            amount = 10,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxSize(),
        )

        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            UnitList(
                units = gameData.allies,
                selectedUnitID = gameData.selectedUnit?.id ?: 0,
                currentUnitId = gameData.currentUnit?.id ?: 0,
                side = Side.Left,
                onSelect = { game.selectedUnitIndex = it },
                modifier = Modifier.weight(1F),
            )

            Divider(modifier = Modifier.fillMaxHeight())

            GameBoard(
                game = game,
                gameData = gameData,
                onAction = {
                    game.next()
                    gameData.gameResult.value?.let { onEnd() }

                    if (game.getUnitById(gameData.selectedUnit?.id ?: 0) == null) {
                        game.selectedUnitIndex =
                            gameData.enemies.lastOrNull()?.id ?: gameData.allies.firstOrNull()?.id ?: 0
                    }
                },
                modifier = Modifier.weight(1F),
            )

            Divider(modifier = Modifier.fillMaxHeight())

            UnitList(
                units = gameData.enemies,
                selectedUnitID = gameData.selectedUnit?.id ?: 0,
                currentUnitId = gameData.currentUnit?.id ?: 0,
                side = Side.Right,
                onSelect = { game.selectedUnitIndex = it },
                modifier = Modifier.weight(1F),
            )
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
        val direction = Random(index).nextBoolean()
        val initial = if (direction) 0F else 1F
        val target = if (direction) 1F else 0F
        val duration = Random(index).nextInt(30000..60000)

        val x = animations[index].animateFloat(
            initialValue = initial,
            targetValue = target,
            animationSpec = infiniteRepeatable(
                animation = tween(duration, easing = LinearEasing),
                initialStartOffset = StartOffset(
                    offsetMillis = Random(index).nextInt(0..60000),
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
    selectedUnitID: Int,
    currentUnitId: Int,
    units: List<GameUnit>,
    side: Side,
    onSelect: (Int) -> Unit,
    modifier: Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.verticalScroll(rememberScrollState()),
    ) {
        units.forEach { unit ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(imageHeight),
            ) {
                SlideAppearAnimation(
                    visible = unit.id != currentUnitId,
                    orientation = Orientation.Horizontal,
                    side = side,
                    duration = normalAnimationDuration,
                    delayIn = normalAnimationDuration,
                ) {
                    UnitInfo(
                        unit = unit,
                        isSelected = unit.id == selectedUnitID,
                        side = side,
                        onSelect = { onSelect(unit.id) },
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
    }
}

@Composable
private fun UnitInfo(
    unit: GameUnit,
    isSelected: Boolean,
    side: Side = Side.Left,
    onSelect: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = if (side == Side.Right) Arrangement.End else Arrangement.Start,
        modifier = modifier
            .clickable { onSelect() }
            .padding(padding)
            .background(StandardBackgroundBrush(), MedievalShape(smallCorners))
            .border(
                if (isSelected) border else smallBorder,
                if (isSelected) colorSelectedBorder else colorBorder,
                MedievalShape(smallCorners)
            )
            .clip(MedievalShape(smallCorners)),
    ) {
        if (side == Side.Left) {
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
                    .height(imageHeight)
                    .width(imageWidth)
                    .padding(padding)
                    .background(colorBackground, MedievalShape(smallCorners))
                    .border(smallBorder, colorBorder, MedievalShape(smallCorners))
                    .clip(MedievalShape(smallCorners)),
            )
        }

        Column(
            horizontalAlignment = if (side == Side.Right) Alignment.End else Alignment.Start,
            modifier = Modifier
        ) {
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

            UnitTextData(
                unit = unit,
                alignment = if (side == Side.Right) Alignment.End else Alignment.Start,
                modifier = Modifier,
            )

            EffectsInfo(
                effects = unit.effects,
                modifier = Modifier,
            )
        }

        if (side == Side.Right) {
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
                    .height(imageHeight)
                    .width(imageWidth)
                    .padding(padding)
                    .background(colorBackground, MedievalShape(smallCorners))
                    .border(smallBorder, colorBorder, MedievalShape(smallCorners))
                    .clip(MedievalShape(smallCorners)),
            )
        }
    }
}

@Composable
private fun UnitTextData(
    unit: GameUnit,
    alignment: Alignment.Horizontal = Alignment.Start,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = alignment,
        modifier = modifier,
    ) {
        MedievalText("HP: ${unit.hp}/${unit.maxHp}")

        when (unit) {
            is Barbarian -> {
                MedievalText("DMG: ${unit.damage - unit.damageDelta}-${unit.damage}")
            }

            is Magician -> {
                MedievalText("DMG: ${unit.damage - unit.damageDelta}-${unit.damage}")

                val effect = unit.magicalEffect as Poisoning
                MedievalText("EFF: ${effect.damage} (${effect.durationInCycles} turns)")
            }

            is Healer -> {
                MedievalText("HEAL: ${unit.heal}")

                val effect = unit.healingEffect as Healling
                MedievalText("EFF: ${effect.heal} (${effect.durationInCycles} turns)")
            }
        }
    }
}

@Composable
private fun EffectsInfo(
    effects: List<Effectable>,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(padding),
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
        Box(
            modifier = Modifier
        ) {
            Image(
                bitmap = when (effect) {
                    is Healling -> getImageBitmap("textures/effect/healing.png")
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
}

@Composable
private fun GameBoard(
    game: Game,
    gameData: GameData,
    onAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        SlideTransition(
            items = gameData.allies.plus(gameData.enemies),
            currentIndex = gameData.allies.plus(gameData.enemies).indexOf(gameData.currentUnit),
            orientation = Orientation.Vertical,
            side = Side.Down,
            duration = normalAnimationDuration,
            delayIn = normalAnimationDuration,
        ) { index, unit ->
            UnitInfo(
                unit = unit,
                isSelected = unit.id == gameData.selectedUnit?.id,
                onSelect = { game.selectedUnitIndex = unit.id },
                modifier = Modifier.fillMaxWidth(),
            )
        }

        Crossfade(
            gameData.currentUnit,
            animationSpec = tween(normalAnimationDuration),
        ) { unit ->
            if (unit != null) {
                Actions(
                    actions = ActionFabric(game, gameData).createActions(),
                    selectedUnitID = unit.id,
                    onAction = onAction,
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun Actions(
    actions: List<Action>,
    selectedUnitID: Int,
    onAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    FlowRow {
        actions.forEach { action ->
            MedievalButton(
                text = action.name,
                onClick = {
                    action.action(selectedUnitID)
                    onAction()
                },
                modifier = modifier.padding(start = padding, top = padding),
            )
        }
    }
}