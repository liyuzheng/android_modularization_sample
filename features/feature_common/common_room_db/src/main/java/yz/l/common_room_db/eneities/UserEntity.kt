package yz.l.common_room_db.eneities

import androidx.room.Entity

/**
 * desc:
 * createed by liyuzheng on 2023/8/26 15:48
 */
@Entity(primaryKeys = ["uid", "remoteName"])
data class UserEntity(
    var uid: String,
    var remoteName: String = "",
    val name: String = "",
    val gender: Int = 1
)