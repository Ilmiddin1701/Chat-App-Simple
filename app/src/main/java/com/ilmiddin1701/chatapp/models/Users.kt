package com.ilmiddin1701.chatapp.models

class Users{
    var name:String? = null
    var uid:String? = null
    var photoUrl:String? = null

    constructor()
    constructor(name: String?, uid: String?, photoUrl: String?) {
        this.name = name
        this.uid = uid
        this.photoUrl = photoUrl
    }
}