package com.cmsbando.erp.api

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Retrofit
import retrofit2.await
import retrofit2.converter.gson.GsonConverterFactory

interface ApiService {
  @POST("api")
  fun login(@Body loginRequest: ErpInterface.LoginInfo): Call<JsonObject>

  @POST("api")
  suspend fun fetchData(@Body data: JsonObject): Response<JsonObject>
}

class ApiHandler {
  val retrofit = Retrofit.Builder().baseUrl("http://cms.ddns.net:3007")
    .addConverterFactory(GsonConverterFactory.create()).build()
  val apiService = retrofit.create(ApiService::class.java)

  fun loginExcute(username: String, password: String, globalVar: GlobalVariable) {

    val loginRequest = ErpInterface.LoginInfo("login", username, password)

    val call = apiService.login(loginRequest)
    call.enqueue(object : Callback<JsonObject> {
      @RequiresApi(Build.VERSION_CODES.O)
      override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
        if (response.isSuccessful) {
          val employee = response.body()
          if (employee != null) {
            val tk_status: String = employee.get("tk_status").asString
            Log.d("xxx", employee.toString())
            if (tk_status != "ng") {
              val empl_info = employee.get("userData").asString
              val gson = Gson()
              val employeeType = object : TypeToken<List<ErpInterface.Employee>>() {}.type
              val persons: List<ErpInterface.Employee> = gson.fromJson(empl_info, employeeType)
              Log.d("xxx", "Trang thai: ${persons.get(0).EMPL_NO}")
              println("Data login: $empl_info")
              globalVar.showDialog(
                "success",
                "Thông báo",
                "Đăng nhập thành công ${persons.get(0).MIDLAST_NAME} ${persons.get(0).FIRST_NAME}",
                {
                  Log.d("xxx", "Day la hanh dong xay ra 1 ${persons.get(0).FIRST_NAME}")
                },
                { Log.d("xxx", "Day la hanh dong xay ra 2 ${persons.get(0).FIRST_NAME}") })

            } else {
              Log.d("xxx", "Login thất bại")
              globalVar.showDialog("error",
                "Thông báo",
                "Đăng nhập thất bại, kiểm tra lại tài khoản và mật khẩu",
                { },
                { })
            }
          } else {
            Log.d("xxx", "Có lỗi")
            globalVar.showDialog("error",
              "Thông báo",
              "Đăng nhập thất bại, kiểm tra lại tài khoản và mật khẩu",
              { },
              { })

          }
          // Xử lý thông tin nhân viên sau khi đăng nhập
        } else {
          // Xử lý lỗi đăng nhập ở đây
          Log.d("xxx", "Có lỗi")
          globalVar.showDialog("error",
            "Thông báo",
            "Đăng nhập thất bại, kiểm tra lại tài khoản và mật khẩu",
            { },
            { })

        }
      }

      override fun onFailure(call: Call<JsonObject>, t: Throwable) {
        // Xử lý lỗi kết nối ở đây

        Log.d("xxx", "Có lỗi 2 ${t.message}")

      }
    })

  }

  suspend fun generalQuery(command: String, data: JsonObject): JsonObject {
    return try {
      val token_string: String =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJwYXlsb2FkIjoiW3tcIkNUUl9DRFwiOlwiMDAyXCIsXCJFTVBMX05PXCI6XCJOSFUxOTAzXCIsXCJDTVNfSURcIjpcIkNNUzExNzlcIixcIkZJUlNUX05BTUVcIjpcIkjDmU5HM1wiLFwiTUlETEFTVF9OQU1FXCI6XCJOR1VZ4buETiBWxIJOXCIsXCJET0JcIjpcIjE5OTMtMTAtMThUMDA6MDA6MDAuMDAwWlwiLFwiSE9NRVRPV05cIjpcIlBow7ogVGjhu40gLSDEkMO0bmcgWHXDom4gLSBTw7NjIFPGoW4gLSBIw6AgTuG7mWlcIixcIlNFWF9DT0RFXCI6MSxcIkFERF9QUk9WSU5DRVwiOlwiSMOgIE7hu5lpXCIsXCJBRERfRElTVFJJQ1RcIjpcIlPDs2MgU8ahblwiLFwiQUREX0NPTU1VTkVcIjpcIsSQw7RuZyBYdcOiblwiLFwiQUREX1ZJTExBR0VcIjpcIlRow7RuIFBow7ogVGjhu41cIixcIlBIT05FX05VTUJFUlwiOlwiMDk3MTA5MjQ1NFwiLFwiV09SS19TVEFSVF9EQVRFXCI6XCIyMDE5LTAzLTExVDAwOjAwOjAwLjAwMFpcIixcIlBBU1NXT1JEXCI6XCIxMjM0NTY3ODlcIixcIkVNQUlMXCI6XCJudmgxOTAzQGNtc2JhbmRvLmNvbVwiLFwiV09SS19QT1NJVElPTl9DT0RFXCI6MixcIldPUktfU0hJRlRfQ09ERVwiOjAsXCJQT1NJVElPTl9DT0RFXCI6MyxcIkpPQl9DT0RFXCI6MSxcIkZBQ1RPUllfQ09ERVwiOjEsXCJXT1JLX1NUQVRVU19DT0RFXCI6MSxcIlJFTUFSS1wiOm51bGwsXCJPTkxJTkVfREFURVRJTUVcIjpcIjIwMjMtMDUtMjhUMTY6MDg6MzcuMTM3WlwiLFwiU0VYX05BTUVcIjpcIk5hbVwiLFwiU0VYX05BTUVfS1JcIjpcIuuCqOyekFwiLFwiV09SS19TVEFUVVNfTkFNRVwiOlwixJBhbmcgbMOgbVwiLFwiV09SS19TVEFUVVNfTkFNRV9LUlwiOlwi6re866y07KSRXCIsXCJGQUNUT1JZX05BTUVcIjpcIk5ow6AgbcOheSAxXCIsXCJGQUNUT1JZX05BTUVfS1JcIjpcIjHqs7XsnqVcIixcIkpPQl9OQU1FXCI6XCJEZXB0IFN0YWZmXCIsXCJKT0JfTkFNRV9LUlwiOlwi67aA7ISc64u064u57J6QXCIsXCJQT1NJVElPTl9OQU1FXCI6XCJTdGFmZlwiLFwiUE9TSVRJT05fTkFNRV9LUlwiOlwi7IKs7JuQXCIsXCJXT1JLX1NISUZfTkFNRVwiOlwiSMOgbmggQ2jDrW5oXCIsXCJXT1JLX1NISUZfTkFNRV9LUlwiOlwi7KCV6recXCIsXCJTVUJERVBUQ09ERVwiOjIsXCJXT1JLX1BPU0lUSU9OX05BTUVcIjpcIlBEXCIsXCJXT1JLX1BPU0lUSU9OX05BTUVfS1JcIjpcIlBEXCIsXCJBVFRfR1JPVVBfQ09ERVwiOjEsXCJNQUlOREVQVENPREVcIjoxLFwiU1VCREVQVE5BTUVcIjpcIlBEXCIsXCJTVUJERVBUTkFNRV9LUlwiOlwi7Ya17JetIChQRClcIixcIk1BSU5ERVBUTkFNRVwiOlwiUUNcIixcIk1BSU5ERVBUTkFNRV9LUlwiOlwi7ZKI7KeIXCJ9XSIsImlhdCI6MTY5NTEwNjM3OCwiZXhwIjoyMDU1MTA2Mzc4fQ.hR-iidSRAq0dIYb42wXKo0VLgRzLVuuZfIJiFXymayc"
      val combineData: JsonObject = JsonObject()
      combineData.addProperty("command", command)
      data.addProperty("token_string", token_string)
      combineData.add("DATA", data)

      val response: Response<JsonObject> = apiService.fetchData(combineData)
      if (response.isSuccessful) {
        response.body() ?: throw Exception("Empty response body")
      } else {
        throw Exception("API request failed with code ${response.code()}")
      }
    } catch (e: Exception) {
      throw e
    }
  }

}