package com.example.demo.view

import com.example.demo.app.Styles
import com.example.demo.controller.ForecastController
import com.example.demo.model.Data
import com.example.demo.model.ForecastPayload
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.input.KeyCode
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.text.FontPosture
import tornadofx.*


class WeatherForecast : View("Climatic") {
    val forecastController: ForecastController by inject()
    var forecastPayload = ForecastPayload()

    var cityLabel: Label by singleAssign()
    var todayTemp: Label by singleAssign()
    var todayIcon: Label by singleAssign()
    var forecastView: DataGrid<Data> by singleAssign()
    var sevenDayForecast : Label by singleAssign()
    var dividerHB: HBox by singleAssign()

    init {
        forecastController.listPayload("Warsaw")
    }

    override val root = borderpane {
        style {
            backgroundColor += c("#666699")
        }
        center = vbox {
            addClass(Styles.contentWrapper)
            currentWeatherView()
            vbox {
                alignment = Pos.CENTER
                cityLabel = label()
                todayIcon = label()
                todayTemp = label()
                sevenDayForecast = label()
                dividerHB = hbox()
                forecastView = datagrid()

            }
        }
    }

    private fun VBox.currentWeatherView() = vbox {
        form {
            paddingAll = 20.0
            fieldset {
                field("Enter City", Orientation.VERTICAL) {
                    textfield(forecastController.selectedCity.name) {
                        validator {
                            if (it.isNullOrBlank()) error("There must be a city provided")
                            else null
                        }
                        setOnKeyPressed {
                            if (it.code == KeyCode.ENTER) {
                                forecastController.selectedCity.commit {
                                    runAsync {
                                        forecastController.allWeather = forecastController.listPayload(cityName =
                                        forecastController.selectedCity.name.value)
                                    } ui {
                                        cityLabel.apply {
                                            addClass(Styles.mainLabels)
                                        }
                                        todayTemp.apply {
                                            addClass(Styles.mainLabels)
                                        }
                                        if (forecastController.allWeather.size != 0) {
                                            forecastPayload = forecastController.allWeather[0]
                                            vbox {
                                                cityLabel.text =" ${forecastPayload.cityName} ${forecastPayload
                                                    .countryCode}, ${forecastPayload.data[0].dateString}"

                                                todayIcon.graphic =
                                                        imageview("file:C:\\Users\\hitma\\Desktop\\" +
                                                                "Kotlin\\WeatherApp\\src\\main\\resources\\${forecastController.getIcon(forecastPayload.data[0].weather.code)}" +
                                                                ".png", lazyload = true) {
                                                            fitHeight = 200.0
                                                            fitWidth = 200.0
                                                            paddingBottom = 10.0
                                                        }

                                                todayTemp.text = "Lowest: ${forecastPayload.data[0].lowTemp} °C  /  " +
                                                        "Highest: ${forecastPayload.data[0].maxTemp} °C  /  " +
                                                        "Description: " +
                                                        "${forecastPayload.data[0].weather.desc}"

                                                dividerHB.style {
                                                    borderColor += box(Color.TRANSPARENT, Color.TRANSPARENT, Color
                                                            .GRAY, Color.TRANSPARENT)
                                                }

                                                sevenDayForecast.text = "seven day forecast"
                                                sevenDayForecast.style {
                                                    fontSize = 14.px
                                                    fill = Color.GREY
                                                    fontStyle = FontPosture.ITALIC
                                                    opacity = 0.7
                                                }

                                                forecastView.items = forecastPayload.data.subList(0,7).observable()
                                                forecastView.apply {
                                                    cellWidth = 120.0
                                                    cellHeight = 200.0
                                                    cellCache {
                                                        stackpane {
                                                            vbox {
                                                                alignment = Pos.TOP_CENTER
                                                                label(it.dateString)
                                                                label {
                                                                    graphic = imageview("file:C:\\Users\\hitma\\Desktop\\Kotlin\\" +
                                                                            "WeatherApp\\src\\main\\resources\\" +
                                                                            "${forecastController.getIcon(it.weather
                                                                                    .code)}.png", lazyload = true).apply {
                                                                        fitHeight = 100.0
                                                                        fitWidth = 100.0
                                                                    }
                                                                    paddingBottom = 20.0

                                                                }
                                                                label("Low: ${it.lowTemp}")
                                                                label("High: ${it.maxTemp}")
                                                            }
                                                        }
                                                    }
                                                }
                                            }

                                        } else {
                                            vbox {
                                                cityLabel.text = "\"${forecastController.selectedCity.name.value}\":" +
                                                        "  " +
                                                        "Sorry, " +
                                                        "couldn't " +
                                                        "find " +
                                                        "such city"

                                                todayTemp.text = ""
                                                todayIcon.graphic = vbox {  }
                                                sevenDayForecast.text = ""
                                                var emptyList = listOf<Data>()
                                                forecastView.items = emptyList.observable()
                                            }
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
    }
}