package com.ilmiddin1701.chatapp.models

class MyMessage {
    var text: String? = null
    var toUID: String? = null
    var fromUID: String? = null
    var date: String? = null

    constructor()

    constructor(text: String?, toUID: String?, fromUID: String?, date: String?) {
        this.text = text
        this.toUID = toUID
        this.fromUID = fromUID
        this.date = date
    }
}