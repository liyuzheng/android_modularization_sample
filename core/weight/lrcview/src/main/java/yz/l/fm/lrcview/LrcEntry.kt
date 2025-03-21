package yz.l.fm.lrcview

import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint

/**
 * @author 李宇征
 * @since 2025/3/20 11:00
 * @desc:
 */
class LrcEntry(private val time: Long, private var text: String) : Comparable<LrcEntry> {
    private var staticLayout: StaticLayout? = null

    /**
     * 歌词距离视图顶部的距离
     */
    private var offset: Float = Float.MIN_VALUE

    companion object {
        const val GRAVITY_CENTER = 0
        const val GRAVITY_LEFT = 1
        const val GRAVITY_RIGHT = 2
    }

    fun init(paint: TextPaint, width: Int, gravity: Int) {
        val align = when (gravity) {
            GRAVITY_LEFT -> Layout.Alignment.ALIGN_NORMAL
            GRAVITY_RIGHT -> Layout.Alignment.ALIGN_OPPOSITE
            else -> Layout.Alignment.ALIGN_CENTER
        }
        staticLayout = StaticLayout.Builder.obtain(
            getShowText(), // 要显示的文本
            0, // 文本的起始索引
            getShowText().length, // 文本的结束索引
            paint, // 文本绘制的画笔
            width // 布局的宽度
        )
            .setAlignment(align) // 设置文本对齐方式
            .setLineSpacing(0f, 1f) // 设置行间距，第一个参数是额外的间距，第二个是倍数
            .setIncludePad(false) // 设置是否包含额外的填充
            .build()
        offset = Float.MIN_VALUE
    }

    fun getTime(): Long = time

    fun getStaticLayout(): StaticLayout? = staticLayout

    fun getHeight(): Int = staticLayout?.height ?: 0

    fun getOffset(): Float = offset

    fun setOffset(offset: Float) {
        this.offset = offset
    }

    fun getText(): String = text


    private fun getShowText(): String {
        return text
    }

    override fun compareTo(other: LrcEntry): Int {
        return (time - other.getTime()).toInt()
    }
}