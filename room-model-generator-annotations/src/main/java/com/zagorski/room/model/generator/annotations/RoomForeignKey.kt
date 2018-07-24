package com.zagorski.room.model.generator.annotations


annotation class RoomForeignKey(val entityClassName: String, val parentColumns: Array<String>, val childColumns: Array<String>)