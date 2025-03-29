package com.utmaximur.workmanager.models

import com.utmaximur.workmanager.dsl.CommonConstraints
import com.utmaximur.workmanager.work.Worker
import com.utmaximur.workmanager.work.WorkerData
import kotlin.reflect.KClass
import kotlin.time.Duration

/**
 * Class to make a request to work
 */
data class OneTimeOperation internal constructor(
    val identifier: String,
    val commonConstraints: CommonConstraints,
    val tags: Set<String>,
    val inputData: WorkerData?,
    val backOffPolicy: BackoffLorrainePolicy?
) {
    class Builder {

        private val tags: MutableSet<String> = mutableSetOf()
        private var commonConstraints: CommonConstraints = CommonConstraints.NONE
        private var inputData: WorkerData? = null
        private var backoffLorrainePolicy: BackoffLorrainePolicy? = null

        private var identifier: String? = null

        fun addIdentifier(identifier: String): Builder {
            this.identifier = identifier
            return this
        }

        inline fun <reified T : Worker> addIdentifier(): Builder {
            addIdentifier(T::class)
            return this
        }

        fun addIdentifier(identifier: KClass<out Worker>): Builder {
            this.identifier = identifier.simpleName
            return this
        }

        fun addTag(tag: String): Builder {
            tags.add(tag)
            return this
        }

        fun backOffPolicy(duration: Duration, policy: BackoffLorrainePolicy.Policy): Builder {
            backoffLorrainePolicy = BackoffLorrainePolicy(
                duration = duration,
                policy = policy
            )
            return this
        }

        fun data(workerData: WorkerData): Builder {
            inputData = workerData
            return this
        }

        fun constraints(constraints: CommonConstraints): Builder {
            commonConstraints = constraints
            return this
        }

        fun build(): OneTimeOperation {
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
}


