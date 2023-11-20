package properties.resources

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import user

class UserResources(
    coins: Int,
    crystals: Int,
    exp: Int,
): Resources {
    override var coins: Int by mutableStateOf(coins)
    override var crystals: Int by mutableStateOf(crystals)
    private var _exp: Int by mutableStateOf(exp)
    override var exp: Int get() = _exp
        set(value) {
            _exp = value
            user.level = calculateLevel(_exp)
            user.requiredExp = calculateRequiredExp(user.level)
        }

    override fun plus(other: Resources) {
        super.plus(other)
        user.level = calculateLevel(user.resources.exp)
        user.requiredExp = calculateRequiredExp(user.level)
    }

    override fun minus(other: Resources) {
        super.minus(other)
        user.level = calculateLevel(user.resources.exp)
        user.requiredExp = calculateRequiredExp(user.level)
    }
}
