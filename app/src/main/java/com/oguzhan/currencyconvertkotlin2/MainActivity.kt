package com.oguzhan.currencyconvertkotlin2

import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun getRates(view: View) {
        val download = Download()
        try {
            val url = "http://data.fixer.io/api/latest?access_key=2553e57b39f56111a1d257dd4e2b4701&format=1"
            download.execute(url)
        } catch (e: Exception) {
        }
    }

    inner class Download : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg p0: String?): String {

            var result = ""

            var url: URL
            var httpURLConnection: HttpURLConnection

            try {

                url = URL(p0[0])
                httpURLConnection = url.openConnection() as HttpURLConnection
                val inputStream = httpURLConnection.inputStream
                val inputStreamReader = InputStreamReader(inputStream)

                var data = inputStreamReader.read()

                while (data > 0) {

                    val character = data.toChar()
                    result += character

                    data = inputStreamReader.read()

                }
                return result

            } catch (e: Exception) {
                return result
            }

        }

        override fun onPostExecute(result: String?) {
            //println(result)

            try {

                val jsonObject = JSONObject(result)
                val rates = jsonObject.getString("rates")

                val jsonObject1 = JSONObject(rates)

                val try1 = jsonObject1.getString("TRY")
                val usd = jsonObject1.getString("USD")
                val cad = jsonObject1.getString("CAD")
                val chf = jsonObject1.getString("CHF")
                val jpy = jsonObject1.getString("JPY")

                textView1.text = "TRY : " + try1
                textView2.text = "USD : " + usd
                textView3.text = "CAD : " + cad
                textView4.text = "CHF : " + chf
                textView5.text = "JPY : " + jpy


            } catch (e: Exception) {

            }

            super.onPostExecute(result)
        }

    }
}
