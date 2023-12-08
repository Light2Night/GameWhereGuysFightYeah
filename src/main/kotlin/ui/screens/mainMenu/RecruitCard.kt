package ui.screens.mainMenu

import Game.Units.Characters.UnitTypes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import bigText
import colorBackground
import colorBorder
import colorText
import colorTextError
import colorTextSecond
import emptyImageBitmap
import getImageBitmap
import imageHeight
import imageWidth
import lang
import padding
import properties.resources.Cost
import properties.user.recruit.*
import smallBorder
import smallCorners
import smallIconSize
import transparencySecond
import ui.composable.MedievalText
import ui.composable.shaders.MedievalShape
import ui.composable.shaders.StandardBackgroundBrush
import user

@Composable
fun RecruitCard(
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
                    .background(
                        Brush.verticalGradient(listOf(
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