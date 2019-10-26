package domains

//@Serializable
data class HostelWrapper (val properties: List<PropertyHostel>, val location: PropertyLocation, val filterData: FilterData, val pagination: Pagination)

data class PropertyHostel (val id:String, val name: String, val overallRating:RatingRaw,val isFeatured:Boolean, val type:String,
                           val lowestPricePerNight:PricePerNight,
                           val overview:String, val images:List<PropertyImage>)

data class PropertyLocation (val city: PropertyCity, val region: String)
data class PropertyCity (val id:String, val name:String, val country:String, val idCountry:String)
data class Pagination (val next:String, val prev:String, val numberOfPages:Int, val totalNumberOfItems :Int)
data class PricePerNight(val value:String, val currency:String)
data class FilterData(val highestPricePerNight: PricePerNight, val lowestPricePerNight:PricePerNight)
data class RatingRaw (val overall:String, val numberOfRatings: String)
data class PropertyImage(val prefix:String, val suffix: String)