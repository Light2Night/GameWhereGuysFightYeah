package ui.screens.mainMenu

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import border
import colorBorder
import corners
import hugePadding
import lang
import org.jetbrains.skiko.currentNanoTime
import padding
import properties.textures.Textures
import properties.user.chest.Chest
import properties.user.quest.Quest
import reallyHugePadding
import smallCorners
import ui.composable.*
import ui.composable.shaders.MedievalShape
import ui.composable.shaders.StandardBackgroundBrush
import ui.composable.shaders.background
import ui.composable.shaders.texture
import user
import kotlin.random.Random

@Composable
fun GuildSubScreen(
    onChestOpen: (Chest) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .background(Textures["background/guild1.png"])
    ) {
        Recruits(
            onChestOpen = onChestOpen,
            modifier = Modifier
                .fillMaxWidth(0.33F)
                .fillMaxHeight()
                .background(StandardBackgroundBrush())
        )

        Divider(modifier = Modifier.fillMaxHeight())

        QuestBoard(
            modifier = Modifier
                .fillMaxSize()
                .padding(reallyHugePadding)
        )
    }
}

@Composable
private fun Recruits(
    onChestOpen: (Chest) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        Column(modifier = Modifier.weight(1F)) {
            Chests(onChestOpen = onChestOpen)

            Divider(orientation = Orientation.Horizontal, modifier = Modifier.fillMaxWidth())

            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                user.recruits.guildList.forEach { recruit ->
                    RecruitCard(
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
private fun Chests(
    onChestOpen: (Chest) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        repeat(user.chests.maxAmount) { i ->
            user.chests.list[i]?.let { chest ->
                Chest(
                    chest = chest,
                    onOpen = {
                        user.chests.removeChest(chest)
                        onChestOpen(chest)
                    },
                    modifier = Modifier.weight(1F).padding(padding)
                )
            } ?: Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1F).padding(padding),
            ) {
                MedievalBox(modifier = Modifier.fillMaxWidth().aspectRatio(1F).padding(padding))
                MedievalText(
                    lang.empty_chest_slot.replaceFirstChar { it.uppercaseChar() },
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

@Composable
private fun Chest(
    chest: Chest,
    onOpen: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.clickable { onOpen() },
    ) {
        MedievalIcon(
            icon = chest.image,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1F),
        )

        MedievalText(
            text = chest.name,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
private fun QuestBoard(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .texture(Textures["background/3.png"], MedievalShape(corners))
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
                        QuestCard(
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
private fun QuestCard(
    request: Quest?,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        if (request != null) {
            MedievalBox(
                background = SolidColor(Color.Transparent),
                modifier = Modifier
                    .fillMaxSize()
                    .texture(Textures["background/4.png"], MedievalShape(smallCorners))
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