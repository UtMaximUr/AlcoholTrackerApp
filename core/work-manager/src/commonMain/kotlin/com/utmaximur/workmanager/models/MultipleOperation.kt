package com.utmaximur.workmanager.models

import com.utmaximur.workmanager.dsl.CommonConstraints
import com.utmaximur.workmanager.dsl.ConstraintsDefinition

data class MultipleOperation internal constructor(
    val operations: List<Operation>,
    val existingPolicy: ExistingWorkerPolicy
) {

    data class Operation(
        val request: OneTimeOperation
    ) {
        companion object {
            fun from(request: OneTimeOperation): Operation = Operation(request = request)

        }
    }

    class Builder {
        private val operations: MutableList<MultipleOperation.Operation> = mutableListOf()
        private var commonConstraints: CommonConstraints? = null

        var existingPolicy: ExistingWorkerPolicy? = null

        fun startWith(request: OneTimeOperation) {
            operations.add(0, Operation.from(request = request))
        }

        fun then(request: OneTimeOperation) {
            operations.add(Operation(request = request))
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
}

