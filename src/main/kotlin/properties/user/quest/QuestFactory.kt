package properties.user.quest

import Game.Units.Characters.UnitTypes
import org.jetbrains.skiko.currentNanoTime
import properties.resources.Reward
import properties.user.recruit.RecruitFactory
import utilities.uniqueId
import user
import kotlin.random.Random

class QuestFactory {
    fun createRandomQuest(x: Int = 0, y: Int = 0, id: Int? = null): Quest? {
        return when (Random(currentNanoTime()).nextInt(0, 3)) {
            0 -> createKillQuest(x, y, id)
            1 -> createKillCharacterQuest(x, y, id)
            2 -> createKillNameQuest(x, y, id)
            else -> null
        }
    }

    private fun createKillQuest(x: Int = 0, y: Int = 0, id: Int? = null): KillQuest {
        val type = UnitTypes.entries.toTypedArray().random()
        val amount = Random(currentNanoTime()).nextInt(5, 20)
        val reward = Reward(
            Random(currentNanoTime()).nextInt(amount * 2, amount * 5),
            Random(currentNanoTime()).nextInt(0, amount / 2),
            Random(currentNanoTime()).nextInt(amount, (amount * 2.5).toInt()),
        )

        return KillQuest(
            id ?: user.quests.list.uniqueId(),
            x,
            y,
            "",
            "",
            "Test_Icon",
            1,
            reward,
            amount.toDouble(),
            0.0,
            type,
        )
    }

    private fun createKillCharacterQuest(x: Int = 0, y: Int = 0, id: Int? = null): KillCharacterQuest {
        val character = RecruitFactory().randomPreset
        val amount = Random(currentNanoTime()).nextInt(3, 12)
        val reward = Reward(
            Random(currentNanoTime()).nextInt(amount * 2, amount * 5),
            Random(currentNanoTime()).nextInt(0, amount / 2),
            Random(currentNanoTime()).nextInt(amount, (amount * 2.5).toInt()),
        )

        return KillCharacterQuest(
            id ?: user.quests.list.uniqueId(),
            x,
            y,
            "",
            "",
            "Test_Icon",
            1,
            reward,
            amount.toDouble(),
            0.0,
            character.charID,
        )
    }

    private fun createKillNameQuest(x: Int = 0, y: Int = 0, id: Int? = null): KillNameQuest {
        val name = RecruitFactory().randomName
        val amount = Random(currentNanoTime()).nextInt(2, 12)
        val reward = Reward(
            Random(currentNanoTime()).nextInt(amount * 2, amount * 5),
            Random(currentNanoTime()).nextInt(0, amount / 2),
            Random(currentNanoTime()).nextInt(amount, (amount * 2.5).toInt()),
        )

        return KillNameQuest(
            id ?: user.quests.list.uniqueId(),
            x,
            y,
            "",
            "",
            "Test_Icon",
            1,
            reward,
            amount.toDouble(),
            0.0,
            name,
        )
    }
}