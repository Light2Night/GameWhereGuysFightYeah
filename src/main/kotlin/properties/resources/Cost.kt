package properties.resources

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import properties.resources.serializers.CostSerializer

@Serializable(with = CostSerializer::class)
@SerialName("cost")
class Cost(
    coins: Int,
    crystals: Int,
    exp: Int,
): Resources {
    override var coins: Int by mutableStateOf(coins)
    override var crystals: Int by mutableStateOf(crystals)
    override var exp: Int by mutableStateOf(exp)
}
