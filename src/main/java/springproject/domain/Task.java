package springproject.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(schema = "todo", name = "task")
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "Description should not be empty")
    private String description;
    @Enumerated(EnumType.ORDINAL)
    private Status status;

    public Task() {
    }

    public Task(int id, String description, Status status) {
        this.id = id;
        this.description = description;
        this.status = status;
    }
}
