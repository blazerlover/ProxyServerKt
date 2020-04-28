package weatherClient.urlBuilder

import weatherServer.*
import weatherServer.request.ClientRequest
import weatherServer.request.ClientRequestByCityName
import weatherServer.request.ClientRequestByLocation
import java.lang.StringBuilder
import java.net.MalformedURLException
import java.net.URL

class URLBuilderWeatherbit: URLBuilder {

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
                requestType = WEATHERBIT_CURRENT_REQUEST_TYPE
            }
            REQUEST_TYPE_VALUE_HOURLY -> {
                requestType = WEATHERBIT_HOURLY_REQUEST_TYPE
            }
            REQUEST_TYPE_VALUE_DAILY -> {
                requestType = WEATHERBIT_DAILY_REQUEST_TYPE
            }
        }
    }

    private fun buildSelector(requestParams: String): URL {
        return when (requestParams) {
            REQUEST_PARAM_BY_LOCATION -> buildURLByLocation(latitude, longitude)
            else ->
                buildURLByCityName(cityName)
        }
    }

    private fun buildURLByLocation(latitude: Double, longitude: Double): URL {
        val sLatitude = "lat=$latitude"
        val sLongitude = "lon=$longitude"

        val builder = StringBuilder()
        builder.append(WEATHERBIT_PATH)
                .append(requestType)
                .append(sLatitude)
                .append("&")
                .append(sLongitude)
//                .append("&units=metric")
                .append(WEATHERBIT_API_KEY)
        try {
            url = URL(builder.toString())
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        println(url)
        return url
    }

    private fun buildURLByCityName(cityName: String): URL {
        val sCityName = "city=$cityName"

        val builder = StringBuilder()
        builder.append(WEATHERBIT_PATH)
                .append(requestType)
                .append(sCityName)
//                .append("&units=metric")
                .append(WEATHERBIT_API_KEY)
        try {
            url = URL(builder.toString())
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        println(url)
        return url
    }
}