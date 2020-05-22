package weatherClient.urlBuilder.urlBuilderWeatherbit

import weatherServer.WEATHERBIT_API_KEY
import weatherServer.WEATHERBIT_CURRENT_REQUEST_TYPE
import weatherServer.WEATHERBIT_PATH
import java.lang.StringBuilder
import java.net.MalformedURLException
import java.net.URL

class UrlBuilderByLocationCurrent: UrlBuilderIml() {

    private fun buildURLByLocation(latitude: Double, longitude: Double): URL {
        lateinit var url: URL
        val urlLatitude = "lat=$latitude"
        val urlLongitude = "lon=$longitude"

        val builder = StringBuilder()
        builder.append(WEATHERBIT_PATH)
                .append(WEATHERBIT_CURRENT_REQUEST_TYPE)
                .append(urlLatitude)
                .append("&")
                .append(urlLongitude)
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