package com.task.noteapp.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.framework.ui.model.BasicListItem
import com.task.noteapp.db.entity.NoteEntity.Companion.TABLE_NAME
import kotlinx.parcelize.Parcelize

@Entity(tableName = TABLE_NAME)
@Parcelize
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "title") var title: String?,
    @ColumnInfo(name = "description") var description: String?,
    @ColumnInfo(name = "image_url") var imageUrl: String?,
    @ColumnInfo(name = "created_date") val createdDate: String?,
    @ColumnInfo(name = "is_edited") var isEdited: Boolean = false
) : Parcelable {
    companion object {
        const val TABLE_NAME = "notes"
    }

    fun toViewEntity(): ViewEntity {
        return ViewEntity(
            uid, title, description, imageUrl, createdDate, isEdited
        )
    }

    data class ViewEntity(
        val uid: Int = 0,
        val title: String?,
        val description: String?,
        val imageUrl: String?,
        val createdDate: String?,
        val isEdited: Boolean = false
    ) : BasicListItem<ViewEntity> {
        override fun areItemsTheSame(oldItem: ViewEntity, newItem: ViewEntity): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ViewEntity, newItem: ViewEntity): Boolean {
            return oldItem == newItem
        }
    }
}