package yz.l.common_room_db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import yz.l.common_room_db.eneities.RemoteEntity

@Dao
interface RemoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAsync(remoteKeyEntity: RemoteEntity)

    @Query("SELECT * FROM RemoteEntity WHERE name = :remoteName ")
    suspend fun getRemoteKeysAsync(remoteName: String): RemoteEntity?

    @Query("DELETE FROM RemoteEntity WHERE name = :remoteName")
    suspend fun clearRemoteKeys(remoteName: String)
}