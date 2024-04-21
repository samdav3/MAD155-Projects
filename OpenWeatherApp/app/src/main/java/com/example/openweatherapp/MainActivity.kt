package com.example.openweatherapp

import android.os.AsyncTask
import android.os.Bundle
import android.telecom.Call
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class MainActivity : AppCompatActivity() {

    val CITY: String = "crystal lake, USA"
    val API:String = "50578db0934a900fa3f05f86a2a3dde1\n"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        weatherTask().execute()

    }
    inner class weatherTask(): AsyncTask<String, Void, String>() {
        @Deprecated("Deprecated in Java")
        override fun onPreExecute() {
            super.onPreExecute()
            /* Showing the ProgressBar, Making the main design GONE */
            findViewById<ProgressBar>(R.id.loader).visibility = View.VISIBLE
            findViewById<RelativeLayout>(R.id.relativeLayout).visibility = View.GONE
            findViewById<TextView>(R.id.errorText).visibility = View.GONE
    }

    @Deprecated("Deprecated in Java")
    override fun doInBackground(vararg params: String?): String? {
        var response:String?
        try{
            response = URL("https://api.openweathermap.org/data/3.0/weather?q=$CITY&units=imperial&appid=$API").readText(
                Charsets.UTF_8
            )

        }catch (e: Exception){
            response = null
        }
        return response
    }

        @Deprecated("Deprecated in Java")
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            try {
                /* Extracting JSON returns from the API */
                val jsonObj = JSONObject(result)
                val main = jsonObj.getJSONObject("main")
                val sys = jsonObj.getJSONObject("sys")
                val weather = jsonObj.getJSONArray("weather").getJSONObject(0)

                val updatedAt:Long = jsonObj.getLong("dt")
                val updatedAtText = "Updated at: "+ SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(Date(updatedAt*1000))
                var temp = main.getString("current.temp")+"°F"
                val tempMin = "Min Temp: " + main.getString("temp_min")+"°F"
                val tempMax = "Max Temp: " + main.getString("temp_max")+"°F"
                //var farTemp = ((temp * 9 / 5) + 32)
                val windSpeed = main.getString("current.wind_speed")
                val precipPercent = main.getString("current.rain.1h")


                val sunrise:Long = sys.getLong("sunrise")
                val sunset:Long = sys.getLong("sunset")
                val weatherDescription = weather.getString("description")

                val address = jsonObj.getString("name")+", "+sys.getString("country")

                /* Populating extracted data into our views */
                findViewById<TextView>(R.id.textViewCurrentLocation).text = address
                findViewById<TextView>(R.id.textViewCurrentDateTime).text =  updatedAtText
                findViewById<TextView>(R.id.textViewCurrentStatus).text = weatherDescription.capitalize()
                findViewById<TextView>(R.id.textViewCurrentTemp).text = temp
                findViewById<TextView>(R.id.temp_min).text = tempMin
                findViewById<TextView>(R.id.temp_max).text = tempMax
                findViewById<TextView>(R.id.sunriseTime).text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunrise*1000))
                findViewById<TextView>(R.id.sunsetTime).text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunset*1000))
                findViewById<TextView>(R.id.windSpeed).text = windSpeed
                findViewById<TextView>(R.id.precipPercent).text = precipPercent

                /* Views populated, Hiding the loader, Showing the main design */
                findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
                findViewById<RelativeLayout>(R.id.relativeLayout).visibility = View.VISIBLE

            } catch (e: Exception) {
                findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
                findViewById<TextView>(R.id.errorText).visibility = View.VISIBLE
            }

        }

        }



    }