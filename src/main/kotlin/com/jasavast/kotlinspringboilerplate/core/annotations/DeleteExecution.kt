package com.jasavast.kotlinspringboilerplate.core.annotations

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Repeatable
annotation class DeleteExecution(final val id:String )
