package yz.l.common_room_db.eneities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * desc:
 * created by liyuzheng on 2023/9/6 16:03
 */
@Entity
data class DiaryEntity(
    @PrimaryKey
    var dateTimestamp: Long = 0,
    var text: String = ""
)