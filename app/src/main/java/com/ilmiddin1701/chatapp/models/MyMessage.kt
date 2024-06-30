package com.ilmiddin1701.chatapp.models

class MyMessage {
    var text: String? = null
    var toUID: String? = null
    var toUserPhotoUrl: String? = null
    var fromUID: String? = null
    var fromUserPhotoUrl: String? = null
    var date: String? = null

    constructor()

    constructor(text: String?, toUID: String?, toUserPhotoUrl: String?, fromUID: String?, fromUserPhotoUrl: String?, date: String?) {
        this.text = text
        this.toUID = toUID
        this.toUserPhotoUrl = toUserPhotoUrl
        this.fromUID = fromUID
        this.fromUserPhotoUrl = fromUserPhotoUrl
        this.date = date
    }
}