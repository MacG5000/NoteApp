package com.android.framework

import com.android.framework.base.FrameworkConfig

public abstract interface ConfigProvider {

    public abstract fun initFrameworkConfigs(): FrameworkConfig
}