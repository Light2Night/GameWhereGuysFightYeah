package properties.resources

import kotlin.math.sqrt

fun calculateLevel(exp: Int) = sqrt(exp / 100.0).unaryPlus().toInt()