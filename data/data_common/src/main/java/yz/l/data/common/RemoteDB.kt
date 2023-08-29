package yz.l.data.common

import yz.l.common_room_db.RoomDB
import yz.l.common_room_db.dao.RemoteDao
import yz.l.common_room_db.eneities.RemoteEntity

/**
 * desc:
 * createed by liyuzheng on 2023/8/29 14:51
 */
object RemoteDB {
    private val dao: RemoteDao by lazy {
        RoomDB.INSTANCE.remoteDao()
    }

    suspend fun getRemoteKeysAsync(remoteName: String) =
        dao.getRemoteKeysAsync(remoteName)?.toRemoteModel()

    suspend fun insertAsync(remoteKeyEntity: RemoteEntity) {
        dao.insertAsync(remoteKeyEntity)
    }
}