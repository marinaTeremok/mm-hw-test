package components

import react.*
import react.dom.li
import models.*

interface HostelPropertyListItemProps :RProps{
    var hostel : Hostel
}

class HostelPropertyListItem(props: HostelPropertyListItemProps):RComponent<HostelPropertyListItemProps, RState>(props){
    override fun RBuilder.render(){
        val hostelItem = props.hostel

        li {
            hostelProperty(
                    hostelItem.name,
                    hostelItem.description,
                    hostelItem.isFeatured,
                    hostelItem.rating,
                    hostelItem.lowestPricePerNight,
                    hostelItem.location,
                    hostelItem.id,
                    hostelItem.imgUrl
            )
        }
    }
}

fun RBuilder.hostelPropertyListItem(hostel: Hostel) = child(HostelPropertyListItem::class){
    attrs.hostel = hostel
    attrs.key = hostel.id
}