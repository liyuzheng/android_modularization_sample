package yz.l.fm.glide

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.util.Log
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
class TTransformation(private val part: Int, private val colorFilter: Int) :
    BitmapTransformation() {
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
        Log.d("TTransformation", "transform $part $outHeight $outWidth")
        if (part == P_FULL) {
            return transformFull(pool, toTransform, outWidth, outHeight)
        }
        return transformHalf(pool, toTransform, outWidth, outHeight)
    }

    private fun transformHalf(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        val bitmap = TransformationUtils.centerCrop(pool, toTransform, outWidth * 2, outHeight)
        val offsetX = if (part == P_LEFT) 0 else bitmap.width / 2
        val halfWidthBitmap = Bitmap.createBitmap(
            bitmap,
            offsetX,
            0,
            bitmap.width / 2,
            bitmap.height
        )
        val blurBitmap = blur(halfWidthBitmap, 80)
        val colorFilterBitmap = colorFilter(blurBitmap)
        return colorFilterBitmap
    }

    private fun transformFull(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        val bitmap = TransformationUtils.centerCrop(pool, toTransform, outWidth, outHeight)
        val blurBitmap = blur(bitmap, 40)
        val colorFilterBitmap = colorFilter(blurBitmap)
        return colorFilterBitmap
    }

    private fun blur(bitmap: Bitmap, radius: Int): Bitmap {
        val sampling = 6
        val canvas = Canvas(bitmap)
        canvas.scale(1f / sampling, 1f / sampling)
        val paint = Paint()
        paint.flags = Paint.FILTER_BITMAP_FLAG
        canvas.drawBitmap(bitmap, 0f, 0f, paint)
        val blurredBitmap = FastBlur.blur(bitmap, radius, true)
        return blurredBitmap
    }

    private fun colorFilter(bitmap: Bitmap): Bitmap {
        val canvas = Canvas(bitmap)
        val paint = Paint()
        val filter: ColorFilter = PorterDuffColorFilter(colorFilter, PorterDuff.Mode.SRC_OVER)
        paint.colorFilter = filter
        canvas.drawBitmap(bitmap, 0f, 0f, paint)
        return bitmap
    }

    override fun equals(other: Any?): Boolean {
        return other is TTransformation && other.part == part && other.colorFilter == colorFilter
    }

    override fun hashCode(): Int {
        return ID.hashCode() + part + colorFilter
    }
}