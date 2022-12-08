package com.example.drinkr.ui.agenda

//val date: String, val time: String, val drink: String, val type: String, val amount: String){}

data class DrinkModel(private var drinkDate: String,private var drinkTime: String,
                      private var drinkTitle: String, private var drinkType: String,
                      private var drinkAmount: String) {

    // Getter and Setter
    fun getDrink_name(): String {
        return drinkTitle
    }

    fun setDrink_name(drinkTitle: String) {
        this.drinkTitle = drinkTitle
    }

    fun getDrink_amount(): String {
        return drinkAmount
    }

    fun setDrink_amount(drinkAmount: String) {
        this.drinkAmount = drinkAmount
    }

    fun getDrink_type(): String {
        return drinkType
    }

    fun setDrink_type(drinkType: String) {
        this.drinkType = drinkType
    }

    fun getDrink_time(): String {
        return drinkTime
    }

    fun setDrink_time(drinkTime: String) {
        this.drinkTime = drinkTime
    }

    fun getDrink_date(): String {
        return drinkDate
    }

    fun setDrink_date(drinkDate: String) {
        this.drinkDate = drinkDate
    }
}
