package com.ilmiddin1701.chatapp.models

class MyMessage {
    var id: String? = null
    var text: String? = null
    var date: String? = null
    var fromUID: String? = null
    var toUID: String? = null

    constructor(id: String?, text: String?, date: String?, fromUID: String?, toUID: String?) {
        this.id = id
        this.text = text
        this.date = date
        this.fromUID = fromUID
        this.toUID = toUID
    }

    constructor()
}