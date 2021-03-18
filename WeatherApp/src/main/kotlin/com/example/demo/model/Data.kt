package com.example.demo.model

import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import java.time.LocalDate
import javax.json.JsonObject

class Data: JsonModel {


    val weatherProperty = SimpleObjectProperty<Weather>()
    var weather : Weather by weatherProperty

    val maxTempProperty = SimpleDoubleProperty()
    var maxTemp : Double by maxTempProperty

    val lowTempProperty = SimpleDoubleProperty()
    var lowTemp : Double by lowTempProperty

    val dateStringProperty = SimpleStringProperty()
    var dateString : String by dateStringProperty

    override fun updateModel(json: JsonObject) {
            with(json){


                weather = getJsonObject("weather").toModel()

                maxTemp = getDouble("max_temp")
                lowTemp = getDouble("low_temp")
                dateString = getString("datetime")
            }
    }
}