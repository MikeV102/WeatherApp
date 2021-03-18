package com.example.demo.model

import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import javax.json.JsonObject

class ForecastPayload: JsonModel {



    val cityNameProperty = SimpleStringProperty()
    var cityName : String by cityNameProperty

    val countryCodeProperty = SimpleStringProperty()
    var countryCode : String by countryCodeProperty

    val dataProperty = SimpleListProperty<Data>()
    var data : List<Data> by property(dataProperty)

    override fun updateModel(json: JsonObject) {
        with(json){
            countryCode = getString("country_code")
            cityName = getString("city_name")
            data = getJsonArray("data").toModel()
        }


    }

}

class CityModel : ItemViewModel<ForecastPayload>(){
    val name = bind(ForecastPayload::cityName)
}