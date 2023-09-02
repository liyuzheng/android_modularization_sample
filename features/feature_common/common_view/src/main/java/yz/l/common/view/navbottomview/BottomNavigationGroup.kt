package yz.l.common.view.navbottomview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.Checkable
import android.widget.LinearLayout
import androidx.annotation.IdRes
import yz.l.common.view.R

/**
 * desc:
 * created by liyuzheng on 2023/9/2 17:03
 */
class BottomNavigationGroup : LinearLayout {
    private var mCheckedId = -1
    private var defaultCheckedId = -1
    private var mOnCheckedChangeListener: OnCheckedChangeListener? = null
    private val options = mutableListOf<BottomNavigationView.Option>()
    private var navViewThemeId = R.style.BottomNavigationViewStyle
    fun options(vararg option: BottomNavigationView.Option) {
        options.addAll(option)
    }

    fun listener(init: () -> OnCheckedChangeListener) {
        this.mOnCheckedChangeListener = init()
    }

    fun defaultChecked(init: () -> Int) {
        this.defaultCheckedId = init()
    }


    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setupAttr(attrs)
    }

    constructor(context: Context) : super(context)

    private fun setupAttr(attrs: AttributeSet?) {
        context.obtainStyledAttributes(attrs, R.styleable.BottomNavigationGroup).run {
            navViewThemeId =
                getResourceId(R.styleable.BottomNavigationGroup_navBottomViewStyle, navViewThemeId)
            recycle()
        }
    }


    fun createBottomNavView() {
        removeAllViews()
        options.forEach {
            val navigationView = BottomNavigationView(context)
            navigationView.layoutParams =
                LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
                    this.weight = 1f
                }
            navigationView.id = it.id
            navigationView.setTheme(navViewThemeId)
            navigationView.setIconRes(it.iconRes)
            navigationView.setText(it.tabText)
            navigationView.setOnClickListener {
                check(navigationView.id)
            }
            if (it.textColor != null) {
                navigationView.setTextColor(requireNotNull(it.textColor))
            }
            if (it.iconW != 0
                && it.iconH != 0
            ) {
                navigationView.setIconWH(it.iconW, it.iconH)
            }
            if (it.textSize != 0f) {
                navigationView.setTextSize(it.textSize)
            }
            addView(navigationView)
        }
        check(defaultCheckedId)

    }

    fun setBadge(@IdRes id: Int, number: Int) {
        findViewById<BottomNavigationView>(id)?.setBadgeNum(number)
    }

    fun check(@IdRes id: Int) {
        if (id != -1 && id == mCheckedId) {
            return
        }

        if (mCheckedId != -1) {
            setCheckedStateForView(mCheckedId, false)
        }

        if (id != -1) {
            setCheckedStateForView(id, true)
        }
        setCheckedId(id)
    }

    private fun setCheckedId(@IdRes id: Int) {
        mCheckedId = id
        if (mOnCheckedChangeListener != null) {
            mOnCheckedChangeListener?.onCheckedChanged(this, mCheckedId)
        }
    }

    private fun setCheckedStateForView(viewId: Int, checked: Boolean) {
        val checkedView = findViewById<View>(viewId)
        if (checkedView != null && checkedView is Checkable) {
            (checkedView as Checkable).isChecked = checked
        }
    }

    fun clearOption() {
        options.clear()
    }

    interface OnCheckedChangeListener {
        fun onCheckedChanged(group: BottomNavigationGroup?, @IdRes checkedId: Int)
    }

}

fun BottomNavigationGroup.setup(init: BottomNavigationGroup.() -> Unit) {
    this.defaultChecked { -1 }
    this.check(-1)
    this.clearOption()
    this.init()
    this.createBottomNavView()
}