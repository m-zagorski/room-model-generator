package com.zagorski.room.model.generator.annotations

@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.FIELD)
annotation class RoomName(val name: String = "")