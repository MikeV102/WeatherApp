package com.example.demo.model

import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import javax.json.JsonObject

class City : JsonModel {
    val nameProperty = SimpleStringProperty()
    var name : String by nameProperty

    override fun updateModel(json: JsonObject) {
        with(json){
            name = getString("city_name")
        }
    }

    override fun toString(): String  = name


}

