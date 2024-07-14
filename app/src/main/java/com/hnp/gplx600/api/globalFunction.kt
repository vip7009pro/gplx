package com.hnp.gplx600.api

import android.annotation.SuppressLint
import android.content.Context
import com.hnp.gplx600.roomdb.QuestionViewModel
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class GlobalFunction {

  @SuppressLint("SuspiciousIndentation")
  fun initDatabase(ct: Context, vm: QuestionViewModel){
    val questionList: JSONArray = JsonFileReader.loadJSONArrayFromAsset(context = ct , fileName = "600cau.json") as JSONArray
    print("questionList length:" +questionList.length())
    if(questionList.length()>0)
    {
      for (i in 0..<questionList.length()) {
        try {
          val jsonObject: JSONObject = questionList.getJSONObject(i)
          val question = ErpInterface.Question(jsonObject.optInt("index"),jsonObject.optString("image"), jsonObject.optInt("no"), jsonObject.optString("text"), jsonObject.optString("tip"),jsonObject.optInt("dapAn"), jsonObject.optString("answers"), jsonObject.optInt("required"), jsonObject.optInt("topic"), jsonObject.optInt("a1"), jsonObject.optInt("a2"), jsonObject.optInt("a3"), jsonObject.optInt("a4"), jsonObject.optInt("b1"))
          vm.addQuestion(question)
        }
        catch (e: JSONException) {
          e.printStackTrace()
        }
      }
    }


  }
}