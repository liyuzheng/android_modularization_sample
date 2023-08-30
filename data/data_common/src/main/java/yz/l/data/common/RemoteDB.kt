package yz.l.data.common

import yz.l.common_room_db.RoomDB
import yz.l.common_room_db.dao.RemoteDao

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

    suspend fun insertAsync(remoteModel: RemoteModel) {
        dao.insertAsync(remoteModel.toRemoteEntity())
    }
}