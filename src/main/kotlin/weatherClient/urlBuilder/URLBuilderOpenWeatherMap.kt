package weatherClient.urlBuilder


import org.json.JSONObject
import weatherServer.*
import weatherServer.request.ClientRequest
import weatherServer.request.ClientRequestByCityName
import weatherServer.request.ClientRequestByLocation
import org.json.simple.parser.JSONParser
import java.io.FileNotFoundException
import java.io.FileReader
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL

class URLBuilderOpenWeatherMap : URLBuilder {

    private lateinit var url: URL
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private lateinit var cityName: String
    private lateinit var requestType: String

    override fun buildURLBy(clientRequest: ClientRequest): URL {
        initRequestParams(clientRequest)
        return buildSelector(clientRequest.requestParams)
    }

    private fun initRequestParams(clientRequest: ClientRequest) {
        if (clientRequest.requestParams == REQUEST_PARAM_BY_LOCATION) {
            clientRequest as ClientRequestByLocation
            latitude = clientRequest.latitude
            longitude = clientRequest.longitude
        } else {
            clientRequest as ClientRequestByCityName
            cityName = clientRequest.cityName
        }
        when (clientRequest.requestType) {
            CURRENT -> {
                requestType = OPEN_WEATHER_MAP_CURRENT_REQUEST_TYPE
            }
            HOURLY -> {
                requestType = OPEN_WEATHER_MAP_HOURLY_REQUEST_TYPE
            }
            DAILY -> {
                requestType = OPEN_WEATHER_MAP_DAILY_REQUEST_TYPE
            }
        }
    }

    private fun buildSelector(requestParams: String): URL {
        return when (requestParams) {
            REQUEST_PARAM_BY_LOCATION -> buildURLByLocation(latitude, longitude)
            else ->
//                не работает для onecall, только для current!!!!!!!!

                buildURLByCityName(cityName)
        }
    }

    private fun buildURLByLocation(latitude: Double, longitude: Double): URL {
        val sLatitude = "lat=$latitude"
        val sLongitude = "lon=$longitude"

        val builder = StringBuilder()
        builder.append(OPEN_WEATHER_MAP_PATH)
                .append(requestType)
                .append(sLatitude)
                .append("&")
                .append(sLongitude)
                .append("&units=metric")
                .append(OPEN_WEATHER_MAP_API_KEY)
        try {
            url = URL(builder.toString())
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        println(url)
        return url
    }

    //не работает для /onecall!!!
    private fun buildURLByCityName(cityName: String): URL {
        val sCityName = "q=$cityName"

        val builder = StringBuilder()
        builder.append(OPEN_WEATHER_MAP_PATH)
                .append(requestType)
                .append(sCityName)
                .append("&units=metric")
                .append(OPEN_WEATHER_MAP_API_KEY)
        try {
            url = URL(builder.toString())
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        println(url)
        return url
    }

//    private fun getCityLocation(cityName: String) {
//        var parser: JSONParser = JSONParser()
//        try {
//            val obj: Any = parser.parse(FileReader("city.list.json"))
//            var jsonObject = obj as JSONObject
//
//        } catch (e: FileNotFoundException) {
//            e.printStackTrace()
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//    }
}