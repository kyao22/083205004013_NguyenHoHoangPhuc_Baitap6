package model

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TaskStorage(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("task_prefs", Context.MODE_PRIVATE)

    private val gson = Gson()

    fun saveTasks(tasks: List<Task>) {
        val json = gson.toJson(tasks)
        prefs.edit().putString("task_list", json).apply()
    }

    fun loadTasks(): List<Task> {
        val json = prefs.getString("task_list", null)
        return if (json != null) {
            val type = object : TypeToken<List<Task>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }
}

class TaskRepository(private val storage: TaskStorage) {
    fun getAllTasks(): List<Task> = storage.loadTasks()

    fun addTask(task: Task) {
        val tasks = storage.loadTasks().toMutableList()
        tasks.add(task)
        storage.saveTasks(tasks)
    }

    fun deleteTask(task: Task) {
        val tasks = storage.loadTasks().toMutableList()
        tasks.remove(task)
        storage.saveTasks(tasks)
    }
}
