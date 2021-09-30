package isel.meic.tfm.fei.utils

import android.content.Context
import android.graphics.Bitmap
import android.util.LruCache
import com.android.volley.toolbox.ImageLoader

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643 - October 2019
 * @mentor: Paulo Pereira
 *
 * Handles the image cache
 */
class LruBitmapCache : LruCache<String, Bitmap>, ImageLoader.ImageCache  {

    constructor(ctx: Context) : super(
        getCacheSize(
            ctx
        )
    )

    companion object {
        private fun getCacheSize (ctx: Context) : Int {
            val displayMetrics = ctx.resources.displayMetrics
            val screenWidth = displayMetrics.widthPixels
            val screenHeight = displayMetrics.heightPixels
            val screenBytes = screenWidth * screenHeight * 4
            return screenBytes * 3
        }
    }

    override fun getBitmap(url: String?): Bitmap =
        get(url)

    override fun putBitmap(url: String?, bitmap: Bitmap?) {
        put(url, bitmap)
    }

    override fun sizeOf(key: String?, value: Bitmap): Int {
        return value.rowBytes * value.height
    }
}