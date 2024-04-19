package yz.l.common_room_db.eneities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RemoteEntity(
    @PrimaryKey
    var name: String = "",
    var next: String = "",
    var prepend: String = ""
)