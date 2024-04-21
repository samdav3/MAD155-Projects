package com.example.openweatherapiapp


import android.os.Bundle
import android.util.JsonReader
import android.util.JsonToken
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.newCoroutineContext
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale



class MainActivity : AppCompatActivity() {

    val API: String = "08297e7a3cb2f5c97805d3221dc47f37"
    private lateinit var response: String

    val CITY: String = "boulder, usa"
    val LAT: String = "42.21"
    val LONG: String = "88.24"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //IO - network
        //Main - when working with MainActivity
        //Default - math
        CoroutineScope(IO).launch {
            callAPI()
        }

    }

    private suspend fun callAPI() {
        val result = getResultFromAPI()
        return populateInfo(result)
    }

    private suspend fun getResultFromAPI(): String{

        //make the URL CALL
        try{
            val myURL = "https://api.openweathermap.org/data/2.5/weather?q=$CITY&appid=$API&units=imperial"
            response = URL(myURL).readText(
                Charsets.UTF_8
            )

        }catch(e: java.lang.Exception) {
            response = "ERR: $e"
        }

        return response
    }

    private suspend fun populateInfo(incomingJSON: String){
        withContext(Main){


            val jsonObj = JSONObject(incomingJSON)
            val main = jsonObj.getJSONObject("main")
            val sys = jsonObj.getJSONObject("sys")
            val wind = jsonObj.getJSONObject("wind")
            val weather = jsonObj.getJSONArray("weather").getJSONObject(0)

            val updatedAt:Long = jsonObj.getLong("dt")
            val updatedAtText = "Updated at: "+ SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(Date(updatedAt*1000))
            val temp = main.getString("temp")+"°F"
            val tempMin = "Min Temp: " + main.getString("temp_min")+"°F"
            val tempMax = "Max Temp: " + main.getString("temp_max")+"°F"
            val windSpeed = wind.getString("speed") + "MPH"
            val precipPercent = main.getString("humidity") + "%"

            val sunrise:Long = sys.getLong("sunrise")
            val sunset:Long = sys.getLong("sunset")
            val weatherDescription = weather.getString("description")

            val address = jsonObj.getString("name")+", "+sys.getString("country")

            findViewById<TextView>(R.id.textViewCurrentLocation).text = address
            findViewById<TextView>(R.id.textViewCurrentDateTime).text =  updatedAtText
            findViewById<TextView>(R.id.textViewCurrentStatus).text = weatherDescription.replaceFirst(weatherDescription, weatherDescription, false)
            findViewById<TextView>(R.id.textViewCurrentTemp).text = temp.toString()
            findViewById<TextView>(R.id.temp_min).text = tempMin
            findViewById<TextView>(R.id.temp_max).text = tempMax
            findViewById<TextView>(R.id.sunriseTime).text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunrise*1000))
            findViewById<TextView>(R.id.sunsetTime).text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunset*1000))
            findViewById<TextView>(R.id.windSpeed).text = windSpeed
            findViewById<TextView>(R.id.precipPercent).text = precipPercent

        }


    }

}