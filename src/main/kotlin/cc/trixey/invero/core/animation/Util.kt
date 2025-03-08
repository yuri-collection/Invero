package cc.trixey.invero.core.animation

/**
 * Invero
 * cc.trixey.invero.core.animation.TypeAliases
 *
 * @author Arasple
 * @since 2023/1/16 10:01
 */
inline fun <reified T> List<T>.toCyclic(cycleMode: CycleMode = CycleMode.LOOP): Cyclic<T> {
    return Cyclic(toTypedArray(), cycleMode)
}