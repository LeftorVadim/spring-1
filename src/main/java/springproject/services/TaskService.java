package springproject.services;

import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springproject.dao.TaskDAO;
import springproject.domain.Status;
import springproject.domain.Task;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@Data
public class TaskService {
    private final TaskDAO taskDAO;

    public TaskService(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    public List<Task> findAllTask(int pageNumber, int pageSize) {
        return taskDAO.findAllTask(pageNumber, pageSize);
    }

    public int taskCount() {
        return taskDAO.taskCount();
    }

    public Task findById(int id) {
        Optional<Task> task = Optional.ofNullable(taskDAO.findTaskById(id));
        return task.orElse(null);
    }

    @Transactional
    public void create(String description, Status status) {
        Task task = new Task();
        task.setDescription(description);
        task.setStatus(status);
        taskDAO.createTask(task);
    }

    @Transactional
    public void update(int id, String description, Status status) {
        try {
            Task task = taskDAO.findTaskById(id);
            task.setStatus(status);
            task.setDescription(description);
            taskDAO.updateTask(id, task);
        } catch (Exception e) {
            System.out.println("Task not found");
        }
    }

    @Transactional
    public void delete(int id) {
        taskDAO.deleteTask(id);
    }
}
