package com.utmaximur.workmanager.dsl

import com.utmaximur.workmanager.models.BackoffLorrainePolicy
import com.utmaximur.workmanager.models.OneTimeOperation
import com.utmaximur.workmanager.work.DataDefinition
import com.utmaximur.workmanager.work.Worker
import com.utmaximur.workmanager.work.WorkerData
import com.utmaximur.workmanager.work.workData
import kotlin.reflect.KClass
import kotlin.time.Duration

class OneTimeDefinition internal constructor() {
    private val tags: MutableSet<String> = mutableSetOf()

    private var commonConstraints: CommonConstraints = CommonConstraints.NONE
    private var inputData: WorkerData? = null
    private var backoffLorrainePolicy: BackoffLorrainePolicy? = null
    private var identifier: String? = null

    inline fun <reified T : Worker> addIdentifier() {
        addIdentifier(T::class)
    }

    fun addIdentifier(identifier: KClass<out Worker>) {
        this.identifier = identifier.simpleName
    }

    fun addTag(tag: String) {
        tags.add(tag)
    }

    fun backOffPolicy(duration: Duration, policy: BackoffLorrainePolicy.Policy) {
        backoffLorrainePolicy = BackoffLorrainePolicy(
            duration = duration,
            policy = policy
        )
    }

    fun data(block: DataDefinition.() -> Unit) {
        inputData = workData(block)
    }

    fun constraints(block: ConstraintsDefinition.() -> Unit) {
        val definition = ConstraintsDefinition().apply(block)

        commonConstraints = definition.build()
    }

    internal fun build(): OneTimeOperation {

        val identifier = requireNotNull(identifier) { "Identifier must not be null" }

        return OneTimeOperation(
            commonConstraints = commonConstraints,
            tags = tags,
            inputData = inputData,
            identifier = identifier,
            backOffPolicy = backoffLorrainePolicy
        )
    }

}

/**
 * Dsl method to create a [OneTimeOperation]
 */
fun oneTimeOperation(block: OneTimeDefinition.() -> Unit): OneTimeOperation {
    val definition = OneTimeDefinition()

    return definition.apply(block)
        .build()
}