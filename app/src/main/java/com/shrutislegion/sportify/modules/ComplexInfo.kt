package com.shrutislegion.sportify.modules

data class ComplexInfo(
    var complexName: String? = null,
    var typeOfSport: String? = null,
    var pricePerHour: Int = 0,
    var numberOfCourts: String? = null,
    var location: String? = null,
    var imageUri: String?=null,
    var phone: String?=null,
    var description: String?=null,
    var UId: String?=null,
    var emailId: String?=null,
    var complexRating: Float = 0F,
    var addedComplexAt: String? = null
)
