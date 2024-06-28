package com.ilmiddin1701.chatapp.models

import java.io.Serializable

class Users: Serializable {
    var name: String? = null
    var uid: String? = null
    var photoUrl: String? = null

    constructor(name: String?, uid: String?, photoUrl: String?) {
        this.name = name
        this.uid = uid
        this.photoUrl = photoUrl
    }

    constructor()
}