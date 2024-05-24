package org.taskspfe.pfe.controller.task;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.taskspfe.pfe.dto.task.TaskDTO;
import org.taskspfe.pfe.model.task.Task;
import org.taskspfe.pfe.service.email.EmailSenderService;
import org.taskspfe.pfe.service.task.TaskService;
import org.taskspfe.pfe.utility.CustomResponseEntity;

import java.util.List;
import java.util.UUID;

@RequestMapping("api/v1/task")
@RestController
public class TaskController{

    private final TaskService taskService;
    private final EmailSenderService emailSenderService;

    public TaskController(TaskService taskService, EmailSenderService emailSenderService) {
        this.taskService = taskService;
        this.emailSenderService = emailSenderService;
    }

    @PostMapping("/admin")
    public ResponseEntity<CustomResponseEntity<TaskDTO>> createTask(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(name = "assignedToId" , required = false) UUID assignedToId,
            @Valid @RequestBody Task newTask
    ) {
        return taskService.createTask(userDetails, assignedToId, newTask);
    }

    @GetMapping("/all")
    public ResponseEntity<CustomResponseEntity<List<TaskDTO>>> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/all/search")
    public ResponseEntity<CustomResponseEntity<List<TaskDTO>>> searchTasks(
            @RequestParam(name = "taskName" , required = false) String taskName,
            @RequestParam(name = "taskDescription" , required = false) String taskDescription,
            @RequestParam(name = "taskStatus" , required = false) String taskStatus,
            @RequestParam(name = "taskCreatedBy" , required = false) UUID taskCreatedBy,
            @RequestParam(name = "taskAssignedTo" , required = false) UUID taskAssignedTo
    ) {
        return taskService.searchTasks(taskName, taskDescription, taskStatus, taskCreatedBy, taskAssignedTo);
    }

    @GetMapping("/all/current")
    public ResponseEntity<CustomResponseEntity<List<TaskDTO>>> fetchTaskByCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        return taskService.fetchTaskByCurrentUser(userDetails);
    }

    @PutMapping("/technician/{taskId}")
    public ResponseEntity<CustomResponseEntity<TaskDTO>> updateTask(
            @PathVariable("taskId") long taskId,
            @RequestParam(name = "progress" , required = false) int progress,
            @RequestParam(name = "assignedToId" , required = false) UUID assignedToId,
            @RequestParam(name = "status" , required = false) String status
    ) {
        return taskService.updateTask(taskId, progress, assignedToId, status);
    }

    @GetMapping("/technician/send")
    public ResponseEntity<CustomResponseEntity<String>> sendMail(
            @RequestParam(name = "title" , required = true) String title ,
            @RequestParam(name = "description" , required = true) String description ,
            @RequestParam(name = "task" , required = true) String task){
        emailSenderService.sendEmail("medmahdidev@gmail.com","Task Email" , emailSenderService.emailTemplateContact(title,description,task));
        return ResponseEntity.ok(new CustomResponseEntity<>(HttpStatus.OK,"Email sent successfully"));
    }

    @PutMapping("/admin/{taskId}")
    public ResponseEntity<CustomResponseEntity<TaskDTO>> updateTask(
            @PathVariable("taskId") long taskId,
            @RequestParam(name = "assignedToId" , required = false) UUID assignedToId,
            @Valid @RequestBody Task updatedTask
    ) {
        return taskService.updateTask(taskId, assignedToId, updatedTask);
    }

    @DeleteMapping("/admin/{taskId}")
    public ResponseEntity<CustomResponseEntity<TaskDTO>> deleteTask(@PathVariable("taskId") final long taskId) {
        return taskService.deleteTask(taskId);
    }



}
