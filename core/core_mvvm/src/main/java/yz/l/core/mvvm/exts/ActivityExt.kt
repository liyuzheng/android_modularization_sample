package yz.l.core.mvvm.exts

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * desc:
 * createed by liyuzheng on 2023/8/28 17:23
 */
fun <T : ViewDataBinding> AppCompatActivity.binding(@LayoutRes resId: Int): Lazy<T> =
    lazy { DataBindingUtil.setContentView(this, resId) }