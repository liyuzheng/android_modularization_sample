package yz.l.core.mvvm

import android.util.SparseArray
import androidx.annotation.CallSuper
import androidx.core.util.forEach
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import yz.l.core_tool.common.BaseAct
import yz.l.core_tool.ext.sparse

/**
 * desc:
 * createed by liyuzheng on 2023/8/28 17:25
 */
abstract class BaseBindingAct<B : ViewDataBinding> : BaseAct() {
    protected abstract val mBinding: B
    private val _variables: SparseArray<ViewModel> by lazy { variables() }

    final override fun getLayoutId(): Int = 0
    override fun setup() {
        beforeSetContentView()
        setupBinding(mBinding)
        setupView()
        setupData()
    }

    /**
     * 需要引入xml中的viewmodel
     * key:BR.xxx
     * value:viewModel
     */
    protected open fun variables(): SparseArray<ViewModel> {
        return sparse()
    }

    /**
     * 数据加载
     */
    protected open fun setupData() = Unit

    /**
     * 控件初始化，一般情况下使用livedata
     * 这里可以做一些rv等控件的固定操作
     */
    protected open fun setupView() = Unit

    @CallSuper
    protected open fun setupBinding(binding: B) {
        _variables.forEach { key, value ->
            binding.setVariable(key, value)
        }
        binding.lifecycleOwner = this
        _variables.clear()
    }

    open fun beforeSetContentView() = Unit

}