package yz.l.common_room_db.exts

import yz.l.common_room_db.eneities.UserEntity
import yz.l.data_user.UserModel

/**
 * desc:
 * createed by liyuzheng on 2023/8/28 13:29
 */
fun UserModel.toEntity(remoteName: String) =
    UserEntity(uid = this.uid, remoteName = remoteName, name = this.name, gender = gender)

fun UserEntity.toUserModel() = UserModel(uid, name, gender)