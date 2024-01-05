package properties.resources

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import properties.resources.serializers.UserResourcesSerializer
import user

@Serializable(with = UserResourcesSerializer::class)
@SerialName("userResources")
class UserResources(
    coins: Int,
    crystals: Int,
    exp: Int,
    updateLevels: Boolean = true,
) : Resources {
    override var coins: Int by mutableStateOf(coins)
    override var crystals: Int by mutableStateOf(crystals)
    private var _exp: Int by mutableStateOf(exp)
    override var exp: Int get() = _exp
        set(value) {
            _exp = value
            updateLevels()
        }

    init {
        if (updateLevels) {
            updateLevels()
        }
    }

    override fun plus(other: Resources) {
        super.plus(other)
        updateLevels()
    }

    override fun minus(other: Resources) {
        super.minus(other)
        updateLevels()
    }

    private fun updateLevels() {
        user.level = calculateLevel(exp)
        user.requiredExp = calculateRequiredExp(user.level)
    }
}
