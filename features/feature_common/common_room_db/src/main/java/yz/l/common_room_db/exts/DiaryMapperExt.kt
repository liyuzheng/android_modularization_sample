package yz.l.common_room_db.exts

import yz.l.common_room_db.eneities.DiaryEntity
import yz.l.common_room_db.eneities.PagingDiaryEntity
import yz.l.data.diary.DiaryModel
import yz.l.data.diary.PagingDiaryModel

/**
 * desc:
 * created by liyuzheng on 2023/9/6 16:13
 */
fun DiaryModel.toDiaryEntity() = DiaryEntity(dateTimestamp, text)
fun DiaryEntity.toDiaryModel() = DiaryModel(dateTimestamp, text)

fun PagingDiaryModel.toPagingDiaryEntity() = PagingDiaryEntity(dateTimestamp)
fun PagingDiaryEntity.toPagingDiaryModel() = PagingDiaryModel(dateTimestamp)