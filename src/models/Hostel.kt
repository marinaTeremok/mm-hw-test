package models

data class Hostel (val name: String, val description: String, val isFeatured: Boolean,
                   val rating: String, val lowestPricePerNight: Double, val location: String, val id: String, val imgUrl: String)