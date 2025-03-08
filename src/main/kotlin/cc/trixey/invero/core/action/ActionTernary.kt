package cc.trixey.invero.core.action

/**
 * Invero
 * cc.trixey.invero.core.action.ActionTernary
 *
 * @author Arasple
 * @since 2023/1/26 22:36
 */
interface ActionTernary {

    val conditions: List<ScriptKether>

    val then: Action?

    val `else`: Action?

}