package com.example.indianpincodeapp.Model

data class State(
    var state:String? = null,
    var districts:MutableList<String>? = null
){
    override fun toString(): String {
        return state.toString()
    }
}
