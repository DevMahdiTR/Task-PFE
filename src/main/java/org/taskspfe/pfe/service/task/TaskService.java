package org.taskspfe.pfe.service.task;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.taskspfe.pfe.dto.task.TaskDTO;
import org.taskspfe.pfe.model.task.Task;
import org.taskspfe.pfe.model.task.TaskStatus;
import org.taskspfe.pfe.utility.CustomResponseEntity;

import java.util.List;
import java.util.UUID;

public interface TaskService {


    public ResponseEntity<CustomResponseEntity<TaskDTO>> createTask(
            UserDetails userDetails,
            UUID assignedToId,
            Task newTask
    );
    public ResponseEntity<CustomResponseEntity<TaskDTO>> updateTask(
            long taskId,
            int progress,
            UUID assignedToId,
            String status
    );
    public ResponseEntity<CustomResponseEntity<TaskDTO>> updateTask(
            long taskId,
            UUID assignedToId,
            Task updatedTask
    );
    public ResponseEntity<CustomResponseEntity<List<TaskDTO>>> fetchTaskByCurrentUser(UserDetails userDetails);
    public ResponseEntity<CustomResponseEntity<TaskDTO>> deleteTask(final long taskId);
    public ResponseEntity<CustomResponseEntity<List<TaskDTO>>> getAllTasks();
    public ResponseEntity<CustomResponseEntity<List<TaskDTO>>> searchTasks(
            final String taskName,
            final String taskDescription,
            final String taskStatus,
            final UUID taskCreatedBy,
            final UUID taskAssignedTo
    );
    public Task getTaskById(final long taskId);
}
