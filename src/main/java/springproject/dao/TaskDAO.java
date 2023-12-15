package springproject.dao;

import lombok.Data;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import springproject.domain.Task;

import java.util.List;

@Component
@Data
public class TaskDAO {
    private final SessionFactory sessionFactory;

    private Session createSession() {
        return sessionFactory.getCurrentSession();
    }

    @Autowired
    public TaskDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Task> findAllTask(int pageNumber, int pageSize) {
        Session session = createSession();
        Query<Task> query = session.createQuery("select t from Task t", Task.class);
        query.setFirstResult(pageNumber);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }
    @Transactional
    public int taskCount() {
        Query<Long> query = createSession().createQuery("select count(t) from Task t", Long.class);
        return Math.toIntExact(query.uniqueResult());
    }

    @Transactional(readOnly = true)
    public Task findTaskById(int id) {
        return createSession().get(Task.class, id);
    }

    @Transactional
    public void createTask(Task task) {
        createSession().save(task);
    }

    @Transactional
    public void updateTask(int id, Task updatedTask) {
        Task newTask = createSession().get(Task.class, id);
        newTask.setStatus(updatedTask.getStatus());
        newTask.setDescription(updatedTask.getDescription());
    }

    @Transactional
    public void deleteTask(int id) {
        createSession().remove(createSession().get(Task.class, id));
    }
}
