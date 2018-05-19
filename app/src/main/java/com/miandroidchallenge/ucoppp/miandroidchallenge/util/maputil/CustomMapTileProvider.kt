package com.miandroidchallenge.ucoppp.miandroidchallenge.util.maputil

import android.content.res.AssetManager
import com.google.android.gms.maps.model.Tile
import com.google.android.gms.maps.model.TileProvider
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream


class CustomMapTileProvider(private val mAssets: AssetManager) : TileProvider {

    override fun getTile(x: Int, y: Int, zoom: Int): Tile? {
        val image = readTileImage(x, y, zoom)
        return if (image == null) null else Tile(TILE_WIDTH, TILE_HEIGHT, image)
    }

    private fun readTileImage(x: Int, y: Int, zoom: Int): ByteArray? {
        var `in`: InputStream? = null
        var buffer: ByteArrayOutputStream? = null

        try {
            `in` = mAssets.open(getTileFilename(x, y, zoom))
            buffer = ByteArrayOutputStream()

            val nRead: Int
            val data = ByteArray(BUFFER_SIZE)
            nRead = `in`.read(data, 0, BUFFER_SIZE)
            while (nRead != -1) {
                buffer.write(data, 0, nRead)
            }
            buffer.flush()

            return buffer.toByteArray()
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        } catch (e: OutOfMemoryError) {
            e.printStackTrace()
            return null
        } finally {
            if (`in` != null) try {
                `in`.close()
            } catch (ignored: Exception) {
            }

            if (buffer != null) try {
                buffer.close()
            } catch (ignored: Exception) {
            }

        }
    }

    private fun getTileFilename(x: Int, y: Int, zoom: Int): String {
        return "map/" + zoom + '/'.toString() + x + '/'.toString() + y + ".png"
    }

    companion object {
        private val TILE_WIDTH = 256
        private val TILE_HEIGHT = 256
        private val BUFFER_SIZE = 16 * 1024
    }
}