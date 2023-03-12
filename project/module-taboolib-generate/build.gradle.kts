plugins {
    id("io.izzel.taboolib") version taboolibPluginVersion
}

taboolib {
    version = taboolibVersion
    usedTaboolibModules.forEach { install(it) }

    description {
        name = rootName

        contributors {
            name("Arasple")
        }

        dependencies {
            name("PlaceholderAPI").optional(true)
            name("NeigeItems").optional(true)
            name("Zaphkiel").optional(true)
            name("HeadDatabase").optional(true)
            name("Oraxen").optional(true)
            name("ItemsAdder").optional(true)
        }

        bukkitNodes = HashMap<String, Any>().apply {
            put("api-version", 1.13)
            put("built-date", currentISODate)
            put("built-by", systemUserName)
            put("built-os", systemOS)
            put("built-ip", systemIP)
        }

    }

    classifier = null
    options("skip-minimize", "keep-kotlin-module", "skip-taboolib-relocate")
}