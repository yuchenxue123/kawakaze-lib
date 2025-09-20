package cute.neko.kawakaze.prepare.types

import cute.neko.kawakaze.config.ConfigRegistrar
import cute.neko.kawakaze.prepare.Preparable

abstract class ConfigPreparable : Preparable {
    override fun prepare() {
        onConfigRegister(ConfigRegistrar())
    }

    abstract fun onConfigRegister(registrar: ConfigRegistrar)
}