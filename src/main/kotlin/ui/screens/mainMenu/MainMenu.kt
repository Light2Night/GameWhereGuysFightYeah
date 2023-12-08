package ui.screens.mainMenu

import Game.Units.Characters.UnitTypes
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.unit.dp
import bigText
import biggerPadding
import colorBackgroundDarker
import colorBorder
import colorTextError
import colorTextLight
import colorTextLighter
import colorTextSecond
import emptyImageBitmap
import getImageBitmap
import iconSize
import lang
import longAnimationDuration
import padding
import properties.user.chest.Chest
import resourceWidth
import smallBorder
import smallIconSize
import smallText
import transparencySecond
import ui.composable.*
import ui.composable.shaders.*
import properties.user.worldMap.Location
import ui.CheckManager
import ui.CheckValue
import ui.MainMenuTab
import user

@Composable
fun MainMenu(
    onStart: (List<UnitTypes>, Location) -> Unit,
    onChestOpen: (Chest) -> Unit,
    modifier: Modifier = Modifier,
) {
    val tabs by remember {
        mutableStateOf(
            CheckManager(
                CheckValue(MainMenuTab.Guild, true),
                CheckValue(MainMenuTab.Party, false),
                CheckValue(MainMenuTab.World, false),
                CheckValue(MainMenuTab.Settings, false),
            )
        )
    }

    Column(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(StandardBackgroundBrush())
        ) {
            Tabs(
                tabs = tabs,
                modifier = Modifier.fillMaxWidth(),
            )

            Divider(
                orientation = Orientation.Horizontal,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Crossfade(
            targetState = tabs.getChecked(),
            animationSpec = tween(longAnimationDuration),
            modifier = Modifier.fillMaxSize(),
        ) { checked ->
            when (checked) {
                MainMenuTab.Guild -> GuildSubScreen(
                    onChestOpen = onChestOpen,
                    modifier = Modifier.fillMaxSize(),
                )

                MainMenuTab.Party -> PartySubScreen(
                    modifier = Modifier.fillMaxSize(),
                )

                MainMenuTab.World -> WorldSubScreen(
                    onStart = onStart,
                    modifier = Modifier.fillMaxSize(),
                )

                MainMenuTab.Settings -> SettingsSubScreen(
                    modifier = Modifier.fillMaxSize(),
                )

                null -> Text("<ERROR>")
            }
        }
    }

    DebugOptions()
}

@Composable
private fun DebugOptions() {
    Column(
        modifier = Modifier
            .background(Color.Black.copy(0.7F))
    ) {
        var isDebugOpen by remember { mutableStateOf(false) }

        MedievalText(
            text = "debug options",
            color = colorTextError,
            modifier = Modifier.clickable { isDebugOpen = !isDebugOpen },
        )

        if (isDebugOpen) {
            MedievalText(
                text = "add test recruit ${user.recruits.selectedList.size}/5",
                color = colorTextError,
                modifier = Modifier
                    .clickable {
                        val recruit = user.recruits.createNewRecruitToGuild()
                        user.resources.coins += recruit.cost?.coins ?: 0
                        user.resources.crystals += recruit.cost?.crystals ?: 0
                        user.recruits.buyRecruit(user.recruits.guildList.last(), replace = false)
                    },
            )

            MedievalText(
                text = "complete quest",
                color = colorTextError,
                modifier = Modifier
                    .clickable {
                        user.quests.list.random().forceComplete()
                        user.quests.checkAllQuests()
                    },
            )

            MedievalText(
                text = "+ 100 exp",
                color = colorTextError,
                modifier = Modifier.clickable { user.resources.exp += 100 }
            )

            MedievalText(
                text = "- 100 exp",
                color = colorTextError,
                modifier = Modifier.clickable { user.resources.exp -= 100 }
            )

            MedievalText(
                text = "+ 100 coins",
                color = colorTextError,
                modifier = Modifier.clickable { user.resources.coins += 100 }
            )

            MedievalText(
                text = "- 100 coins",
                color = colorTextError,
                modifier = Modifier.clickable { user.resources.coins -= 100 }
            )

            MedievalText(
                text = "+ 100 crystals",
                color = colorTextError,
                modifier = Modifier.clickable { user.resources.crystals += 100 }
            )

            MedievalText(
                text = "- 100 crystals",
                color = colorTextError,
                modifier = Modifier.clickable { user.resources.crystals -= 100 }
            )
        }
    }
}

@Composable
private fun Tabs(
    tabs: CheckManager<MainMenuTab>,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            tabs.getKeys().forEach { tab ->
                Tab(
                    tab = tab,
                    selected = tabs.getChecked() == tab,
                    onClick = { tabs.check(tab) },
                    modifier = Modifier.height(iconSize)
                )
            }

            UserResourcesInfo(modifier = Modifier.padding(start = biggerPadding))
        }

        UserInfo()
    }
}

@Composable
private fun Tab(
    tab: MainMenuTab,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clickable(onClick = onClick, enabled = !selected)
            .background(ButtonBrush())
            .background(if (selected) colorBackgroundDarker.copy(alpha = transparencySecond) else Color.Transparent)
            .border(smallBorder, colorBorder),
    ) {
        Text(
            tab.toString(),
            color = colorTextLighter,
            fontSize = bigText,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(resource = "fonts/cambria.ttc")),
            modifier = Modifier.padding(biggerPadding),
        )
    }
}

@Composable
private fun UserResourcesInfo(
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(padding),
            modifier = Modifier.width(resourceWidth),
        ) {
            Image(
                bitmap = getImageBitmap("textures/assets/coin.png") ?: emptyImageBitmap,
                contentDescription = "coin icon",
                modifier = Modifier.size(smallIconSize),
            )

            MedievalText(
                user.resources.coins.toString(),
                fontSize = bigText,
                fontWeight = FontWeight.Bold,
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(padding),
            modifier = Modifier.width(resourceWidth),
        ) {
            Image(
                bitmap = getImageBitmap("textures/assets/crystal.png") ?: emptyImageBitmap,
                contentDescription = "crystal icon",
                modifier = Modifier.size(smallIconSize),
            )

            MedievalText(
                user.resources.crystals.toString(),
                fontSize = bigText,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
private fun UserInfo(
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Column(
            horizontalAlignment = Alignment.End,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(padding),
            ) {
                MedievalText(
                    "${lang.level_short.uppercase()}. ${user.level}",
                    color = colorTextSecond,
                )

                MedievalText(
                    user.name,
                    fontWeight = FontWeight.Bold,
                )
            }

            Box(
                contentAlignment = Alignment.Center,
            ) {
                MedievalProgressbar(
                    value = user.resources.exp,
                    min = 0,
                    max = user.requiredExp,
                    modifier = Modifier.size(256.dp, 16.dp),
                )

                MedievalText(
                    text = "${lang.exp_short.uppercase()}: ${user.resources.exp}/${user.requiredExp}",
                    fontSize = smallText,
                    color = colorTextLight,
                )
            }
        }

        MedievalIcon(
            icon = user.profileImage ?: emptyImageBitmap,
            modifier = Modifier
                .size(iconSize)
                .padding(padding)
        )
    }
}