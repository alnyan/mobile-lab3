package ua.kpi.comsys.iv7114.mobilelab4

import android.content.Context
import android.graphics.drawable.Drawable
import java.io.IOException
import java.io.InputStream

fun assetDrawable(ctx: Context, filename: String): Drawable? {
    var stream: InputStream? = null
    return try {
        stream = ctx.assets.open(filename)
        Drawable.createFromStream(stream, null)
    } catch (e: IOException) {
        e.printStackTrace()
        null
    } finally {
        stream?.close()
    }
}