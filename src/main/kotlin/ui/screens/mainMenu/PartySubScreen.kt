package ui.screens.mainMenu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import properties.textures.Textures
import ui.composable.Divider
import ui.composable.shaders.StandardBackgroundBrush
import ui.composable.shaders.background
import user
import kotlin.math.ceil

@Composable
fun PartySubScreen(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .background(Textures["background/party1.png"])
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
            RecruitCard(
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
                        RecruitCard(
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