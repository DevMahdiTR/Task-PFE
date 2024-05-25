package org.taskspfe.pfe.dto.task;

import org.springframework.stereotype.Service;
import org.taskspfe.pfe.dto.user.UserEntityDTOMapper;
import org.taskspfe.pfe.model.task.Task;

import java.util.function.Function;

@Service
public class TaskDTOMapper implements Function<Task,TaskDTO> {
    @Override
    public TaskDTO apply(Task task) {
        return new TaskDTO(
                task.getId(),
                task.getName(),
                task.getTimeInHours(),
                task.getProgress(),
                task.getDescription(),
                task.getStatus(),
                task.getCreateAt(),
                (task.getCreatedBy() == null ? null : new UserEntityDTOMapper().apply(task.getCreatedBy())),
                (task.getAssignedTo() == null ? null : new UserEntityDTOMapper().apply(task.getAssignedTo()))
        );
    }
}
