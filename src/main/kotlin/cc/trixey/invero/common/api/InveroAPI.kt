package cc.trixey.invero.common.api

/**
 * Invero
 * cc.trixey.invero.common.api.InveroAPI
 *
 * @author Arasple
 * @since 2023/2/1 16:37
 */
interface InveroAPI {

    /**
     * Access to the MenuManager API
     */
    fun getMenuManager(): InveroMenuManager

    /**
     * Access to the DataManager API
     * Related to global context & player data
     */
    fun getDataManager(): InveroDataManager

    /**
     * Access to the JavaScript handler
     */
    fun getJavaScriptHandler(): InveroJavaScriptHandler

    /**
     * Access to the Kether handler
     */
    fun getKetherHandler(): InveroKetherHandler

    /**
     * Access to the Invero registry
     */
    fun getRegistry(): InveroRegistry

}