package com.hnp.gplx600.api

import android.content.Context
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

object JsonFileReader {
  fun loadJSONFromAsset(context: Context, fileName: String?): JSONObject? {
    val json: String
    try {
      val file = context.assets.open(fileName!!)
      val size = file.available()
      val buffer = ByteArray(size)
      file.read(buffer)
      file.close()
      json = String(buffer, charset("UTF-8"))
      return JSONObject(json)
    } catch (e: IOException) {
      e.printStackTrace()
      return null
    } catch (e: JSONException) {
      e.printStackTrace()
      return null
    }
  }

  fun loadJSONArrayFromAsset(context: Context, fileName: String?): JSONArray? {
    val json: String
    try {
      val file = context.assets.open(fileName!!)
      val size = file.available()
      val buffer = ByteArray(size)
      file.read(buffer)
      file.close()
      json = String(buffer, charset("UTF-8"))
      return JSONArray(json)
    } catch (e: IOException) {
      e.printStackTrace()
      return null
    } catch (e: JSONException) {
      e.printStackTrace()
      return null
    }
  }
}
