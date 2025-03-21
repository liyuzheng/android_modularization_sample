package yz.l.fm.glide

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.TransformationUtils
import jp.wasabeef.glide.transformations.internal.FastBlur
import java.security.MessageDigest

/**
 * @author 李宇征
 * @since 2025/3/20 16:44
 * @desc:
 */
class TTransformation(private val part: Int) : BitmapTransformation() {
    companion object {
        const val P_LEFT = -1
        const val P_FULL = 0
        const val P_RIGHT = 1
        private const val VERSION = 1
        private const val ID =
            "com.tinnove.netease.music.utils.transform.TTransformation.$VERSION"
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update((ID + part).toByteArray(Charsets.UTF_8))
    }

    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        if (part == P_FULL) {
            return TransformationUtils.centerCrop(pool, toTransform, outWidth, outHeight)
        }
        val bitmap = TransformationUtils.centerCrop(pool, toTransform, outWidth * 2, outHeight)
        val offsetX = if (part == P_LEFT) 0 else bitmap.width / 2
        val halfWidthBitmap = Bitmap.createBitmap(
            bitmap,
            offsetX,
            0,
            bitmap.width / 2,
            bitmap.height
        )
        val blurBitmap = blur(halfWidthBitmap)
        val colorFilterBitmap = colorFilter(blurBitmap)
//        if(part == P_LEFT) return blurBitmap else
        return colorFilterBitmap
    }

    private fun blur(bitmap: Bitmap): Bitmap {
        val sampling = 6
        val canvas = Canvas(bitmap)
        canvas.scale(1f / sampling, 1f / sampling)
        val paint = Paint()
        paint.flags = Paint.FILTER_BITMAP_FLAG
        canvas.drawBitmap(bitmap, 0f, 0f, paint)
        val blurredBitmap = FastBlur.blur(bitmap, 80, true)
        return blurredBitmap
    }

    private fun colorFilter(bitmap: Bitmap): Bitmap {
        val colorFilter = Color.argb(128, 0, 0, 0)

        val canvas = Canvas(bitmap)
        val paint = Paint()
        val filter: ColorFilter = PorterDuffColorFilter(colorFilter, PorterDuff.Mode.SRC_OVER)
        paint.colorFilter = filter
        canvas.drawBitmap(bitmap, 0f, 0f, paint)
        return bitmap
    }

    override fun equals(other: Any?): Boolean {
        return other is TTransformation && other.part == part
    }

    override fun hashCode(): Int {
        return ID.hashCode() + part
    }
}