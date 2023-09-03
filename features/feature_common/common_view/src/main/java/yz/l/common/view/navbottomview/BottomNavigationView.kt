package yz.l.common.view.navbottomview

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.Checkable
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import yz.l.common.view.R
import yz.l.common.view.databinding.ViewMainBottomLayoutBinding
import yz.l.core_tool.ext.dp
import yz.l.core_tool.utils.ResourceUtil

/**
 * desc:
 * createed by liyuzheng on 2021/5/13 14:28
 */
class BottomNavigationView : FrameLayout, Checkable {
    constructor(context: Context, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
        setupAttr(attrs)
    }

    constructor(context: Context) : this(context, null)


    private var textColor =
        ResourceUtil.getColorStateList(context, R.color.color_bottom_nav_view_text_default)

    @DrawableRes
    private var icon: Int = 0
    private var iconWidth: Int = 0
    private var iconHeight: Int = 0
    private var tabText: String = ""
    private var textSize: Float = 0f
    private val mBinding by lazy {
        ViewMainBottomLayoutBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )
    }

    private fun setupAttr(attrs: AttributeSet?) {
        context.obtainStyledAttributes(attrs, R.styleable.BottomNavigationView).run {
            textColor = getColorStateList(R.styleable.BottomNavigationView_textColor) ?: textColor
            iconWidth =
                getDimensionPixelSize(R.styleable.BottomNavigationView_iconWidth, 30.dp)
            iconHeight =
                getDimensionPixelSize(R.styleable.BottomNavigationView_iconHeight, 30.dp)
            textSize =
                getDimensionPixelSize(
                    R.styleable.BottomNavigationView_textSize,
                    12.dp
                ).toFloat()
            recycle()
        }
    }

    fun setTheme(themeId: Int) {
        val mTheme = context.resources.newTheme()
        mTheme.applyStyle(themeId, true)
        mTheme.obtainStyledAttributes(
            intArrayOf(
                R.attr.iconWidth,
                R.attr.iconHeight,
                R.attr.textColor,
                R.attr.textSize
            )
        ).run {
            iconWidth =
                this.getDimensionPixelSize(this.getIndex(0), iconWidth)
            iconHeight =
                this.getDimensionPixelSize(this.getIndex(1), iconHeight)
            textColor =
                this.getColorStateList(this.getIndex(2)) ?: textColor
            textSize = this.getDimension(this.getIndex(3), textSize)
            recycle()
        }
        setup()
    }

    private fun setup() {
        clipChildren = false
        clipToPadding = false
        setupIcon()
        setupText()
    }

    private fun setupText() {
        mBinding.tvTab.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
        mBinding.tvTab.text = tabText
        mBinding.tvTab.setTextColor(textColor)
    }

    private fun setupIcon() {
        mBinding.ivTab.layoutParams.width = iconWidth
        mBinding.ivTab.layoutParams.height = iconHeight
        mBinding.ivTab.setImageResource(icon)
    }

    fun setBadgeNum(num: Int) {
        mBinding.ivBadge.setBadgeNum(num)
    }

    fun setText(tabText: String) {
        this.tabText = tabText
        mBinding.tvTab.text = tabText
    }

    fun setIconRes(@DrawableRes iconRes: Int) {
        this.icon = iconRes
        mBinding.ivTab.setImageResource(icon)
    }

    fun setTextColor(color: ColorStateList) {
        this.textColor = color
        mBinding.tvTab.setTextColor(color)
    }

    fun setIconWH(w: Int, h: Int) {
        this.iconWidth = w
        this.iconHeight = h
        mBinding.ivTab.layoutParams.width = w
        mBinding.ivTab.layoutParams.height = h
    }

    fun setTextSize(sp: Float) {
        this.textSize = sp
        mBinding.tvTab.setTextSize(TypedValue.COMPLEX_UNIT_PX, sp)
    }

    override fun setChecked(checked: Boolean) {
        isSelected = checked
    }

    override fun isChecked(): Boolean {
        return isSelected
    }

    override fun toggle() {
        isChecked = !isChecked
    }

    class Option {
        @IdRes
        var id: Int = -1
            private set
        var tabText: String = ""

        @DrawableRes
        var iconRes: Int = 0
            private set

        var textColor: ColorStateList? = null
            private set

        var iconW: Int = 0
            private set

        var iconH: Int = 0
            private set

        var textSize: Float = 0f
            private set

        fun id(init: () -> Int) {
            id = init()
        }

        fun tabText(init: () -> String) {
            tabText = init()
        }

        fun iconRes(init: () -> Int) {
            iconRes = init()
        }

        fun textColor(init: () -> Int) {
            textColor = ResourceUtil.getColorStateList(resId = init())
        }

        fun iconW(init: () -> Int) {
            iconW = init()
        }

        fun iconH(init: () -> Int) {
            iconH = init()
        }

        fun textSize(init: () -> Float) {
            textSize = init()
        }
    }
}

fun bottomNavOption(init: BottomNavigationView.Option.() -> Unit): BottomNavigationView.Option {
    val option = BottomNavigationView.Option()
    option.init()
    return option
}