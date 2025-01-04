package com.ug.taskaya.data.repositories

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.ug.taskaya.data.entities.TaskEntity
import com.ug.taskaya.data.entities.UserEntity
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class Repository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val db: FirebaseFirestore
) {


    private val usersCollection = db.collection("users")
    private var taskListener: ListenerRegistration? = null

    private var labelsListener: ListenerRegistration? = null

    fun listenToLabels(
        onLabelsChanged: (Result<List<String>>) -> Unit
    ) {
        // Remove any existing listener to avoid duplication
        labelsListener?.remove()

        labelsListener = usersCollection.whereEqualTo("email", getCurrentUserEmail())
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    onLabelsChanged(Result.failure(error))
                    return@addSnapshotListener
                }

                if (snapshot != null && !snapshot.isEmpty) {
                    val userDocument = snapshot.documents.first()
                    val labels = userDocument["labels"] as? List<String> ?: emptyList()
                    onLabelsChanged(Result.success(labels))
                } else {
                    onLabelsChanged(Result.failure(Exception("User not found or labels empty")))
                }
            }
    }


    suspend fun removeLabelFromUser(label: String): Result<Unit> {

        return try {

            val userSnapshot = usersCollection.whereEqualTo("email", getCurrentUserEmail()).get().await()
            if (userSnapshot.isEmpty) {
                Result.failure(Exception("User not found"))
            } else {
                val userDocument = userSnapshot.documents.first()
                val existingLabels = userDocument["labels"] as? List<String> ?: emptyList()
                if (!existingLabels.contains(label)) {
                    Result.failure(Exception("Label not found"))
                } else {
                    val updatedLabels = existingLabels.filterNot { it == label }
                    userDocument.reference.update("labels", updatedLabels).await()
                    Result.success(Unit)
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun addLabel(label: String): Result<Unit> {

        return try {
            val userSnapshot = usersCollection.whereEqualTo("email", getCurrentUserEmail()).get().await()
            if (userSnapshot.isEmpty) {
                Result.failure(Exception("User not found"))
            } else {
                val userDocument = userSnapshot.documents.first()
                val existingLabels = userDocument["labels"] as? List<String> ?: emptyList()
                if (existingLabels.contains(label)) {
                    Result.failure(Exception("Label already exists"))
                } else {
                    val updatedLabels = existingLabels + label
                    userDocument.reference.update("labels", updatedLabels).await()
                    Result.success(Unit)
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun listenToTasks(
        onTasksChanged: (Result<List<TaskEntity>>) -> Unit
    ) {
        // Remove any existing listener to avoid duplication
        taskListener?.remove()

        taskListener = usersCollection.whereEqualTo("email", getCurrentUserEmail())
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    onTasksChanged(Result.failure(error))
                    return@addSnapshotListener
                }

                if (snapshot != null && !snapshot.isEmpty) {
                    val userDocument = snapshot.documents.first()
                    val tasks = userDocument["tasks"] as? List<Map<String, Any>>
                    val taskEntities = tasks?.mapNotNull { it.toTaskEntity() } ?: emptyList()
                    onTasksChanged(Result.success(taskEntities))
                } else {
                    onTasksChanged(Result.failure(Exception("User not found or tasks empty")))
                }
            }
    }


    suspend fun fetchUserByEmail(): Result<UserEntity> {

        return try {

            val userSnapshot = usersCollection.whereEqualTo("email", getCurrentUserEmail()).get().await()

            if (userSnapshot.isEmpty) {
                Result.failure(Exception("User not found"))
            } else {

                val userDocument = userSnapshot.documents.first()
                val user = UserEntity(
                    id = userDocument["id"] as? Long ?: 0L,
                    tasks = (userDocument["tasks"] as? List<Map<String, Any>>)?.map { it.toTaskEntity() } ?: emptyList(),
                    name = userDocument["name"] as? String ?: "",
                    email = userDocument["email"] as? String ?: "",
                    labels = userDocument["labels"] as? List<String> ?: emptyList()
                )

                Result.success(user)
            }
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }


    suspend fun deleteTaskForCurrentUser(taskId: Long): Result<Unit> {
        return try {
            val email = getCurrentUserEmail()
            if (email != null) {
                if (email.isEmpty()) return Result.failure(Exception("User not signed in"))
            }

            val userSnapshot = usersCollection.whereEqualTo("email", email).get().await()
            if (userSnapshot.isEmpty) return Result.failure(Exception("User not found"))

            val userDocument = userSnapshot.documents.first()
            val existingTasks = userDocument["tasks"] as? List<Map<String, Any>> ?: emptyList()

            val updatedTasks = existingTasks.filterNot { taskMap ->
                (taskMap["id"] as? Long) == taskId
            }

            userDocument.reference.update("tasks", updatedTasks).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun addNewUserToFireStore(): Result<Unit> {

        val currentUser = firebaseAuth.currentUser

        return if (currentUser == null) {
            Result.failure(Exception("No authenticated user found"))
        } else {
            try {
                val userEntity = UserEntity(
                    id = System.currentTimeMillis(),
                    tasks = emptyList(),
                    name = currentUser.displayName.orEmpty(),
                    email = currentUser.email.orEmpty(),
                    labels = emptyList()
                )
                addUser(userEntity)
            } catch (exception: Exception) {
                Result.failure(exception)
            }
        }
    }

    suspend fun updateTaskForCurrentUser(updatedTask: TaskEntity): Result<Unit> {

        return try {

            val userEmail = getCurrentUserEmail().takeIf { it != null }
                ?: return Result.failure(Exception("User not signed in"))

            val userSnapshot = usersCollection.whereEqualTo("email", userEmail).get().await()
            if (userSnapshot.isEmpty) return Result.failure(Exception("User not found"))

            val userDocument = userSnapshot.documents.first()
            val tasks = userDocument["tasks"] as? List<Map<String, Any>>

            val updatedTasks = tasks?.map { taskMap ->
                val task = taskMap.toTaskEntity()
                if (task.id == updatedTask.id) updatedTask else task
            } ?: listOf(updatedTask)

            userDocument.reference.update("tasks", updatedTasks.map { it.toMap() }).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun addTaskToUser(email: String, newTask: TaskEntity): Result<Unit> {
        return try {
            val userSnapshot = usersCollection.whereEqualTo("email", email).get().await()
            if (userSnapshot.documents.isEmpty()) return Result.failure(Exception("User not found"))

            val userDocument = userSnapshot.documents.first()
            val existingTasks = userDocument["tasks"] as? List<Map<String, Any>>
            val updatedTasks = (existingTasks?.mapNotNull { it.toTaskEntity() } ?: emptyList()) + newTask

            userDocument.reference.update("tasks", updatedTasks.map { it.toMap() }).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun addUser(user: UserEntity): Result<Unit> {
        return try {
            usersCollection.add(user).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getCurrentUserEmail(): String? = firebaseAuth.currentUser?.email

    fun signOut(onComplete: (Result<Unit>) -> Unit) {
        try {
            firebaseAuth.signOut()
            onComplete(Result.success(Unit))
        } catch (exception: Exception) {
            onComplete(Result.failure(exception))
        }
    }

    fun signUp(email: String, password: String, onComplete: (Boolean, String) -> Unit) {
        if (email.isBlank() || password.isBlank()) {
            onComplete(false, "Email or Password can't be empty")
            return
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(true, "Account Created Successfully")
                } else {
                    val errorMessage = mapFirebaseAuthException(task.exception)
                    onComplete(false, errorMessage)
                }
            }
    }

    fun signIn(email: String, password: String, onComplete: (Boolean, String) -> Unit) {
        if (email.isBlank() || password.isBlank()) {
            onComplete(false, "Email or Password can't be empty")
            return
        }

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(true, "Welcome")
                } else {
                    val errorMessage = mapFirebaseAuthException(task.exception)
                    onComplete(false, errorMessage)
                }
            }
    }

    fun sendPasswordResetEmail(email: String, onResult: (Result<Unit>) -> Unit) {

        if (email.isBlank()) {
            onResult(Result.failure(Exception("Email can't be empty")))
            return
        }

        firebaseAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(Result.success(Unit))
                } else {
                    onResult(Result.failure(task.exception ?: Exception("Error sending reset email")))
                }
            }
    }

    private fun mapFirebaseAuthException(exception: Exception?): String {
        return when (exception) {
            is FirebaseAuthWeakPasswordException -> "Password is too weak"
            is FirebaseAuthInvalidCredentialsException -> "Invalid email format"
            is FirebaseAuthUserCollisionException -> "Email already in use"
            is FirebaseNetworkException -> "Network error. Please check your connection"
            is FirebaseAuthInvalidUserException -> "No account found with this email"
            else -> exception?.message.orEmpty()
        }
    }

    private fun TaskEntity.toMap(): Map<String, Any?> = mapOf(
        "id" to id,
        "taskContent" to taskContent,
        "labels" to labels,
        "dueDate" to dueDate,
        "isRepeated" to isRepeated,
        "isStared" to isStared,
        "priority" to priority,
        "isCompleted" to isCompleted,
        "completionDate" to completionDate
    )

    private fun Map<String, Any>.toTaskEntity(): TaskEntity = TaskEntity(
        id = this["id"] as? Long ?: System.currentTimeMillis(),
        taskContent = this["taskContent"] as? String ?: "",
        labels = this["labels"] as? List<String> ?: emptyList(),
        dueDate = this["dueDate"] as? String ?: "",
        isRepeated = this["isRepeated"] as? Boolean ?: false,
        isStared = this["isStared"] as? Boolean ?: false,
        priority = this["priority"] as? Long ?: 0L,
        isCompleted = this["isCompleted"] as? Boolean ?: false,
        completionDate = this["completionDate"] as? String ?: ""
    )

}
