package com.example.demo.model

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import javax.json.JsonObject

class Weather: JsonModel {
    val codeProperty = SimpleIntegerProperty()
    var code : Int by codeProperty

    val descProperty = SimpleStringProperty()
    var desc : String by descProperty

    val iconProperty = SimpleStringProperty()
    var icon : String by iconProperty

    override fun updateModel(json: JsonObject) {
        with(json){
            code = getInt("code")
            desc = getString("description")
            icon = getString("icon")
        }
    }
}