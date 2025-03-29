package com.utmaximur.workmanager.dsl

import com.utmaximur.workmanager.models.ExistingWorkerPolicy
import com.utmaximur.workmanager.models.MultipleOperation
import com.utmaximur.workmanager.models.OneTimeOperation

class MultipleOperationDefinition internal constructor() {

    private val operations: MutableList<MultipleOperation.Operation> = mutableListOf()
    private var commonConstraints: CommonConstraints? = null

    var existingPolicy: ExistingWorkerPolicy? = null

    fun startWith(request: OneTimeOperation) {
        operations.add(0, MultipleOperation.Operation.from(request = request))
    }

    fun startWith(block: OneTimeDefinition.() -> Unit) {
        val request = OneTimeDefinition().apply(block)
            .build()

        operations.add(0, MultipleOperation.Operation.from(request = request))
    }


    fun then(request: OneTimeOperation) {
        operations.add(MultipleOperation.Operation(request = request))
    }

    fun then(block: OneTimeDefinition.() -> Unit) {
        val request = OneTimeDefinition().apply(block)
            .build()
        operations.add(MultipleOperation.Operation.from(request))
    }

    fun constrainedAll(block: ConstraintsDefinition.() -> Unit) {
        commonConstraints = ConstraintsDefinition().apply(block)
            .build()
    }

    fun build(): MultipleOperation {
        val policy = requireNotNull(existingPolicy) { "Existing policy must no be null" }
        val operations = commonConstraints?.let { constraints ->
            operations.map { operation ->
                operation.copy(
                    request = operation.request
                        .copy(commonConstraints = constraints)
                )
            }
        } ?: operations

        return MultipleOperation(
            operations = operations,
            existingPolicy = policy
        )
    }

}

/**
 * Dsl method to create a [MultipleOperation]
 */
fun multipleOperations(
    block: MultipleOperationDefinition.() -> Unit
): MultipleOperation {
    val definition = MultipleOperationDefinition().apply(block)

    return definition.build()
}

/**
 * Method to create a [MultipleOperation] from a [OneTimeOperation]
 */
infix fun OneTimeOperation.then(block: MultipleOperationDefinition.() -> Unit): MultipleOperation {
    val definition = MultipleOperationDefinition()

    definition.startWith(this)

    return definition.apply(block)
        .build()
}