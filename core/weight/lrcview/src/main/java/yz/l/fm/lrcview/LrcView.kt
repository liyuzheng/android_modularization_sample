package yz.l.fm.lrcview

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.text.TextUtils
import android.text.format.DateUtils
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.Scroller
import androidx.annotation.Keep
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.withContext
import kotlin.math.abs

/**
 * @author 李宇征
 * @since 2025/3/20 11:07
 * @desc:
 */
@Keep
open class LrcView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    companion object {
        private const val TAG = "LrcView"
        private const val ADJUST_DURATION = 100L
        private const val TIMELINE_KEEP_TIME = 4 * DateUtils.SECOND_IN_MILLIS
    }


    protected var mLrcEntryList: MutableList<LrcEntry> = mutableListOf()
    private val mLrcPaint = TextPaint()
    private val mTimePaint = TextPaint()
    private var mTimeFontMetrics: Paint.FontMetrics? = null
    private var mPlayDrawable: Drawable? = null
    private var mDividerHeight = 0f
    private var mAnimationDuration = 0L
    private var mNormalTextColor = 0
    private var mNormalTextSize = 0f
    private var mCurrentTextColor = 0
    private var mCurrentTextSize = 0f
    private var mTimelineTextColor = 0
    private var mTimelineColor = 0
    private var mTimeTextColor = 0
    private var mDrawableWidth = 0
    private var mTimeTextWidth = 0
    private var mDefaultLabel: String? = null
    private var mLrcPadding = 0f
    private var mOnPlayClickListener: OnPlayClickListener? = null
    private var mOnTapListener: OnTapListener? = null
    private var mAnimator: ValueAnimator? = null
    private var mGestureDetector: GestureDetector? = null
    private var mScroller: Scroller? = null
    private var mOffset = 0f
    private var mCurrentLine = 0
    private var mFlag: Any? = null
    private var isShowTimeline = false
    private var isTouching = false
    private var isFling = false
    private var topOffset = 300f

    /**
     * 歌词显示位置，靠左/居中/靠右
     */
    private var mTextGravity = 0

    /**
     * 播放按钮点击监听器，点击后应该跳转到指定播放位置
     */
    interface OnPlayClickListener {
        /**
         * 播放按钮被点击，应该跳转到指定播放位置
         *
         * @param view 歌词控件
         * @param time 选中播放进度
         * @return 是否成功消费该事件，如果成功消费，则会更新UI
         */
        fun onPlayClick(view: LrcView, time: Long): Boolean
    }

    /**
     * 歌词控件点击监听器
     */
    interface OnTapListener {
        /**
         * 歌词控件被点击
         *
         * @param view 歌词控件
         * @param floatX   点击坐标x，相对于控件
         * @param floatY   点击坐标y，相对于控件
         */
        fun onTap(view: LrcView, floatX: Float, floatY: Float)
    }

    private val mSimpleOnGestureListener = object : GestureDetector.SimpleOnGestureListener() {
        // 本次点击仅仅为了停止歌词滚动，则不响应点击事件
        private var isTouchForStopFling = false

        override fun onDown(e: MotionEvent): Boolean {
            if (!hasLrc()) {
                return mOnTapListener != null
            }
            isTouching = true
            removeCallbacks(hideTimelineRunnable)
            if (isFling) {
                isTouchForStopFling = true
                mScroller?.forceFinished(true)
            } else {
                isTouchForStopFling = false
            }
            return mOnPlayClickListener != null || mOnTapListener != null
        }

        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            if (!hasLrc() || mOnPlayClickListener == null) {
                return super.onScroll(e1, e2, distanceX, distanceY)
            }
            if (!isShowTimeline) {
                isShowTimeline = true
            } else {
                mOffset += -distanceY
                mOffset = mOffset.coerceAtMost(getOffset(0))
                mOffset = mOffset.coerceAtLeast(getOffset(mLrcEntryList.size - 1))
            }
            invalidate()
            return true
        }

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            if (!hasLrc() || mOnPlayClickListener == null) {
                return super.onFling(e1, e2, velocityX, velocityY)
            }
            if (isShowTimeline) {
                isFling = true
                removeCallbacks(hideTimelineRunnable)
                mScroller?.fling(
                    0,
                    mOffset.toInt(),
                    0,
                    velocityY.toInt(),
                    0,
                    0,
                    getOffset(mLrcEntryList.size - 1).toInt(),
                    getOffset(0).toInt()
                )
                return true
            }
            return super.onFling(e1, e2, velocityX, velocityY)
        }

        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            if (hasLrc() && mOnPlayClickListener != null && isShowTimeline && mPlayDrawable?.bounds?.contains(
                    e.x.toInt(),
                    e.y.toInt()
                ) == true
            ) {
                val centerLine = getCenterLine()
                val centerLineTime = mLrcEntryList[centerLine].getTime()
                // onPlayClick 消费了才更新 UI
                if (mOnPlayClickListener != null && mOnPlayClickListener?.onPlayClick(
                        this@LrcView,
                        centerLineTime
                    ) == true
                ) {
                    isShowTimeline = false
                    removeCallbacks(hideTimelineRunnable)
                    mCurrentLine = centerLine
                    Log.d(TAG, "mOnPlayClickListener $mCurrentLine")
                    invalidate()
                    return true
                }
            } else if (mOnTapListener != null && !isTouchForStopFling) {
                mOnTapListener?.onTap(this@LrcView, e.x, e.y)
            }
            return super.onSingleTapConfirmed(e)
        }
    }


    init {
        init(attrs)
    }

    protected open fun init(attrs: AttributeSet?) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.LrcView)
        mCurrentTextSize = ta.getDimension(
            R.styleable.LrcView_lrcTextSize,
            resources.getDimension(R.dimen.lrc_text_size)
        )
        mNormalTextSize = ta.getDimension(
            R.styleable.LrcView_lrcNormalTextSize,
            resources.getDimension(R.dimen.lrc_text_size)
        )
        if (mNormalTextSize == 0f) {
            mNormalTextSize = mCurrentTextSize
        }

        mDividerHeight = ta.getDimension(
            R.styleable.LrcView_lrcDividerHeight,
            resources.getDimension(R.dimen.lrc_divider_height)
        )
        val defDuration = resources.getInteger(R.integer.lrc_animation_duration)
        mAnimationDuration =
            ta.getInt(R.styleable.LrcView_lrcAnimationDuration, defDuration).toLong()
        mAnimationDuration =
            if (mAnimationDuration < 0) defDuration.toLong() else mAnimationDuration
        mNormalTextColor = ta.getColor(
            R.styleable.LrcView_lrcNormalTextColor,
            context.getColor(R.color.lrc_normal_text_color)
        )
        mCurrentTextColor = ta.getColor(
            R.styleable.LrcView_lrcCurrentTextColor,
            context.getColor(R.color.lrc_current_text_color)
        )
        mTimelineTextColor = ta.getColor(
            R.styleable.LrcView_lrcTimelineTextColor,
            context.getColor(R.color.lrc_timeline_text_color)
        )
        mDefaultLabel = ta.getString(R.styleable.LrcView_lrcLabel)
        mDefaultLabel =
            if (TextUtils.isEmpty(mDefaultLabel)) context.getString(R.string.root_default_lyric) else mDefaultLabel
        mLrcPadding = ta.getDimension(R.styleable.LrcView_lrcPadding, 0f)
        mTimelineColor = ta.getColor(
            R.styleable.LrcView_lrcTimelineColor,
            context.getColor(R.color.lrc_timeline_color)
        )
        val timelineHeight = ta.getDimension(
            R.styleable.LrcView_lrcTimelineHeight,
            resources.getDimension(R.dimen.lrc_timeline_height)
        )
        mPlayDrawable = ta.getDrawable(R.styleable.LrcView_lrcPlayDrawable)
        mTimeTextColor = ta.getColor(
            R.styleable.LrcView_lrcTimeTextColor,
            context.getColor(R.color.lrc_time_text_color)
        )
        val timeTextSize = ta.getDimension(
            R.styleable.LrcView_lrcTimeTextSize,
            resources.getDimension(R.dimen.lrc_time_text_size)
        )
        mTextGravity = ta.getInteger(R.styleable.LrcView_lrcTextGravity, LrcEntry.GRAVITY_CENTER)
        topOffset = ta.getDimension(R.styleable.LrcView_lrcTopOffset, 300f)
        ta.recycle()
        mDrawableWidth = resources.getDimension(R.dimen.lrc_drawable_width).toInt()
        mTimeTextWidth = resources.getDimension(R.dimen.lrc_time_width).toInt()
        mLrcPaint.isAntiAlias = true
        mLrcPaint.textSize = mCurrentTextSize
        mLrcPaint.textAlign = Paint.Align.LEFT
        mTimePaint.isAntiAlias = true
        mTimePaint.textSize = timeTextSize
        mTimePaint.textAlign = Paint.Align.CENTER
        mTimePaint.strokeWidth = timelineHeight
        mTimePaint.strokeCap = Paint.Cap.ROUND
        mTimeFontMetrics = mTimePaint.fontMetrics

        mGestureDetector = GestureDetector(context, mSimpleOnGestureListener)
        mGestureDetector?.setIsLongpressEnabled(false)
        mScroller = Scroller(context)
    }

    /**
     * 设置非当前行歌词字体颜色
     */
    fun setNormalColor(normalColor: Int) {
        mNormalTextColor = normalColor
        postInvalidate()
    }

    /**
     * 普通歌词文本字体大小
     */
    open fun setNormalTextSize(size: Float) {
        mNormalTextSize = size
    }

    /**
     * 当前歌词文本字体大小
     */
    open fun setCurrentTextSize(size: Float) {
        mCurrentTextSize = size
    }

    /**
     * 设置当前行歌词的字体颜色
     */
    fun setCurrentColor(currentColor: Int) {
        mCurrentTextColor = currentColor
        postInvalidate()
    }

    /**
     * 设置拖动歌词时选中歌词的字体颜色
     */
    fun setTimelineTextColor(timelineTextColor: Int) {
        mTimelineTextColor = timelineTextColor
        postInvalidate()
    }

    /**
     * 设置拖动歌词时时间线的颜色
     */
    fun setTimelineColor(timelineColor: Int) {
        mTimelineColor = timelineColor
        postInvalidate()
    }

    /**
     * 设置拖动歌词时右侧时间字体颜色
     */
    fun setTimeTextColor(timeTextColor: Int) {
        mTimeTextColor = timeTextColor
        postInvalidate()
    }

    /**
     * 设置歌词是否允许拖动
     *
     * @param draggable           是否允许拖动
     * @param onPlayClickListener 设置歌词拖动后播放按钮点击监听器，如果允许拖动，则不能为 null
     */
    fun setDraggable(draggable: Boolean, onPlayClickListener: OnPlayClickListener?) {
        if (draggable) {
            if (onPlayClickListener == null) {
                throw IllegalArgumentException("if draggable == true, onPlayClickListener must not be null")
            }
            mOnPlayClickListener = onPlayClickListener
        } else {
            mOnPlayClickListener = null
        }
    }

    /**
     * 设置播放按钮点击监听器
     *
     * @param onPlayClickListener 如果为非 null ，则激活歌词拖动功能，否则将将禁用歌词拖动功能
     * @deprecated use [setDraggable] instead
     */
    @Deprecated("use setDraggable instead")
    fun setOnPlayClickListener(onPlayClickListener: OnPlayClickListener?) {
        mOnPlayClickListener = onPlayClickListener
    }

    /**
     * 设置歌词控件点击监听器
     *
     * @param onTapListener 歌词控件点击监听器
     */
    fun setOnTapListener(onTapListener: OnTapListener?) {
        mOnTapListener = onTapListener
    }

    /**
     * 设置歌词为空时屏幕中央显示的文字，如“暂无歌词”
     */
    fun setLabel(label: String) {
        mDefaultLabel = label
        invalidate()
    }

    open fun setTopOffset(offset: Float) {
        topOffset = offset
        invalidate()
    }

    /**
     * 歌词是否有效
     *
     * @return true，如果歌词有效，否则false
     */
    fun hasLrc(): Boolean {
        return mLrcEntryList.isNotEmpty()
    }

    /**
     * 刷新歌词
     *
     * @param time 当前播放时间
     */
    fun updateTime(time: Long) {
        if (!hasLrc()) {
            return
        }
        val line = findShowLine(time)
        if (line != mCurrentLine) {
            mCurrentLine = line
            if (!isShowTimeline) {
                smoothScrollTo(line)
            } else {
                invalidate()
            }
        }
    }

    open fun setTextGravity(gravity: Int) {
        mTextGravity = gravity
        mLrcEntryList.forEach { lrcEntry ->
            lrcEntry.init(mLrcPaint, getLrcWidth().toInt(), mTextGravity)
        }
        invalidate()
    }

    open fun setLrcDividerHeight(dividerHeight: Float) {
        mDividerHeight = dividerHeight
        invalidate()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (changed) {
            initPlayDrawable()
            initEntryList()
            if (hasLrc()) {
                smoothScrollTo(mCurrentLine, 0L)
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerY = topOffset

        // 无歌词文件
        if (!hasLrc()) {
            mLrcPaint.color = mCurrentTextColor
            val staticLayout = StaticLayout.Builder.obtain(
                mDefaultLabel.toString(),  // 要显示的文本
                0,  // 文本的起始索引，这里从 0 开始
                mDefaultLabel.toString().length,  // 文本的结束索引
                mLrcPaint,  // 用于绘制文本的画笔
                getLrcWidth().toInt()  // 布局的宽度
            )
                .setAlignment(Layout.Alignment.ALIGN_CENTER)  // 设置文本对齐方式为居中
                .setLineSpacing(0f, 1f)  // 设置行间距，第一个参数是额外的间距，第二个是倍数
                .setIncludePad(false)  // 设置是否包含额外的填充
                .build()  // 构建 StaticLayout 对象
            drawText(canvas, staticLayout, centerY.toFloat())
            return
        }

        val centerLine = getCenterLine()

        if (isShowTimeline) {
            mPlayDrawable?.draw(canvas)

            mTimePaint.color = mTimelineColor
            canvas.drawLine(
                mTimeTextWidth.toFloat(),
                centerY,
                (width - mTimeTextWidth).toFloat(),
                centerY,
                mTimePaint
            )

            mTimePaint.color = mTimeTextColor
            val timeText = LrcUtils.formatTime(mLrcEntryList[centerLine].getTime())
            val timeX = (width - mTimeTextWidth / 2).toFloat()
            val timeY =
                centerY - ((mTimeFontMetrics?.descent ?: 0f) + (mTimeFontMetrics?.ascent ?: 0f)) / 2
            canvas.drawText(timeText, timeX, timeY, mTimePaint)
        }

        canvas.translate(0f, mOffset)

        var y = 0f
        for (i in mLrcEntryList.indices) {
            if (i > 0) {
                y += ((mLrcEntryList[i - 1].getHeight() + mLrcEntryList[i].getHeight()) shr 1) + mDividerHeight
            }
            if (i == mCurrentLine) {
                mLrcPaint.textSize = mCurrentTextSize
                mLrcPaint.color = mCurrentTextColor
                mLrcPaint.typeface = android.graphics.Typeface.create(
                    android.graphics.Typeface.DEFAULT,
                    android.graphics.Typeface.BOLD
                )
            } else if (isShowTimeline && i == centerLine) {
                mLrcPaint.textSize = mCurrentTextSize
                mLrcPaint.color = mTimelineTextColor
                mLrcPaint.typeface = android.graphics.Typeface.create(
                    android.graphics.Typeface.DEFAULT,
                    android.graphics.Typeface.NORMAL
                )
            } else {
                mLrcPaint.textSize = mNormalTextSize
                mLrcPaint.color = mNormalTextColor
                mLrcPaint.typeface = android.graphics.Typeface.create(
                    android.graphics.Typeface.DEFAULT,
                    android.graphics.Typeface.NORMAL
                )
            }
            drawText(canvas, mLrcEntryList[i].getStaticLayout(), y)
        }
    }

    /**
     * 画一行歌词
     *
     * @param y 歌词中心 Y 坐标
     */
    protected open fun drawText(canvas: Canvas, staticLayout: StaticLayout?, y: Float) {
        canvas.save()
        canvas.translate(mLrcPadding, y - ((staticLayout?.height ?: 0) shr 1))
        staticLayout?.draw(canvas)
        canvas.restore()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_CANCEL) {
            isTouching = false
            // 手指离开屏幕，启动延时任务，恢复歌词位置
            if (hasLrc() && isShowTimeline) {
                adjustCenter()
                postDelayed(hideTimelineRunnable, TIMELINE_KEEP_TIME)
            }
        }
        return mGestureDetector?.onTouchEvent(event) ?: super.onTouchEvent(event)
    }

    /**
     * 手势监听器
     */

    private val hideTimelineRunnable = Runnable {
        Log.d(TAG, "hideTimelineRunnable run")
        if (hasLrc() && isShowTimeline) {
            isShowTimeline = false
            smoothScrollTo(mCurrentLine)
        }
    }

    override fun computeScroll() {
        if (mScroller?.computeScrollOffset() == true) {
            mOffset = mScroller?.currY?.toFloat() ?: 0f
            invalidate()
        }

        if (isFling && mScroller?.isFinished == true) {
            Log.d(TAG, "fling finish")
            isFling = false
            if (hasLrc() && !isTouching) {
                adjustCenter()
                postDelayed(hideTimelineRunnable, TIMELINE_KEEP_TIME)
            }
        }
    }

    override fun onDetachedFromWindow() {
        removeCallbacks(hideTimelineRunnable)
        super.onDetachedFromWindow()
        job?.cancel()
    }

    protected open fun onLrcLoaded(entryList: List<LrcEntry>?) {
        if (!entryList.isNullOrEmpty()) {
            mLrcEntryList.addAll(entryList)
        }
        mLrcEntryList.sort()
        initEntryList()
        invalidate()
    }

    protected open fun initPlayDrawable() {
        val l = (mTimeTextWidth - mDrawableWidth) / 2
        val t = topOffset - mDrawableWidth / 2
        val r = l + mDrawableWidth
        val b = t + mDrawableWidth
        mPlayDrawable?.setBounds(l, t.toInt(), r, b.toInt())
    }

    protected open fun initEntryList() {
        if (!hasLrc() || width == 0) {
            return
        }
        mLrcEntryList.forEach { lrcEntry ->
            lrcEntry.init(mLrcPaint, getLrcWidth().toInt(), mTextGravity)
        }
        mOffset = topOffset
    }

    protected fun reset() {
        endAnimation()
        mScroller?.forceFinished(true)
        isShowTimeline = false
        isTouching = false
        isFling = false
        removeCallbacks(hideTimelineRunnable)
        mLrcEntryList.clear()
        mOffset = 0f
        mCurrentLine = 0
        invalidate()
    }

    /**
     * 将中心行微调至正中心
     */
    private fun adjustCenter() {
        smoothScrollTo(getCenterLine(), ADJUST_DURATION)
    }

    /**
     * 滚动到某一行
     */
    private fun smoothScrollTo(line: Int) {
        smoothScrollTo(line, mAnimationDuration)
    }

    /**
     * 滚动到某一行
     */
    private fun smoothScrollTo(line: Int, duration: Long) {
        val offset = getOffset(line)
        endAnimation()

        mAnimator = ValueAnimator.ofFloat(mOffset, offset)
        mAnimator?.duration = duration
        mAnimator?.interpolator = LinearInterpolator()
        mAnimator?.addUpdateListener { animation ->
            mOffset = animation.animatedValue as Float
            invalidate()
        }
        mAnimator?.start()
    }

    /**
     * 结束滚动动画
     */
    private fun endAnimation() {
        if (mAnimator != null && mAnimator?.isRunning == true) {
            mAnimator?.end()
        }
    }

    /**
     * 二分法查找当前时间应该显示的行数（最后一个 <= time 的行数）
     */
    protected open fun findShowLine(time: Long): Int {
        var left = 0
        var right = mLrcEntryList.size - 1
        while (left <= right) {
            val middle = (left + right) / 2
            val middleTime = mLrcEntryList[middle].getTime()

            if (time < middleTime) {
                right = middle - 1
            } else {
                if (middle + 1 >= mLrcEntryList.size || time < mLrcEntryList[middle + 1].getTime()) {
                    return middle
                }

                left = middle + 1
            }
        }

        return 0
    }

    /**
     * 获取当前在视图中央的行数
     */
    protected open fun getCenterLine(): Int {
        var centerLine = 0
        var minDistance = Float.MAX_VALUE
        for (i in mLrcEntryList.indices) {
            if (abs(mOffset - getOffset(i)) < minDistance) {
                minDistance = abs(mOffset - getOffset(i))
                centerLine = i
            }
        }
        return centerLine
    }

    /**
     * 获取歌词距离视图顶部的距离
     * 采用懒加载方式
     */
    protected open fun getOffset(line: Int): Float {
        if (mLrcEntryList[line].getOffset() == Float.MIN_VALUE) {
            var offset = topOffset
            for (i in 1..line) {
                offset -= ((mLrcEntryList[i - 1].getHeight() + mLrcEntryList[i].getHeight()) shr 1) + mDividerHeight
            }
            mLrcEntryList[line].setOffset(offset)
        }

        return mLrcEntryList[line].getOffset()
    }

    /**
     * 获取歌词宽度
     */
    private fun getLrcWidth(): Float {
        return width - mLrcPadding * 2
    }

    private fun getFlag(): Any? {
        return mFlag
    }

    private fun setFlag(flag: Any?) {
        this.mFlag = flag
    }

    @OptIn(DelicateCoroutinesApi::class)
    protected val singleThreadContext = newSingleThreadContext("SingleThreadContext")
    protected val scope = CoroutineScope(singleThreadContext)
    protected var job: Job? = null
    open fun loadLrc(mainLrcText: String) {
        job?.cancel()
        reset()
        val sb = StringBuilder("file://")
        sb.append(mainLrcText)
        val flag = sb.toString()
        setFlag(flag)
        // 使用协程作用域
        job = scope.launch(Dispatchers.Main.immediate) {
            // 在 IO 线程执行耗时操作
            val lrcEntries = withContext(Dispatchers.IO) {
                LrcUtils.parseLrc(mainLrcText)
            }
            // 回到主线程更新 UI
            if (getFlag() == flag) {
                onLrcLoaded(lrcEntries)
                setFlag(null)
            }
        }
    }
}