package yz.l.common_room_db

import android.annotation.SuppressLint
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import yz.l.common_room_db.dao.LotteryDao
import yz.l.common_room_db.dao.RemoteDao
import yz.l.common_room_db.dao.UserDao
import yz.l.common_room_db.eneities.LotteryEntity
import yz.l.common_room_db.eneities.RemoteEntity
import yz.l.common_room_db.eneities.UserEntity

/**
 * desc:
 * createed by liyuzheng on 2023/8/26 15:47
 */
@SuppressLint("StaticFieldLeak")
object RoomDB {
    private lateinit var context: Context

    //application初始化时调用,如果采用其他的单例方式需要每次传入context，使用比较麻烦。
    fun init(context: Context) {
        this.context = context.applicationContext
    }

    val INSTANCE: AppDataBase by lazy { Holder.holder }

    private object Holder {
        val holder by lazy {
            Room.databaseBuilder(
                context.applicationContext,
                AppDataBase::class.java,
                "android_room_db.db" //数据库名称
            )
                .allowMainThreadQueries() //允许启用同步查询，即：允许主线程可以查询数据库，这个配置要视情况使用，一般不推荐同步查询
                .fallbackToDestructiveMigration()//如果数据库升级失败了，删除重新创建
                .enableMultiInstanceInvalidation()//多进程查询支持
//              .addMigrations(MIGRATION_1_2) //数据库版本升级，MIGRATION_1_2为要执行的表格执行sql语句，例如database.execSQL("ALTER TABLE localCacheMusic ADD COLUMN time Int NOT NULL default 0 ;")
                .build()
        }
    }
}

@Database(
    entities = [UserEntity::class, RemoteEntity::class, LotteryEntity::class],
    version = 1, exportSchema = false
)
@TypeConverters(value = [LocalTypeConverter::class])
abstract class AppDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun remoteDao(): RemoteDao
    abstract fun lotteryDao(): LotteryDao
}
