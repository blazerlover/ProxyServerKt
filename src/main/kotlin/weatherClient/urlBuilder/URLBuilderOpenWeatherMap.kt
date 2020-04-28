package weatherClient.urlBuilder

import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import weatherServer.*
import weatherServer.request.ClientRequest
import weatherServer.request.ClientRequestByCityName
import weatherServer.request.ClientRequestByLocation
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
            REQUEST_TYPE_VALUE_CURRENT -> {
                requestType = OPEN_WEATHER_MAP_CURRENT_REQUEST_TYPE
            }
            REQUEST_TYPE_VALUE_HOURLY -> {
                requestType = OPEN_WEATHER_MAP_HOURLY_REQUEST_TYPE
            }
            REQUEST_TYPE_VALUE_DAILY -> {
                requestType = OPEN_WEATHER_MAP_DAILY_REQUEST_TYPE
            }
        }
    }

    private fun buildSelector(requestParams: String): URL {
        return when (requestParams) {
            REQUEST_PARAM_BY_LOCATION -> buildURLByLocation(latitude, longitude)
            else -> {
//                не работает для onecall, приходится вычеслять координаты по названию города!
                getCityLocation(cityName)
                buildURLByLocation(latitude, longitude)
            }
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

    //не работает для /onecall! нужна платная подписка для другого запроса
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

    private fun getCityLocation(cityName: String) {

        var message: String? = null
        val url: URL

        val builder = HttpUrl.parse("https://api.opencagedata.com/geocode/v1/json?q=$cityName&key=608b35e49a8c40b4bdb0d5790029dffd")!!.newBuilder()

        url = builder.build().url()
        println(url)
        val client = OkHttpClient()
        val request = Request.Builder()
                .url(url)
                .build()
        val call = client.newCall(request)
        try {
            call.execute().use { response -> message = response.body()!!.string() }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        var jsonObject = JSONObject(message)
        val jsonArray = jsonObject.getJSONArray("results")
        jsonObject = jsonArray.getJSONObject(5)
        println(jsonObject)
        latitude = jsonObject.getJSONObject("geometry").getDouble("lat")
        longitude = jsonObject.getJSONObject("geometry").getDouble("lng")
    }
}