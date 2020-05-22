package weatherClient.urlBuilder.urlBuilderWeatherbit

import weatherServer.WEATHERBIT_API_KEY
import weatherServer.WEATHERBIT_CURRENT_REQUEST_TYPE
import weatherServer.WEATHERBIT_PATH
import java.lang.StringBuilder
import java.net.MalformedURLException
import java.net.URL

class UrlBuilderByCity: UrlBuilderIml() {

    private fun buildURLByCityName(requestType: String, cityName: String): URL {
        lateinit var url: URL
        val sCityName = "city=$cityName"

        val builder = StringBuilder()
        builder.append(WEATHERBIT_PATH)
                .append(WEATHERBIT_CURRENT_REQUEST_TYPE)
                .append(sCityName)
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