package com.android.framework.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.android.framework.network.base.ProgressInformation
import com.android.framework.network.base.ResponseSubscriptionStatus

public abstract class BaseActivity <VM: BaseViewModel, DB: ViewDataBinding>: AppCompatActivity() {

    /**
     * Activity'den sağlanan ViewDataBinding
     */
    private lateinit var binding: DB

    /**
     * Activity'nin eriştiği viewModel
     */
    private lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = provideViewModel()
        binding = DataBindingUtil.setContentView(this, provideLayoutResId())
        bindViewModel(binding)
        viewModel.getProgressLiveData()
            .observe(this) { progressInformation: ProgressInformation? ->
                this.determineLoadingVisibility(
                    progressInformation
                )
            }
        activityStartAnimation(provideStartAnimations())
    }

    override fun finish() {
        super.finish()
        activityFinishAnimation(provideFinishAnimations())
    }

    private fun determineLoadingVisibility(progressInformation: ProgressInformation?) {
        progressInformation?.let {
            val subscriptionStatus: ResponseSubscriptionStatus = it.responseSubscriptionStatus
            val requestTag: String? = progressInformation.tag
            if (subscriptionStatus === ResponseSubscriptionStatus.SUBSCRIBED) {
                showLoading(requestTag)
            } else if (subscriptionStatus === ResponseSubscriptionStatus.FINISHED) {
                hideLoading(requestTag)
            }
        }
    }

    private fun activityStartAnimation(animPair: Pair<Int, Int>?) {
        animPair?.let {
            overridePendingTransition(it.first, it.second)
        }
    }

    private fun activityFinishAnimation(animPair: Pair<Int, Int>?) {
        animPair?.let {
            overridePendingTransition(it.first, it.second)
        }
    }

    abstract fun showLoading(requestTag: String?)

    abstract fun hideLoading(requestTag: String?)

    abstract fun provideViewModel(): VM

    abstract fun provideLayoutResId(): Int

    abstract fun bindViewModel(binding: DB)

    abstract fun provideStartAnimations(): Pair<Int, Int>?

    abstract fun provideFinishAnimations(): Pair<Int, Int>?

    open fun getViewModel(): VM {
        return viewModel
    }

    open fun getBinding(): DB {
        return binding
    }

}