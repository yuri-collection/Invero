package cc.trixey.invero.common.util

import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.function.info
import taboolib.common5.format

/**
 * Invero
 * cc.trixey.invero.common.util.PasteInitializer
 *
 * @author Modified based on work by Arasple
 * @since 2025/05/30
 */
object PasteInitializer {

    @Awake(LifeCycle.ENABLE)
    fun init() {
        // 加载配置
        PasteConfig.loadConfig()
        val serviceType = PasteConfig.getServiceType()
        info("paste-config-loaded".format(serviceType))
    }
}
