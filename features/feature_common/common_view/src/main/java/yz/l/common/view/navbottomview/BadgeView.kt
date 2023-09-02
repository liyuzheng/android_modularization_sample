package yz.l.common.view.navbottomview

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import yz.l.common.view.R
import yz.l.core_tool.ext.dp

/**
 * desc:
 * createed by liyuzheng on 2021/5/13 15:56
 */
class BadgeView(context: Context, attrs: AttributeSet) : AppCompatTextView(context, attrs) {
    companion object {
        const val TYPE_NUM = 1
        const val TYPE_CIRCLE = 2
    }

    private var maxNum = 99
    private var type = TYPE_NUM
    private var currNum = 0

    init {
        context.obtainStyledAttributes(attrs, R.styleable.BadgeView).run {
            maxNum = getInt(R.styleable.BadgeView_maxNum, 99)
            type = getInt(R.styleable.BadgeView_style, TYPE_NUM)
            recycle()
        }
        minWidth = 18.dp
        minHeight = 18.dp
        gravity = Gravity.CENTER
        setPadding(5.dp, 0, 5.dp, 0)
        setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10f)
        setBackgroundResource(R.drawable.bg_circle_ff5a01)
        setTextColor(Color.WHITE)
        typeface = Typeface.DEFAULT_BOLD
    }

    fun setMaxNum(maxNum: Int) {
        this.maxNum = maxNum
        setBadgeNum(currNum)
    }

    fun setBadgeNum(num: Int) {
        currNum = num
        if (num == 0)
            visibility = View.INVISIBLE
        else {
            visibility = View.VISIBLE
            text = if (num > maxNum) {
                "$maxNum+"
            } else {
                "$num"
            }
        }
    }
}