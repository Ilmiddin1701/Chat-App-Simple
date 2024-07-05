package com.ilmiddin1701.chatapp.models

import java.io.Serializable

class MyMessage: Serializable {
    var text: String? = null
    var toUID: String? = null
    var fromUID: String? = null
    var image: String? = null
    var date: String? = null

    constructor()

    constructor(text: String?, toUID: String?, fromUID: String?, date: String?) {
        this.text = text
        this.toUID = toUID
        this.fromUID = fromUID
        this.date = date
    }

    constructor(text: String?, toUID: String?, fromUID: String?, image: String?, date: String?) {
        this.text = text
        this.toUID = toUID
        this.fromUID = fromUID
        this.image = image
        this.date = date
    }
}