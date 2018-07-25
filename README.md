# Room model generator  [ ![Download](https://api.bintray.com/packages/mateusz/maven/room-model-generator-annotations/images/download.svg?version=0.0.1-beta) ](https://bintray.com/mateusz/maven/room-model-generator-annotations/0.0.1-beta/link)

Generate [Room](https://developer.android.com/topic/libraries/architecture/room) entities from plain java/kotlin models located in java-library module.


## Problem

If you tend to use plain java/kotlin module for you'r logic, you might have created interface for database to be implemented and provided
from android code and while doing that you were probably thinking how to solve entities issue, you have model defined in non android module
and you need android annotations to use it in database. This would require writing the same model in android module with proper annotations,
ewentually you could create model with `PrimaryKey` and add all model logic with `Embedded` annotation, in kotlin this would shorten to 

```kotlin
data class KotlinModel(val id: String, val name:String)

@RoomEntity
data class RoomEntity(val kotlinModel: KotlinModel, @PrimaryKey val id: String = kotlinModel.id)
```

But still if you would like to rename or ignore some fields from you'r model you would have to probably write the same model again,
also add some extensions to transform model into entity and entity back into model.

What if this all could be done for you? If you could annotate your non-android model with `room-similar` annotations and receive
proper `entity`? With all the extension methods written?

## Usage

Create your non-android models like you did before, but annotate them with `RoomEntity`, optionally you can annotate all fields, or add 
other `room` alike annotations - for more info check another chapter `Supported annotations`. When you have your models ready simply 
create data class containing all the classes annotated with `RoomEntities`

```kotlin
@RoomEntity
data class Cow(val id: String)

@RoomEntities
data class Entities(val cow:Cow)
```

Processor will generate automatically `CowRoom` class with `Room` annotations also extension proper extension methods are going to be 
created 

```kotlin
fun Cow.entity() = CowRoom(id)

fun CowRoom.model() = Cow(id))

fun List<Cow>.entities() = map { it.entity() }

fun List<CowRoom>.models() = map { it.model() }
```

## Supported annotations

### Entity -> RoomEntity

All non-android models have to be annotated with `@RoomEntity` before beign added to `@RoomEntities` class.
Optionally you can define `tableName` if you would like your database table name to be different

```kotlin
@RoomEntity("users")
data class User(val id: String)
```

### PrimaryKey -> RoomPrimaryKey

In an entity one field has to be either annotated with `@RoomPrimaryKey` or eventually called `id`. If there will be field annotated with
`@RoomPrimaryKey` and other field called `id` that id will not be taken into entity generation. It only will be if there are no
present annotations.
Optionally you can mark `primary key` as auto generated

```kotlin
data class User(@PrimaryKey(true) val primary: String)

data class User(val id: String)
```

### ColumnInfo -> RoomName

Field in model class can also be annotated with `@RoomName` if you would like to change its name.

```kotlin
data class User(@RoomName("user_name") val name: String)
```

### Ignore -> RoomIgnore

If you would like to skip some fields from adding to database you can annotate them with `@RoomIgnore` 

```kotlin
data class User(@RoomIgnore val avatarFile: File)
```

### Embedded -> RoomEmbedded

Works just the same as rooms embedded annotation, ie signals that nested fields (i.e. fields of the annotated field's class) can be referenced directly in the SQL queries
Keep in mind that currently it does not transform `@RoomNames` and other annotations from inner class to room annotations.

```kotlin
data class Inner(val name: String)

data class Outer(@RoomEmbedded val inner: Inner)
```

### ForeignKeys -> RoomForeignKeys

If you would like to add some relations you can mark your model with `@RoomForeignKey` just like you would with standard room `ForeignKey`

```kotlin
@RoomEntity
@ForeignKey([ForeignKey]
data class User(val id: String)
```

### ForeignKey -> RoomForeignKey

Strictly connected with `ForeignKeys`. There is one small change to default room implementation, you do not specify class directly
but instead its name (this will be addressed in future releases). So you have to specify: `entityClassName`, array of `parent columns` and array of `child columns`.

```kotlin
@RoomEntity
@RoomForeignKeys([(RoomForeignKey("Cow", ["cowId"], ["Id"]))
data class User(val id: String, val cowId: String)
```

## To be done

Actually `indexes` are not supported. They will be supported in upcoming release.

## Download

Add as gradle dependency:
In your non-android module all you need is:
```gradle
compileOnly "com.zagorski:room-model-generator-annotations:$roomGeneratorVersion"
```
And in your android, assuming you are using kotlin:
```gradle
compileOnly "com.zagorski:room-model-generator-annotations:$roomGeneratorVersion"
kapt "com.zagorski:room-model-generator-processor:$roomGeneratorVersion"
```

# License

```
Copyright 2018 Mateusz Zagórski.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
