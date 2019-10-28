package components

import react.*
import react.dom.*


interface HostelPropertyProps : RProps {
    var name: String
    var description: String
    var isFeatured: Boolean
    var rating: String
    var lowestPricePerNight: Double
    var location: String
    var id: String
    var imgUri: String
}

class HostelProperty(props:HostelPropertyProps) :RComponent <HostelPropertyProps, RState>(props){
    override fun RBuilder.render() {
        div("card-div ${if(props.isFeatured) """card-div-featured""" else """card-div-bg"""}") {
            div("grid-container"){
                div("card-img"){
                    img (src="http://${props.imgUri}"){ }
                }
                div("hostel-name"){
                    b{
                        +"${props.name}"
                    }
                    p{
                        +"${props.location}"
                    }
                }
                div("rate"){
                    div("flag"){
                        +"${props.rating}"
                    }
                }
                div("desc"){
                    div("truncate"){
                        +"${props.description}"
                    }
                }
                div("price"){
                    +"Prices from: ${props.lowestPricePerNight.toString()} EUR"
                }
            }
        }
    }
}

fun RBuilder.hostelProperty(name:String, description:String, isFeatured:Boolean, rating:String,
                            lowestPricePerNight:Double, location:String, id:String, imgUri: String) = child(HostelProperty::class){
    attrs.name = name
    attrs.description = description
    attrs.isFeatured = isFeatured
    attrs.rating = rating
    attrs.lowestPricePerNight = lowestPricePerNight
    attrs.location = location
    attrs.id = id
    attrs.imgUri = imgUri
}