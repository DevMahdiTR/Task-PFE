package org.taskspfe.pfe.dto.task;

import org.taskspfe.pfe.dto.user.UserEntityDTO;
import org.taskspfe.pfe.model.task.TaskStatus;

import java.time.LocalDateTime;

public record TaskDTO (
        long id,
        String name,
        int timeInHours,
        int progress,
        String description,
        String status,
        LocalDateTime createAt,
        UserEntityDTO createdBy,
        UserEntityDTO assignedTo
){
}
