package com.example.demo.controller

import com.example.demo.model.CityModel
import com.example.demo.model.ForecastPayload
import com.example.demo.util.key
import javafx.collections.FXCollections
import tornadofx.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ForecastController: Controller() {


    val selectedCity : CityModel by inject()
    var allWeather = FXCollections.emptyObservableList<ForecastPayload>()

    val api: Rest by inject()

    init {
        api.baseURI = "https://api.weatherbit.io/v2.0/forecast/daily/"
    }

    fun parseDate(date: String) = LocalDate.parse(date, DateTimeFormatter.ISO_DATE)

    fun listPayload(cityName: String) = api.get("?city=${cityName}&key=${key}").list().toModel<ForecastPayload>()

    fun getIcon(code: Int)= when(code) {
        200,201,202,230,231,232,233 -> "tstorms"
        300,301,302,623 -> "flurries"
        500,501,502,511,520,521,522 -> "rain"
        600,601,602,610,611,612,621,622,623 ->"snow"
        700,711,721,731,741,751 -> "fog"
        800 -> "clear"
        801,802 -> "mostlysunny"
        803,804 -> "clouds"
        else -> "unknown"
    }

}