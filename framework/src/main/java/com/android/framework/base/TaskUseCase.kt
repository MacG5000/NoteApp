package com.android.framework.base

abstract class TaskUseCase<ReturnType, Params>: BaseUseCase() {

    abstract fun execute(params: Params): ReturnType
}