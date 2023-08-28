package yz.l.common_room_db.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * desc:
 * createed by liyuzheng on 2023/8/28 15:34
 */
val MIGRATION_1_2: Migration = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        //sqlLite 数据库升级修改语句执行
        //database.execSQL("ALTER TABLE localCacheMusic ADD COLUMN time Int NOT NULL default 0 ;")
    }
}