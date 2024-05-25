package org.taskspfe.pfe.service.task;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.taskspfe.pfe.dto.task.TaskDTO;
import org.taskspfe.pfe.model.task.Task;
import org.taskspfe.pfe.utility.CustomResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface TaskService {


    ResponseEntity<CustomResponseEntity<TaskDTO>> createTask(
            UserDetails userDetails,
            UUID assignedToId,
            Task newTask
    );
    ResponseEntity<CustomResponseEntity<TaskDTO>> updateTask(
            long taskId,
            int progress,
            UUID assignedToId,
            String status
    );
    ResponseEntity<CustomResponseEntity<TaskDTO>> updateTask(
            long taskId,
            UUID assignedToId,
            Task updatedTask
    );
    ResponseEntity<CustomResponseEntity<List<TaskDTO>>> fetchTaskByCurrentUser(UserDetails userDetails);
    ResponseEntity<CustomResponseEntity<TaskDTO>> deleteTask(final long taskId);
    ResponseEntity<CustomResponseEntity<List<TaskDTO>>> getAllTasks();
    ResponseEntity<CustomResponseEntity<List<TaskDTO>>> searchTasks(
            final String taskName,
            final String taskDescription,
            final String taskStatus,
            final UUID taskCreatedBy,
            final UUID taskAssignedTo
    );
    ResponseEntity<CustomResponseEntity<Map<LocalDate, Long>>> getTaskCountByDayInMonth(int year, int month);
    Task getTaskById(final long taskId);
}
