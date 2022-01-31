package com.example.myloginapp.services

import com.example.myloginapp.extentions.*
import com.example.myloginapp.model.BaseResponseModel
import com.example.myloginapp.model.BaseResponseModelLowerCase
import com.example.myloginapp.model.instagrammodel.InstagramAccessTokenResponseModel
import com.example.myloginapp.model.instagrammodel.InstagramUsernameResponseModel
import com.example.myloginapp.model.instagrammodel.RequestValueModel
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import javax.net.ssl.HttpsURLConnection

object WebService
{


    //region POST HTTPS

    @kotlin.jvm.Throws(IOException::class)
    fun requestPostHttps(stringUrl : String?, jsonObject : JSONObject, stringContentType : String) : BaseResponseModel?
    {
        val httpsURLConnection : HttpsURLConnection = URL(stringUrl).openConnection() as HttpsURLConnection
        httpsURLConnection.readTimeout = INT_3_000_TIMEMILLS
        httpsURLConnection.connectTimeout = INT_3_000_TIMEMILLS
        httpsURLConnection.requestMethod = STRING_POST
        httpsURLConnection.setRequestProperty(STRING_CONTENT_TYPE, stringContentType)
        httpsURLConnection.setRequestProperty("Accept", "*/*")
        httpsURLConnection.doInput = true
        httpsURLConnection.doOutput = true
        val outputStream : OutputStream = httpsURLConnection.outputStream
        val bufferedWriter = BufferedWriter(OutputStreamWriter(outputStream,"UTF-8"))
        bufferedWriter.write(jsonObject.toString())
        bufferedWriter.flush()
        bufferedWriter.close()
        outputStream.close()
        print(httpsURLConnection.responseMessage)
        if (httpsURLConnection.responseCode == HttpsURLConnection.HTTP_OK)
        {
            val `in` = BufferedReader(InputStreamReader(httpsURLConnection.inputStream))
            val stringBuffered = StringBuffer("")
            var line: String? = ""
            while (`in`.readLine().also
                {
                    line = it
            } != null)
            {
                stringBuffered.append(line)
                break
            }
            `in`.close()
            return stringBuffered.toString().parseStringToJson()
        } else
        {
            return null
        }
    }

    //endregion


    //region POST HTTP

    @kotlin.jvm.Throws(IOException::class)
    fun requestPostHttp(stringUrl : String?, jsonObject : JSONObject) : BaseResponseModelLowerCase?
    {
        val httpsURLConnection : HttpURLConnection = URL(stringUrl).openConnection() as HttpURLConnection
        httpsURLConnection.readTimeout = INT_3_000_TIMEMILLS
        httpsURLConnection.connectTimeout = INT_3_000_TIMEMILLS
        httpsURLConnection.requestMethod = STRING_POST
        httpsURLConnection.setRequestProperty(STRING_CONTENT_TYPE, STRING_APPLICATION_TYPE)
        httpsURLConnection.setRequestProperty("Accept", "*/*")
        httpsURLConnection.doInput = true
        httpsURLConnection.doOutput = true
        val outputStream : OutputStream = httpsURLConnection.outputStream
        val bufferedWriter = BufferedWriter(OutputStreamWriter(outputStream,"UTF-8"))
        bufferedWriter.write(jsonObject.toString())
        bufferedWriter.flush()
        bufferedWriter.close()
        outputStream.close()
        if (httpsURLConnection.responseCode == HttpsURLConnection.HTTP_OK)
        {
            val `in` = BufferedReader(InputStreamReader(httpsURLConnection.inputStream))
            val stringBuffered = StringBuffer("")
            var line: String? = ""
            while (`in`.readLine().also
                {
                    line = it
                } != null)
            {
                stringBuffered.append(line)
                break
            }
            `in`.close()
            return stringBuffered.toString().parseStringToJson2()
        } else
        {
            return null
        }
    }

    //endregion


    //region INSTAGRAMTOKENREQUEST

    fun requestAccessTokenInstagram(stringUrl : String?, listFields : List<RequestValueModel>, stringContentType : String) : InstagramAccessTokenResponseModel?
    {
        val httpsURLConnection : HttpsURLConnection = URL(stringUrl).openConnection() as HttpsURLConnection
        httpsURLConnection.readTimeout = INT_3_000_TIMEMILLS
        httpsURLConnection.connectTimeout = INT_3_000_TIMEMILLS
        httpsURLConnection.requestMethod = STRING_POST
        httpsURLConnection.setRequestProperty(STRING_CONTENT_TYPE, stringContentType)
        httpsURLConnection.setRequestProperty("Accept", "*/*")
        httpsURLConnection.doInput = true
        httpsURLConnection.doOutput = true
        val outputStream : OutputStream = httpsURLConnection.outputStream
        val bufferedWriter = BufferedWriter(OutputStreamWriter(outputStream,"UTF-8"))
        bufferedWriter.write(getQuery(listFields))
        bufferedWriter.flush()
        bufferedWriter.close()
        outputStream.close()
        print(httpsURLConnection.responseMessage)
        if (httpsURLConnection.responseCode == HttpsURLConnection.HTTP_OK)
        {
            val `in` = BufferedReader(InputStreamReader(httpsURLConnection.inputStream))
            val stringBuffered = StringBuffer("")
            var line: String? = ""
            while (`in`.readLine().also
                {
                    line = it
                } != null)
            {
                stringBuffered.append(line)
                break
            }
            `in`.close()
            val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
            val jsonString= gsonBuilder.toJson(JsonParser.parseString(stringBuffered.toString()))
            return gsonBuilder.fromJson(jsonString, InstagramAccessTokenResponseModel::class.java)
        } else
        {
            return null
        }
    }

    private fun getQuery(listFields: List<RequestValueModel>): String
    {
        val stringBuilder = StringBuilder()
        var first = true
        for (field in listFields)
        {
            if (first) first = false else stringBuilder.append("&")
            stringBuilder.append(URLEncoder.encode(field.RequestKey, "UTF-8"))
            stringBuilder.append("=")
            stringBuilder.append(URLEncoder.encode(field.RequestValue, "UTF-8"))
        }
        return stringBuilder.toString()
    }

    //endregion


    //region INSTAGRAMUSERNAMEREQUEST

    fun requestUsernameInstagram(stringUrl : String?) : InstagramUsernameResponseModel?
    {
        val httpsURLConnection : HttpsURLConnection = URL(stringUrl).openConnection() as HttpsURLConnection
        httpsURLConnection.requestMethod = STRING_GET
        print(httpsURLConnection.responseMessage)
        if (httpsURLConnection.responseCode == HttpsURLConnection.HTTP_OK)
        {
            val `in` = BufferedReader(InputStreamReader(httpsURLConnection.inputStream))
            val stringBuffered = StringBuffer("")
            var line: String? = ""
            while (`in`.readLine().also
                {
                    line = it
                } != null)
            {
                stringBuffered.append(line)
                break
            }
            `in`.close()
            val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
            val jsonString= gsonBuilder.toJson(JsonParser.parseString(stringBuffered.toString()))
            return gsonBuilder.fromJson(jsonString, InstagramUsernameResponseModel::class.java)
        } else
        {
            return null
        }
    }

    //endregion


}
