package ui.screens.mainMenu

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import bigText
import biggerPadding
import colorBackground
import colorBorder
import colorText
import colorTextError
import colorTextLighter
import colorTextSecond
import imageHeight
import imageWidth
import lang
import normalText
import padding
import properties.resources.Cost
import properties.textures.Textures
import properties.user.recruit.*
import smallBorder
import smallCorners
import smallIconSize
import transparency
import transparencySecond
import ui.composable.AppearDisappearAnimation
import ui.composable.MedievalBox
import ui.composable.MedievalText
import ui.composable.shaders.MedievalShape
import ui.composable.shaders.StandardBackgroundBrush
import user

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RecruitCard(
    recruit: Recruit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        Box(
            modifier = Modifier
                .clickable(onClick = onClick)
                .fillMaxWidth()
                .aspectRatio(imageWidth / imageHeight)
                .padding(padding)
                .background(StandardBackgroundBrush(), MedievalShape(smallCorners))
                .border(smallBorder, colorBorder, MedievalShape(smallCorners))
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
                    bitmap = recruit.image,
                    contentDescription = recruit.name,
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
                            text = recruit.name.replaceFirstChar { it.uppercaseChar() },
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
                            data = recruit.absoluteData,
                            modifier = Modifier
                                .background(StandardBackgroundBrush(), alpha = transparency)
                                .padding(start = padding, end = padding, top = biggerPadding, bottom = biggerPadding)
                                .fillMaxWidth(),
                        )
                    }

                    Stars(
                        amount = recruit.stars,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.verticalGradient(listOf(
                                    Color.Transparent,
                                    colorBackground.copy(alpha = transparencySecond),
                                    colorBackground,
                                ))),
                    )
                }
            }
        }

        recruit.cost?.let {
            Cost(
                cost = it,
                modifier = Modifier
            )
        }
    }
}

@Composable
private fun UnitTextData(
    data: RecruitData,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        val hpText = lang.hp
            .replace("<amount>", data.maxHP.toString())
            .replaceFirstChar { it.uppercaseChar() }
        MedievalText(hpText)

        when (data) {
            is BarbarianData -> {
                val damageText = lang.damage
                    .replace("<amount>", "${data.damage - data.damageDelta}-${data.damage + data.damageDelta}")
                    .replaceFirstChar { it.uppercaseChar() }
                MedievalText(damageText)
            }

            is MageData -> {
                val damageText = lang.damage
                    .replace("<amount>", "${data.damage - data.damageDelta}-${data.damage + data.damageDelta}")
                    .replaceFirstChar { it.uppercaseChar() }
                MedievalText(damageText)

                val effectText = lang.poison
                    .replace("<amount>", data.magicalEffectDamage.toString())
                    .replace("<turns>", data.magicalEffectTurns.toString())
                    .replaceFirstChar { it.uppercaseChar() }
                MedievalText(effectText)
            }

            is HealerData -> {
                val healText = lang.heal
                    .replace("<amount>", data.heal.toString())
                    .replaceFirstChar { it.uppercaseChar() }
                MedievalText(healText)

                val effectText = lang.healing
                    .replace("<amount>", data.healingEffectHeal.toString())
                    .replace("<turns>", data.healingEffectTurns.toString())
                    .replaceFirstChar { it.uppercaseChar() }
                MedievalText(effectText)
            }
        }
    }
}

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
                    bitmap = Textures["assets/coin.png"],
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
                    bitmap = Textures["assets/crystal.png"],
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