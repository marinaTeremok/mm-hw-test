package components

import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.ul
import models.*

interface HostelPropertyListProps : RProps {
    var hostels: List<Hostel>
}

class HostelPropertyList(props: HostelPropertyListProps): RComponent<HostelPropertyListProps,RState>(props){

    override fun RBuilder.render(){
        ul ("card-list"){
            props.hostels.map { hostel ->
                hostelPropertyListItem(hostel)
            }
        }
    }
}

fun RBuilder.hostelPropertyList(hostels: List<Hostel>) = child(HostelPropertyList::class){
    attrs.hostels = hostels
}
