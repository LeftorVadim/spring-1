package springproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springproject.domain.Task;
import springproject.services.TaskService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public String tasks(Model model,
                        @RequestParam(value = "page", required = false, defaultValue = "1") int pageNumber,
                        @RequestParam(value = "size", required = false, defaultValue = "10") int pageSize) {
        model.addAttribute("task", taskService.findAllTask((pageNumber - 1) * pageSize, pageSize));
        model.addAttribute("page", pageNumber);
        int totalPages = (int) Math.ceil(1.0 * taskService.taskCount() / pageSize);
        if (totalPages > 1) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "task/tasks";
    }

    @GetMapping("/{id}")
    public String showOneTask(@PathVariable("id") int id, Model model) {
        model.addAttribute("task", taskService.findById(id));
        return "task/show";
    }

    @GetMapping("/{id}/update")
    public String editTask(Model model, @PathVariable("id") int id) {
        model.addAttribute("task", taskService.findById(id));
        return "task/update";
    }

    @PatchMapping("/{id}")
    public String updateTask(@ModelAttribute("task") @Valid Task task, BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "task/update";
        }
        taskService.update(id, task.getDescription(), task.getStatus());
        return "redirect:/task";
    }

    @GetMapping("/create")
    public String newTask(@ModelAttribute("task") Task task) {
        return "task/create";
    }

    @PostMapping()
    public String createTask(@ModelAttribute("task") @Valid Task task, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "task/create";
        }
        taskService.create(task.getDescription(), task.getStatus());
        return "redirect:/task";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") int id) {
        taskService.delete(id);
        return "redirect:/task";
    }
}
