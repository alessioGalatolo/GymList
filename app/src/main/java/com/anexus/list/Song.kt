package com.anexus.list

import java.net.URI

data class Song(val title: String,val artist: String, val image: URI?, val length: Int, val id: Long)