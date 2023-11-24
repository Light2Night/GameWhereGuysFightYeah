package properties.user.quest

import Game.Units.Characters.UnitTypes
import lang
import org.jetbrains.skiko.currentNanoTime
import properties.resources.Reward
import properties.user.recruit.RecruitFactory
import kotlin.random.Random

class QuestFactory {
    fun createRandomQuest(x: Int = 0, y: Int = 0): Quest? {
        return when (Random(currentNanoTime()).nextInt(0, 3)) {
            0 -> createKillQuest(x, y)
            1 -> createKillCharacterQuest(x, y)
            2 -> createKillNameQuest(x, y)
            else -> null
        }
    }

    private fun createKillQuest(x: Int = 0, y: Int = 0): KillQuest {
        val type = UnitTypes.values().random()
        val amount = Random(currentNanoTime()).nextInt(5, 20)
        val reward = Reward(
            Random(currentNanoTime()).nextInt(amount * 2, amount * 5),
            Random(currentNanoTime()).nextInt(0, amount / 2),
            Random(currentNanoTime()).nextInt(amount, (amount * 2.5).toInt()),
        )
        val questName = lang.kill_quest_name
            .replaceFirstChar { it.uppercaseChar() }
            .replace("<amount>", amount.toString())
            .replace("<name>", type.name)

        return KillQuest(
            0,
            x,
            y,
            questName,
            questName,
            "Test_Icon",
            1,
            reward,
            type,
            amount,
        )
    }

    private fun createKillCharacterQuest(x: Int = 0, y: Int = 0): KillCharacterQuest {
        val character = RecruitFactory().randomPreset
        val amount = Random(currentNanoTime()).nextInt(3, 12)
        val reward = Reward(
            Random(currentNanoTime()).nextInt(amount * 2, amount * 5),
            Random(currentNanoTime()).nextInt(0, amount / 2),
            Random(currentNanoTime()).nextInt(amount, (amount * 2.5).toInt()),
        )
        val questName = lang.kill_quest_name
            .replaceFirstChar { it.uppercaseChar() }
            .replace("<amount>", amount.toString())
            .replace("<name>", character.name)

        return KillCharacterQuest(
            0,
            x,
            y,
            questName,
            questName,
            "Test_Icon",
            1,
            reward,
            character.charID,
            amount,
        )
    }

    private fun createKillNameQuest(x: Int = 0, y: Int = 0): KillNameQuest {
        val name = RecruitFactory().randomName
        val amount = Random(currentNanoTime()).nextInt(2, 12)
        val reward = Reward(
            Random(currentNanoTime()).nextInt(amount * 2, amount * 5),
            Random(currentNanoTime()).nextInt(0, amount / 2),
            Random(currentNanoTime()).nextInt(amount, (amount * 2.5).toInt()),
        )
        val questName = lang.kill_quest_name
            .replaceFirstChar { it.uppercaseChar() }
            .replace("<amount>", amount.toString())
            .replace("<name>", name)

        return KillNameQuest(
            0,
            x,
            y,
            questName,
            questName,
            "Test_Icon",
            1,
            reward,
            name,
            amount,
        )
    }
}