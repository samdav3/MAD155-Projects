package com.example.googlemapapp

import com.google.android.gms.maps.model.LatLng

data class PlaceResponse(
        val geometry: Geometry,
        val name: Geometry,
        val vicinity: Geometry,
        val rating: Geometry
    ) {

        data class Geometry(
            val location: GeometryLocation,
            val name: String,
            val vicinity: String,
            val rating: Float
        )

        data class GeometryLocation(
            val lat: Double,
            val lng: Double
        )
}

    fun PlaceResponse.toPlace(): Place = Place(
        name = geometry.name,
        latLng = LatLng(geometry.location.lat, geometry.location.lng),
        address = geometry.vicinity,
        rating = geometry.rating
    )
