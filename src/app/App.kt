package app

import react.*
import react.dom.*
import components.*
import domains.*
import models.*
import utils.*
import kotlin.browser.window
import kotlinext.js.jsObject
import kotlin.js.Promise


interface AppState : RState {
    var hostels: List<Hostel>
    var errorMessage: String
    var loading: Boolean
    var isError: Boolean
    var secondsElapsed: Int
}

//My original idea was to use coroutines to fetch data from api (see getProperties in HostelPropertService.kt) but somehow I couldnt make it working ptoperly with kotlin-react
// plus apparently Serialization doesnt work with kotlin-react (this is an open ticket https://youtrack.jetbrains.com/issue/CRKA-84)
// so couldnt use window.fetch at all and had to wrap Axios to get data
// and you should need "npm install axios --save" in advance in the project folder
@JsModule("axios")
external fun <T> axios(config: AxiosConfigSettings): Promise<AxiosResponse<T>>

//add enhanced typing for axios
external interface AxiosConfigSettings {
    var url: String
    var method: String  //get, post, head, delete, put, patch and so on.  The default is get if you not set any info
    var baseUrl: String
    var timeout: Number
    var data: dynamic
    var transferRequest: dynamic
    var transferResponse: dynamic
    var headers: dynamic
    var params: dynamic
    var withCredentials: Boolean
    var adapter: dynamic
    var auth: dynamic
    var responseType: String //defauls is json
    var xsrfCookieName: String
    var xsrfHeaderName: String
    var onUploadProgress: dynamic
    var onDowndloadProgress: dynamic
    var maxContentLength: Number
    var validateStatus: (Number) -> Boolean
    var maxRedirects: Number
    var httpAgent: dynamic
    var httpsAgent: dynamic
    var proxy: dynamic
    var cancelToken: dynamic
}

external interface AxiosResponse<T> {
    val data: T
    val status: Number
    val statusText: String
    val headers: dynamic
    val config: AxiosConfigSettings
}

//just for this search response data
external interface HostelWrapper {
    val properties: List<PropertyHostel>
    val location: PropertyLocation
    val filterData: FilterData
    val pagination: Pagination
}

external interface Stats{
    val response: String
}

class App : RComponent<RProps, AppState>() {
    override fun AppState.init(){
        hostels =  emptyList()
        loading = true
        errorMessage = ""
        isError = false
        secondsElapsed = 0
    }

    private var conversionRate: Double = 7.55

    //This fun should actually call HostelPropertService.getProperties...
    private fun getHostelProperties() {
        val config: AxiosConfigSettings = jsObject {
            url = "https://gist.githubusercontent.com/ruimendesM/bf8d095f2e92da94938810b8a8187c21/raw/70b112f88e803bf0f101f2c823a186f3d076d9e6/properties.json"
            timeout = 100000000
        }

        axios<HostelWrapper>(config).then{ response ->
            val location = response.data.location.city.name.concat(", ").concat(response.data.location.city.country)
            val pr: MutableList<Hostel> = mutableListOf()

            //ugly hack here to work around a known problem with iterating through json arrays
            val sPropertyList = JSON.stringify(response.data.properties)
            val hostelList = JSON.parse<Array<PropertyHostel>>(sPropertyList)
            for(item in hostelList){
                val rating = calculateRating(item.overallRating?.overall, item.overallRating?.numberOfRatings)
                val lowestPricePerNightEur = convertLowestPricePerNightToEur(item.lowestPricePerNight?.value, item.lowestPricePerNight?.currency, conversionRate)
                val imgSrc = if (item.images != null) buildImgUrl(JSON.stringify(item.images)) else ""
                pr.add(Hostel(item.name, item.overview,item.isFeatured, rating, lowestPricePerNightEur,location,item.id, imgSrc ))
            }
            setState {
                hostels = pr
                loading = false
                isError = false
            }
            window.clearInterval(timerID!!)
            sendStats("load-details", state.secondsElapsed)
        }.catch { error ->
            setState {
                loading = false
                errorMessage = error.message ?: ""
                isError = true
            }
            window.clearInterval(timerID!!)
            console.log(error.message)
            console.log(error)
        }
    }

    private fun buildImgUrl(images: String): String{
        val imgList = JSON.parse<Array<PropertyImage>>(images)
        return imgList[0]?.prefix.concat(imgList[0]?.suffix)
    }

    //And this fun should also call HostelPropertService....
    private fun sendStats(action:String?, duration: Int=0){
        val hostUrl = "https://gist.githubusercontent.com/ruimendesM/cb9313c4d4b3434975a3d7a6700d1787/raw/02d17a4c542ac99fe559df360cbfe9ba24dbe6be/stats?action="
        val sAction = action?: "load-details"
        val sDuration = "&duration=".concat((duration*10).toString())
        console.log(duration)
        val config: AxiosConfigSettings = jsObject {
            url = hostUrl.concat(sAction).concat(sDuration)
            timeout = 10000
        }
        axios<Stats>(config).then{ response ->
            console.log(response.status)
            console.log(response.data)
        }.catch { error ->
            console.log(error.message)
            console.log(error)
        }
    }

    var timerID: Int? = null

    override fun componentDidMount() {
        timerID = window.setInterval({
            setState { secondsElapsed += 1 }
        }, 10)
        getHostelProperties()
    }

    override fun componentWillUnmount() {
        window.clearInterval(timerID!!)
    }

    override fun RBuilder.render() {
        div("${if(state.loading) """show-loader loader""" else """hide-loader"""}"){

        }
        div("hostel-list"){
            hostelPropertyList(state.hostels)
        }
        div("${if(state.isError) """show-error""" else """hide-error"""}"){
            p{
                b{
                    "Sorry something went wrong please refresh"
                }
            }
        }
    }
}

fun RBuilder.app() = child(App::class) {}
