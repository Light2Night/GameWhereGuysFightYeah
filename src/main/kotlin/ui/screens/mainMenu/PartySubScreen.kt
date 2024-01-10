package ui.screens.mainMenu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import properties.textures.Textures
import properties.user.recruit.Recruit
import ui.composable.Divider
import ui.composable.Grid
import ui.composable.shaders.StandardBackgroundBrush
import ui.composable.shaders.background
import user

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
    Grid(
        user.recruits.selectedList,
        columns = 2,
        modifier = modifier.verticalScroll(rememberScrollState()),
    ) {
        val recruit = it as Recruit
        RecruitCard(
            recruit = recruit,
            onClick = {
                user.recruits.deselectRecruit(recruit)
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun RecruitedList(
    modifier: Modifier = Modifier,
) {
    Grid(
        user.recruits.list,
        columns = 4,
        modifier = modifier.verticalScroll(rememberScrollState()),
    ) {
        val recruit = it as Recruit
        RecruitCard(
            recruit = recruit,
            onClick = {
                user.recruits.selectRecruit(recruit)
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}