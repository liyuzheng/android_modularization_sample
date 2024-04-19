package yz.l.common_room_db.eneities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * desc:
 * created by liyuzheng on 2023/9/6 17:19
 */
@Entity
data class PagingDiaryEntity(
    @PrimaryKey
    var dateTimestamp: Long = 0
)