package it.sevenbits.courses.homeworks.postgresql.core.service;

import it.sevenbits.courses.homeworks.postgresql.core.model.Task;
import it.sevenbits.courses.homeworks.postgresql.core.repository.TasksRepository;
import it.sevenbits.courses.homeworks.postgresql.web.model.AddTaskRequest;
import it.sevenbits.courses.homeworks.postgresql.web.model.GetTasksRequest;
import it.sevenbits.courses.homeworks.postgresql.web.model.GetTasksResponse;
import it.sevenbits.courses.homeworks.postgresql.web.model.UpdateTaskRequest;
import it.sevenbits.courses.homeworks.postgresql.web.service.TasksPaginationLinksService;

import java.util.List;
import java.util.Date;
import java.util.UUID;


/**
 * Service for tasks.
 *
 * @author Daria Trofimova
 * @version 1.0
 * @since 2019-03-29
 */
public class TasksService {

    private TasksRepository tasksRepository;

    private TasksValidationService tasksValidationService;

    /**
     * Constructor.
     *
     * @param tasksRepository - tasks repository to use.
     */
    public TasksService(final TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
        tasksValidationService = new TasksValidationService();
    }

    /**
     * Creates task based on add task request.
     *
     * @param addTaskRequest - add task request.
     * @return created task.
     */
    private Task formTask(final AddTaskRequest addTaskRequest) {
        UUID id = getNextId();
        String text = addTaskRequest.getText();
        String status = tasksValidationService.DEFAULT_STATUS;
        Date createdAt = new Date();
        return new Task(id, text, status, createdAt, createdAt);
    }

    /**
     * Creates task based on updated task request and existing task.
     *
     * @param updateTaskRequest - update task request.
     * @return created task.
     */
    private Task formUpdatedTask(final Task task, final UpdateTaskRequest updateTaskRequest) {
        String text = task.getText();
        String status = task.getStatus();
        if (updateTaskRequest.getText() != null) {
            text = updateTaskRequest.getText();
        }
        if (updateTaskRequest.getStatus() != null) {
            status = updateTaskRequest.getStatus();
        }
        return new Task(task.getId(), text, status, task.getCreatedAt(), task.getUpdatedAt());
    }

    /**
     * Returns id for new task.
     *
     * @return id.
     */
    private UUID getNextId() {
        return UUID.randomUUID();
    }

    /**
     * Determines whether task with id exists or not.
     *
     * @param id - id.
     * @return true if status legit, false otherwise.
     */
    public boolean isTaskExists(final UUID id) {
        return tasksRepository.getTask(id) != null;
    }

    /**
     * Returns all task with given status.
     *
     * @return tasks.
     */
    public List<Task> getTasks() {
        return tasksRepository.getTasksByStatus(TasksValidationService.DEFAULT_STATUS);
    }

    /**
     * Returns all task with given status.
     *
     * @param status - task status.
     * @return tasks.
     */
    public List<Task> getTasks(final String status) {
        return tasksRepository.getTasksByStatus(status);
    }

    /**
     * Creates new task based on a given update task request.
     *
     * @param addTaskRequest - update task request.
     * @return new task.
     */
    public Task create(final AddTaskRequest addTaskRequest) {
        return tasksRepository.create(formTask(addTaskRequest));
    }

    /**
     * Returns task by id.
     *
     * @param id - task id.
     * @return task.
     */
    public Task getTask(final UUID id) {
        return tasksRepository.getTask(id);
    }

    /**
     * Deletes task by id.
     *
     * @param id - task id.
     * @return true if removal was successful.
     */
    public boolean deleteTask(final UUID id) {
        return tasksRepository.deleteTask(id);
    }

    /**
     * Updates task by id according to given update task request.
     *
     * @param id                - task id.
     * @param updateTaskRequest - update task request.
     * @return updated task.
     */
    public Task updateTask(final UUID id, final UpdateTaskRequest updateTaskRequest) {
        return tasksRepository.updateTask(
                id,
                formUpdatedTask(tasksRepository.getTask(id), updateTaskRequest));
    }

    /**
     * Determines whether update task request valid or not.
     *
     * @param updateTaskRequest - update task request.
     * @return true if request valid, false otherwise.
     */
    public boolean isUpdateTaskRequestValid(final UpdateTaskRequest updateTaskRequest) {
        String text = updateTaskRequest.getText();
        String status = updateTaskRequest.getStatus();
        if (status != null) {
            if (!tasksValidationService.isStatusLegit(status)) {
                return false;
            }
        }
        return status != null || text != null;
    }

    /**
     * Creates get tasks response according to given get tasks request.
     *
     * @param getTasksRequest - get tasks request.
     * @return created get tasks response.
     */
    public GetTasksResponse createGetTasksResponse(final GetTasksRequest getTasksRequest) {
        String status = tasksValidationService.validateStatus(getTasksRequest.getStatus());
        int tasksAmount = tasksRepository.getTasksAmount(status);
        int size = tasksValidationService.validateSize(getTasksRequest.getSize());
        int pagesAmount = tasksAmount / size;
        if (tasksAmount % size != 0) {
            pagesAmount = pagesAmount + 1;
        }
        int pageNumber = tasksValidationService.validatePageNumber(getTasksRequest.getPageNumber(), pagesAmount);
        String order = tasksValidationService.validateOrder(getTasksRequest.getOrder());


        List<Task> tasks = tasksRepository.getTasks(status, order, size, size * (pageNumber - 1));
        TasksPaginationLinksService tasksPaginationLinksService = new TasksPaginationLinksService(status, order, tasksAmount, pageNumber,
                size);
        return new GetTasksResponse(tasksAmount,
                pageNumber,
                size,
                tasksPaginationLinksService.getNextPageLink(),
                tasksPaginationLinksService.getPrevPageLink(),
                tasksPaginationLinksService.getFirstPageLink(),
                tasksPaginationLinksService.getLastPageLink(),
                tasks);
    }
}
