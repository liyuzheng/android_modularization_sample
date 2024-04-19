package yz.l.data.common

import yz.l.common_room_db.eneities.RemoteEntity

/**
 * desc:
 * createed by liyuzheng on 2023/8/29 14:52
 */

fun RemoteEntity.toRemoteModel() = RemoteModel(name, next, prepend)
fun RemoteModel.toRemoteEntity() = RemoteEntity(name, next, prepend)