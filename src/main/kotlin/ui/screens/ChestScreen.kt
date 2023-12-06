package ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import properties.user.chest.Chest
import ui.ChestScreenState
import ui.composable.MedievalButton
import ui.composable.MedievalIcon
import ui.composable.MedievalText
import ui.composable.shaders.StandardBackgroundBrush

@Composable
fun ChestScreen(
    chest: Chest,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var screenState by remember { mutableStateOf(ChestScreenState.Start) }

    Box(
        modifier = modifier.background(StandardBackgroundBrush()),
    ) {
        when (screenState) {
            ChestScreenState.Start -> ChestStart(
                chest = chest,
                onStart = {
                    screenState = ChestScreenState.Open
                },
                modifier = Modifier
                    .fillMaxHeight(0.5F)
                    .align(Alignment.Center),
            )
            ChestScreenState.Open -> ChestOpen(
                chest = chest,
                onStop = {
                    screenState = ChestScreenState.Result
                },
                modifier = Modifier
                    .fillMaxHeight(0.5F)
                    .align(Alignment.Center),
            )
            ChestScreenState.Result -> ChestResult(
                chest = chest,
                onClose = onClose,
                modifier = Modifier
                    .fillMaxHeight(0.5F)
                    .align(Alignment.Center),
            )
        }
    }
}

@Composable
private fun ChestStart(
    chest: Chest,
    onStart: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        MedievalIcon(
            icon = chest.image,
            modifier = Modifier
                .weight(1F)
                .aspectRatio(1F),
        )
        MedievalText(
            text = chest.name,
            fontWeight = FontWeight.Bold,
        )
        MedievalButton(
            onClick = onStart,
            text = "Open",
        )
    }
}

@Composable
private fun ChestOpen(
    chest: Chest,
    onStop: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var openings by remember { mutableStateOf(0) }
    var testText by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        MedievalText(
            text = "$openings/${chest.sheet.amount}",
        )
        MedievalText(
            text = testText,
        )
        MedievalButton(
            onClick = {
                val entry = chest.sheet.getNextEntry()
                if (entry == null) {
                    onStop()
                    return@MedievalButton
                }

                testText = "${entry.type} - ${entry.absoluteValue}"
                openings++
            },
            text = "Next",
        )
    }
}

@Composable
private fun ChestResult(
    chest: Chest,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        chest.sheet.entries.forEach { entry ->
            MedievalText(
                text = "${entry.type} - ${entry.absoluteValue}",
            )
        }

        MedievalButton(
            onClick = {
                chest.sheet.giveReward()
                onClose()
            },
            text = "Close",
        )
    }
}