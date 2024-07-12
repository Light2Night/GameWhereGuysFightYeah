package ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import bigIconSize
import bigText
import biggerPadding
import colorText
import corners
import emptyImageBitmap
import hugeText
import padding
import properties.textures.Textures
import properties.user.recruit.RecruitFactory
import ui.composable.MedievalBox
import ui.composable.MedievalText
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
        modifier = modifier,
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            drawImage(
                image = Textures.getOrNull(cutscene.background) ?: Textures["background/forest1.png"],
                dstOffset = IntOffset.Zero,
                dstSize = IntSize(size.width.toInt(), size.height.toInt()),
            )

            val leftCharacter = recruits.find { it?.charID == cutscene.state.leftSlot }
            val leftImageName = leftCharacter?.expressions?.getByNameOrNull(cutscene.state.leftExpression)?.image
            val leftImage = leftImageName?.let { imgName -> Textures[imgName] } ?: emptyImageBitmap
            val scale = (size.height * 0.8) / leftImage.height
            drawImage(
                image = leftImage,
                dstOffset = IntOffset(((size.width * 0.2) - (leftImage.width * scale / 2)).toInt(), (size.height * 0.2).toInt()),
                dstSize = IntSize((leftImage.width * scale).toInt(), (size.height * 0.8).toInt()),
            )

            val centralCharacter = recruits.find { it?.charID == cutscene.state.centralSlot }
            val centralImageName = centralCharacter?.expressions?.getByNameOrNull(cutscene.state.centralExpression)?.image
            val centralImage = centralImageName?.let { imgName -> Textures[imgName] } ?: emptyImageBitmap
            val scale2 = (size.height * 0.8) / centralImage.height
            drawImage(
                image = centralImage,
                dstOffset = IntOffset(((size.width * 0.5) - (leftImage.width * scale / 2)).toInt(), (size.height * 0.2).toInt()),
                dstSize = IntSize((centralImage.width * scale2).toInt(), (size.height * 0.8).toInt()),
            )

            val rightCharacter = recruits.find { it?.charID == cutscene.state.rightSlot }
            val rightImageName = rightCharacter?.expressions?.getByNameOrNull(cutscene.state.rightExpression)?.image
            val rightImage = rightImageName?.let { imgName -> Textures[imgName] } ?: emptyImageBitmap
            val scale3 = (size.height * 0.8) / rightImage.height
            drawImage(
                image = rightImage,
                dstOffset = IntOffset(((size.width * 0.8) - (leftImage.width * scale / 2)).toInt(), (size.height * 0.2).toInt()),
                dstSize = IntSize((rightImage.width * scale3).toInt(), (size.height * 0.8).toInt()),
            )
        }

        DialogWindow(
            name = recruits.find { cutscene.state.speakingChar == it?.charID }?.name ?: "",
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
        MedievalBox(
            contentAlignment = Alignment.TopStart,
            modifier = Modifier
                .fillMaxSize()
                .clickable(onClick = onNext),
        ) {
            MedievalText(
                text = text,
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
            MedievalText(name, fontSize = hugeText)
        }

        Icon(
            Icons.Default.ArrowBack,
            contentDescription = "back",
            tint = colorText,
            modifier = Modifier
                .size(bigIconSize)
                .align(Alignment.BottomEnd)
                .padding(padding)
                .clickable(onClick = onBack),
        )
    }
}