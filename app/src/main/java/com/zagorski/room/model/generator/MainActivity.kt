package com.zagorski.room.model.generator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zagorski.generator.R
import com.zagorski.java.Cow
import com.zagorski.java.FirstEntity
import com.zagorski.java.SecondEntity
import com.zagorski.room.model.generator.annotations.RoomEntities

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}


@RoomEntities
data class RoomEntities(val firstEntity: FirstEntity,
                        val secondEntity: SecondEntity,
                        val cow: Cow)
