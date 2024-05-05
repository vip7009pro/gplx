package com.hnp.gplx600.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hnp.gplx600.api.ErpInterface

@Database(entities = [ErpInterface.Question::class],version = 1, exportSchema = false)
abstract class AppDataBase: RoomDatabase() {
abstract val questionDao: DaoInterface
}