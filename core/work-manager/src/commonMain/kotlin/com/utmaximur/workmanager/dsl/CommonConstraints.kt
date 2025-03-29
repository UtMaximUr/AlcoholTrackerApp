package com.utmaximur.workmanager.dsl

//TODO added more constraints
class CommonConstraints internal constructor(
    val requireNetwork: Boolean
) {

    companion object {
        val NONE = CommonConstraints(requireNetwork = false)

        val NETWORK = CommonConstraints(requireNetwork = true)
    }

    class Builder {

        private var requiredNetwork: Boolean = false
        fun setRequiredNetwork(requiredNetwork: Boolean): Builder {
            this.requiredNetwork = requiredNetwork
            return this
        }

        fun build() = CommonConstraints(
            requireNetwork = requiredNetwork
        )

    }
}

class ConstraintsDefinition internal constructor() {
    var requiredNetwork: Boolean = false

    fun build() = CommonConstraints.Builder()
        .setRequiredNetwork(requiredNetwork)
        .build()

}