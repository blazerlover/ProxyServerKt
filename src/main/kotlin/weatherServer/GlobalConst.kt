package weatherServer

const val OPEN_WEATHER_MAP_PATH = "https://api.openweathermap.org/data/2.5"
const val OPEN_WEATHER_MAP_API_KEY = "&appid=ece86214d27a8d2c3a6f208489f5b390"
const val OPEN_WEATHER_MAP_CURRENT_REQUEST_TYPE = "/weather?"
const val OPEN_WEATHER_MAP_HOURLY_REQUEST_TYPE = "/onecall?"
const val OPEN_WEATHER_MAP_HOURLY_REQUEST_TYPE_ = "/forecast/hourly?"
const val OPEN_WEATHER_MAP_DAILY_REQUEST_TYPE = "/onecall?"
const val OPEN_WEATHER_MAP_DAILY_REQUEST_TYPE_ = "climate/month?"

const val WEATHERBIT_PATH = "http://api.weatherbit.io/v2.0"
const val WEATHERBIT_API_KEY = "&key=286754432c094a92a43d63c8d5161910"
const val WEATHERBIT_CURRENT_REQUEST_TYPE ="/current?"
const val WEATHERBIT_HOURLY_REQUEST_TYPE ="/forecast/hourly?"
const val WEATHERBIT_DAILY_REQUEST_TYPE ="/forecast/daily?"

const val REQUEST_PARAM_BY_LOCATION = "bylocation"
const val REQUEST_PARAM_BY_CITY_NAME = "bycityname"

const val LATITUDE_KEY: String = "latitude"
const val LONGITUDE_KEY: String = "longitude"
const val CITY_NAME_KEY: String = "city"
const val REQUEST_TYPE_KEY: String = "type"

const val CURRENT: String = "current"
const val HOURLY: String = "hourly"
const val DAILY: String = "daily"