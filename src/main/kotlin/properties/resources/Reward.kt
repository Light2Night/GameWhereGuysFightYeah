package properties.resources

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class Reward(
    coins: Int,
    crystals: Int,
    exp: Int,
): Resources {
    override var coins: Int by mutableStateOf(coins)
    override var crystals: Int by mutableStateOf(crystals)
    override var exp: Int by mutableStateOf(exp)
}