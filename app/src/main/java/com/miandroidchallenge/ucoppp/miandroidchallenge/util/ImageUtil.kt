package com.miandroidchallenge.ucoppp.miandroidchallenge.util

import android.app.Application
import android.graphics.Bitmap
import android.support.v4.content.ContextCompat
import android.graphics.BitmapFactory
import android.text.TextUtils
import java.io.*


fun getImagePath(app: Application, finalDirectory: String) = ContextCompat.getNoBackupFilesDir(app)?.absolutePath + File.separator + finalDirectory

fun saveFile(app: Application, bitmap: Bitmap, imageName: String) {
    val imageFile = getImagePath(
            app = app,
            finalDirectory = imageName
    )
    var fos: FileOutputStream? = FileOutputStream(imageFile)
    try {
        val success = bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
        if (success) {
            try {
                fos?.close()
            } catch (ignored: IOException) {
            } finally {
                fos = null
            }
        }
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    } finally {
        if (fos != null) {
            try {
                fos.close()
            } catch (ignored: IOException) {
            }
        }
    }
}

fun getImage(path: String): Bitmap? {
    if (TextUtils.isEmpty(path))
        return null

    val file = File(path)
    var fis: FileInputStream? = null
    try {
        fis = FileInputStream(file)
        return BitmapFactory.decodeStream(fis)
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    } finally {
        if (fis != null) {
            try {
                fis.close()
            } catch (ignored: IOException) {
            }

        }
    }
    return null
}
