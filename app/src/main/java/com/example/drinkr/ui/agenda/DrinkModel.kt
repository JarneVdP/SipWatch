package com.example.drinkr.ui.agenda

//val date: String, val time: String, val drink: String, val type: String, val amount: String){}

data class DrinkModel(private var drinkDate: String,private var drinkTime: String,
                      private var drinkTitle: String, private var drinkType: String,
                      private var drinkAmount: String) {
    fun getDrink_name(): String {
        return drinkTitle
    }

    fun getDrink_amount(): String {
        return drinkAmount
    }

    fun getDrink_type(): String {
        return drinkType
    }

    fun getDrink_time(): String {
        return drinkTime
    }

    fun getDrink_date(): String {
        return drinkDate
    }
}
