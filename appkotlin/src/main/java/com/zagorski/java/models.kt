package com.zagorski.java

import com.zagorski.room.model.generator.annotations.*

@RoomEntity
@RoomForeignKeys([
    (RoomForeignKey("FirstEntity", ["uid"], ["ownerId"])),
    RoomForeignKey("FirstEntity", ["bcd"], ["ohNo"])])
data class Cow(@RoomPrimaryKey val name: String,
               @RoomName("srypeHype") val type: String,
               @RoomName("meh") val size: Long,
               @RoomEmbedded val foo: FirstEntity,
               @RoomIgnore val ignoreMe: String)



@RoomEntity
data class FirstEntity(val id: String,
                       @RoomName("roomName") val name: String)

@RoomEntity
data class SecondEntity(@RoomPrimaryKey val something: String)

data class NotAnEntity(val id: String)
