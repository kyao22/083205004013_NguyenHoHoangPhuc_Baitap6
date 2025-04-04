package com.example.cau_1

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import model.Task
import model.TaskRepository

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {
    private val _tasks = MutableStateFlow(repository.getAllTasks())
    val tasks = _tasks.asStateFlow()

    fun addTask(title: String, description: String) {
        val newTask = Task(id = _tasks.value.size + 1, title = title, description = description)
        repository.addTask(newTask)
        _tasks.value = repository.getAllTasks()
    }

    fun deleteTask(task: Task) {
        repository.deleteTask(task)
        _tasks.value = repository.getAllTasks()
    }
}