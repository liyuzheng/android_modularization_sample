package yz.l.core_tool.common

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity

/**
 * desc:
 * createed by liyuzheng on 2023/8/28 17:24
 */
abstract class BaseAct : AppCompatActivity() {
    abstract fun getLayoutId(): Int

    private val mLayout: Int by lazy {
        getLayoutId()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(onBackPressedCallbackEnable) {
                override fun handleOnBackPressed() {
                    onBackPressedCallback()
                }
            })
        setupContentView()
        loadParams(savedInstanceState)
        setupStatusBar()
        setup()
    }

    protected open fun setupContentView() {
        if (mLayout != 0) {
            setContentView(mLayout)
        }
    }


    protected open fun loadParams(savedInstanceState: Bundle?) {

    }

    abstract fun setup()

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
    }

    protected open fun setupStatusBar() {

    }

    protected open fun onBackPressedCallback() {}

    protected open var onBackPressedCallbackEnable = false


}