package properties.resources

import kotlin.math.pow
import kotlin.math.sqrt

fun calculateLevel(exp: Int) = sqrt(exp / 100.0).plus(1).toInt()

fun calculateRequiredExp(level: Int) = ((level).toDouble().pow(2) * 100.0).toInt()