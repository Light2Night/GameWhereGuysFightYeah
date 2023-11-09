interface HasID {
    val id: Int
}

fun Collection<HasID>.uniqueId(): Int {
    var newID = 0
    forEach {
        if (it.id != newID) {
            return newID
        }

        newID++
    }

    return newID
}