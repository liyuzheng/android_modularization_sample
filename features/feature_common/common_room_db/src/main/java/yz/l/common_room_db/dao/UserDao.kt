package yz.l.common_room_db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import yz.l.common_room_db.eneities.UserEntity

/**
 * desc:
 * createed by liyuzheng on 2023/8/26 16:14
 */
@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAsync(userEntity: UserEntity)

    @Update
    suspend fun updateAsync(userEntity: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveSync(userEntity: UserEntity)

    @Delete
    suspend fun deleteAsync(userEntity: UserEntity)

    @Query("SELECT * FROM UserEntity WHERE remoteName=:remoteName")
    suspend fun getUserListAsync(remoteName: String): List<UserEntity>

    @Query("SELECT * FROM UserEntity WHERE remoteName=:remoteName")
    fun getUserListFlow(remoteName: String): Flow<List<UserEntity>>

    @Query("SELECT * FROM UserEntity WHERE uid=:uid")
    suspend fun getUserAsync(uid: String): UserEntity?

    @Query("SELECT * FROM UserEntity WHERE uid=:uid")
    fun getUserSync(uid: String): UserEntity?

    @Query("SELECT * FROM UserEntity WHERE uid=:uid")
    fun getUserFlow(uid: String): Flow<UserEntity?>
}