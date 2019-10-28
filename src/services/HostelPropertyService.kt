package services

//import domains.*
//import kotlinext.js.jsObject
//import kotlin.js.Promise
//import kotlin.coroutines.*
//import org.w3c.fetch.*
//import kotlin.browser.window
//import kotlin.js.json
//
////window.fetch(Request("https://gist.githubusercontent.com/ruimendesM/bf8d095f2e92da94938810b8a8187c21/raw/70b112f88e803bf0f101f2c823a186f3d076d9e6/properties.json")).then {
////    response-> response.text().then {
////    json ->
////            //JSON.parse<HostelWrapper>(json)
////            console.log(JSON.parse<HostelWrapper>(json))
////    }
////}
//
//
//suspend fun <T> getProperties(url: String, method: String = "GET", body: dynamic = null): T {
//    val res = window.fetch(url, object : RequestInit {
//        override var method: String? = method
//        override var body: dynamic = body
//        override var headers: dynamic = json("Accept" to "application/json")
//    }).await()
//
//    return if (res.ok) res.json().await() as T else throw "Request failed"
//}

////usage
//launch {
//    val properties = getProperties<HostelWrapper>("https://gist.githubusercontent.com/ruimendesM/bf8d095f2e92da94938810b8a8187c21/raw/70b112f88e803bf0f101f2c823a186f3d076d9e6/properties.json")
//    console.log(properties)
//}

