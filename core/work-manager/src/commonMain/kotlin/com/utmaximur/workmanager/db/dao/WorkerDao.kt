package com.utmaximur.workmanager.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import com.utmaximur.workmanager.db.entity.WorkerEntity

@Dao
internal interface WorkerDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(worker: WorkerEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(workers: List<WorkerEntity>)

    @Upsert
    suspend fun upsert(worker: WorkerEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(worker: WorkerEntity)

    @Delete
    suspend fun delete(worker: WorkerEntity)

    @Query("SELECT * FROM worker WHERE uuid = (:uuidString)")
    suspend fun getWorker(uuidString: String): WorkerEntity?

    @Query("SELECT * FROM worker")
    suspend fun getWorkers(): List<WorkerEntity>

    @Query("SELECT * FROM worker WHERE queue_id = (:queueId)")
    suspend fun getWorkersForQueueId(queueId: String): List<WorkerEntity>

    @Query("SELECT * FROM worker WHERE tags LIKE '%' || :tag || '%'")
    suspend fun getWorkerForTag(tag: String): WorkerEntity?

    @Query("SELECT * FROM worker WHERE tags LIKE '%' || :tag || '%'")
    fun getWorkerForTagFlow(tag: String): Flow<WorkerEntity?>

    @Query("SELECT * FROM worker WHERE uuid = (:uuidString)")
    fun getWorkerAsFlow(uuidString: String): Flow<WorkerEntity?>

    @Query("SELECT * FROM worker")
    fun getWorkersAsFlow(): Flow<List<WorkerEntity>>

    @Query("SELECT * FROM worker WHERE queue_id = (:queueId)")
    fun getWorkersForQueueIdAsFlow(queueId: String): Flow<List<WorkerEntity>>

    @Query("SELECT * FROM worker WHERE state = 'SUCCEEDED'")
    suspend fun getWorkersSucceeded(): List<WorkerEntity>

    @Query("DELETE FROM worker WHERE state = 'SUCCEEDED'")
    suspend fun deleteWorkersSucceeded()

}