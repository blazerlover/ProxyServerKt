package weatherClient.urlBuilder.urlBuilderWeatherbit

import weatherServer.*
import weatherServer.request.ClientRequest

abstract class UrlBuilderIml {
    object F {
        fun getUrlBuilder(clientRequest: ClientRequest): UrlBuilderIml {
            return when (clientRequest.requestParams) {
                REQUEST_PARAM_BY_LOCATION -> {
                    when (clientRequest.requestType) {
                        REQUEST_TYPE_VALUE_CURRENT -> {
                            UrlBuilderByLocationCurrent()
                        }
                        REQUEST_TYPE_VALUE_HOURLY -> {
                            WEATHERBIT_HOURLY_REQUEST_TYPE
                        }
                        else -> {
                            WEATHERBIT_DAILY_REQUEST_TYPE
                        }
                    }
                    UrlBuilderByLocationCurrent()
                }
                else -> UrlBuilderByCity()
            }
        }
    }
}