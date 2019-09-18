package com.example.stripedemokotlin.models

import java.io.Serializable

class CardPojo : Serializable {

    var id: String? = null
    var name: String? = null
    var last4: String? = null
    var `object`: String? = null
    var address_city: String? = null
    var address_country: String? = null
    var address_line1: String? = null
    var address_line2: String? = null
    var address_state: String? = null
    var address_zip: String? = null
    var exp_month: Int = 0
    var exp_year: Int = 0
}
