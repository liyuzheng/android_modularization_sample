package yz.l.data_user

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import yz.l.common_room_db.RoomDB
import yz.l.common_room_db.dao.UserDao

/**
 * desc:
 * createed by liyuzheng on 2023/8/28 13:18
 */
object UserDB {
    private val dao: UserDao by lazy {
        RoomDB.INSTANCE.userDao()
    }

    suspend fun saveUserAsync(user: UserModel) {
        dao.saveAsync(user.toEntity("test"))
    }

    suspend fun updateUserAsync(user: UserModel) {
        dao.updateAsync(user.toEntity("test"))
    }

    fun saveUserSync(user: UserModel) {
        dao.saveSync(user.toEntity("test"))
    }

    suspend fun getUserAsync(uid: String): UserModel? {
        return dao.getUserAsync(uid)?.toUserModel()
    }

    fun getUserSync(uid: String): UserModel? {
        return dao.getUserSync(uid)?.toUserModel()
    }

    fun getUserFlow(uid: String): Flow<UserModel?> {
        return dao.getUserFlow(uid).map {
            it?.toUserModel()
        }
    }
}
