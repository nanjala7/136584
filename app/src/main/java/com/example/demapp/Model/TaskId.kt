package com.example.demapp.Model

import androidx.annotation.NonNull
import com.google.firebase.firestore.Exclude

open class TaskId {
    @Exclude
    var TaskId: String? = null

    fun <T: TaskId> withId(@NonNull id: String): T {
        this.TaskId = id
        return this as T
    }
}