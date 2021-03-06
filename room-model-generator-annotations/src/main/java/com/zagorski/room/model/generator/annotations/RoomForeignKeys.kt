package com.zagorski.room.model.generator.annotations


@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS)
annotation class RoomForeignKeys(val foreignKeys: Array<RoomForeignKey> = [])