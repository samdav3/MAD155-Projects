package com.example.googlemapapp

import android.content.Context
import androidx.annotation.RawRes
import com.google.android.gms.maps.GoogleMap
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.maps.android.ktx.utils.geojson.geoJsonLayer
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.Charset

/**
 * Reads a list of place JSON objects from the file places.json
 */
class PlacesReader(private val context: Context) {

    // GSON object responsible for converting from JSON to a Place object
    private val gson = Gson()

    // InputStream representing places.json
    private val inputStream: InputStream get() = context.resources.openRawResource(R.raw.place)
    //context.resources.openRawResource(R.raw.place)

    /**
     * Reads the list of place JSON objects in the file places.json
     * and returns a list of Place objects
     */
    fun read(): List<Place> {
        val itemType = object : TypeToken<List<PlaceResponse>>() {}.type
        val reader = InputStreamReader(inputStream)
        return gson.fromJson<List<PlaceResponse>>(reader, itemType).map {
            it.toPlace()
        }
    }
}