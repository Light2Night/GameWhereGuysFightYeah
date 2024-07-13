package ui.screens

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import bigIconSize
import bigText
import biggerPadding
import colorText
import corners
import emptyImageBitmap
import hugeText
import kotlinx.coroutines.delay
import longAnimationDuration
import normalAnimationDuration
import padding
import properties.textures.Textures
import properties.user.recruit.RecruitFactory
import ui.composable.MedievalBox
import ui.composable.MedievalText
import ui.composable.shaders.background
import ui.screens.cutsceneScreen.Cutscene
import ui.screens.cutsceneScreen.getByNameOrNull

@Composable
fun CutsceneScreen(
    cutscene: Cutscene,
    onEnd: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val recruits by remember {
        val factory = RecruitFactory()
        mutableStateOf(cutscene.characterIDs.map { factory.getPreset(it) })
    }

    Box(
        modifier = modifier
            .background(Textures.getOrNull(cutscene.background) ?: Textures["background/forest1.png"]),
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight(0.8F)
                .fillMaxWidth(0.9F)
                .align(Alignment.BottomCenter),
        ) {
            val leftCharacter = recruits.find { it?.charID == cutscene.state.leftSlot }
            val leftImageName = leftCharacter?.expressions?.getByNameOrNull(cutscene.state.leftExpression)?.image
            val leftImage = leftImageName?.let { imgName -> Textures[imgName] } ?: emptyImageBitmap
            CharacterImage(
                image = leftImage,
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.BottomStart),
            )

            val centralCharacter = recruits.find { it?.charID == cutscene.state.centralSlot }
            val centralImageName =
                centralCharacter?.expressions?.getByNameOrNull(cutscene.state.centralExpression)?.image
            val centralImage = centralImageName?.let { imgName -> Textures[imgName] } ?: emptyImageBitmap
            CharacterImage(
                image = centralImage,
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.BottomCenter),
            )

            val rightCharacter = recruits.find { it?.charID == cutscene.state.rightSlot }
            val rightImageName = rightCharacter?.expressions?.getByNameOrNull(cutscene.state.rightExpression)?.image
            val rightImage = rightImageName?.let { imgName -> Textures[imgName] } ?: emptyImageBitmap
            CharacterImage(
                image = rightImage,
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.BottomEnd),
            )
        }

        DialogWindow(
            name = cutscene.state.speakingName ?: recruits.find { cutscene.state.speakingChar == it?.charID }?.name ?: "",
            text = cutscene.state.speakingText,
            onNext = {
                cutscene.next()
                if (cutscene.isFinished()) {
                    onEnd()
                }
            },
            onBack = {
                cutscene.back()
            },
            modifier = Modifier
                .fillMaxWidth(0.8F)
                .fillMaxHeight(0.2F)
                .align(Alignment.BottomCenter)
                .offset(0.dp, corners)
        )
    }
}

@Composable
private fun CharacterImage(
    image: ImageBitmap,
    modifier: Modifier = Modifier,
) {
    Crossfade(
        targetState = image,
        animationSpec = tween(longAnimationDuration),
        modifier = modifier,
    ) { img ->
        Image(
            img,
            contentDescription = null,
            alignment = Alignment.BottomStart,
            modifier = Modifier.fillMaxHeight(),
        )
    }
}

@Composable
private fun DialogWindow(
    name: String,
    text: String,
    onNext: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
    ) {
        var isRunning by remember { mutableStateOf(false) }
        var content by remember { mutableStateOf("") }

        LaunchedEffect(text) {
            if (isRunning) return@LaunchedEffect

            if (text.isBlank()) {
                content = ""
                return@LaunchedEffect
            }

            isRunning = true
            runTextAnimation(
                content = text,
                onNewContent = { content = it },
            )
            isRunning = false
        }

        MedievalBox(
            contentAlignment = Alignment.TopStart,
            modifier = Modifier
                .fillMaxSize()
                .clickable(onClick = {
                    isRunning = false
                    onNext()
                }),
        ) {
            MedievalText(
                text = content,
                fontSize = bigText,
                modifier = Modifier.padding(top = 24.dp, start = biggerPadding, end = padding, bottom = padding),
            )
        }

        MedievalBox(
            modifier = Modifier
                .width((256 + 128).dp)
                .height(48.dp)
                .offset(16.dp, (-24).dp),
        ) {
            Crossfade(
                targetState = name,
                animationSpec = tween(normalAnimationDuration),
            ) { nm ->
                MedievalText(nm, fontSize = hugeText)
            }
        }

        Icon(
            Icons.Default.ArrowBack,
            contentDescription = "back",
            tint = colorText,
            modifier = Modifier
                .size(bigIconSize)
                .align(Alignment.BottomEnd)
                .padding(padding)
                .clickable(onClick = {
                    isRunning = false
                    onBack()
                }),
        )
    }
}

private suspend fun runTextAnimation(
    content: String,
    onNewContent: (String) -> Unit,
    interval: Long = 30,
) {
    var newContent = ""

    content.forEach {
        newContent += it
        onNewContent(newContent)
        delay(interval)
    }
}