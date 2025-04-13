package com.utmaximur.domain

fun interface Validator<in T : Any, out R> {
    fun validate(value: T): R
}