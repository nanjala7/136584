package com.example.demapp

class DataClass {
     internal var imageURL: String? = null
     internal var caption: String? = null

    constructor() {}

    fun getImageURL(): String? {
        return imageURL
    }

    fun setImageURL(imageURL: String?) {
        this.imageURL = imageURL
    }

    fun getCaption(): String? {
        return caption
    }

    fun setCaption(caption: String?) {
        this.caption = caption
    }

    constructor(imageURL: String?, caption: String?) {
        this.imageURL = imageURL
        this.caption = caption
    }

}