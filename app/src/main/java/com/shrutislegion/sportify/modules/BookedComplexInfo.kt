package com.shrutislegion.sportify.modules

data class BookedComplexInfo(
    var complexName: String? = null,
    var typeOfSport: String? = null,
    var pricePerHour: String? = null,
    var numberOfCourts: String? = null,
    var location: String? = null,
    var imageUri: String?=null,
    var phone: String?=null,
    var description: String?=null,
    var UId: String?=null,
    var emailId: String?=null,
    var bookedHours: MutableList<Int>? = mutableListOf<Int>()
)
