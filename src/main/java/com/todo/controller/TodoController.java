package com.todo.controller;

import com.todo.model.Todo;
import com.todo.model.User;
import com.todo.service.TodoService;
import com.todo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/todos")
public class TodoController {
    
    private final TodoService todoService;
    private final UserService userService;
    
    @Autowired
    public TodoController(TodoService todoService, UserService userService) {
        this.todoService = todoService;
        this.userService = userService;
    }
    
    @GetMapping
    public String listTodos(Authentication authentication,
                           @RequestParam(required = false) String filter,
                           Model model) {
        User user = userService.findByUsername(authentication.getName());
        
        List<Todo> todos;
        if ("completed".equals(filter)) {
            todos = todoService.getTodosByUserAndCompleted(user, true);
        } else if ("incomplete".equals(filter)) {
            todos = todoService.getTodosByUserAndCompleted(user, false);
        } else {
            todos = todoService.getAllTodosByUser(user);
        }
        
        model.addAttribute("todos", todos);
        model.addAttribute("newTodo", new Todo());
        model.addAttribute("totalCount", todoService.countTodosByUser(user));
        model.addAttribute("completedCount", todoService.countCompletedTodosByUser(user));
        model.addAttribute("incompleteCount", todoService.countIncompleteTodosByUser(user));
        model.addAttribute("currentFilter", filter);
        model.addAttribute("username", user.getUsername());
        
        return "todos";
    }
    
    @PostMapping
    public String createTodo(@Valid @ModelAttribute("newTodo") Todo todo,
                            BindingResult result,
                            Authentication authentication,
                            RedirectAttributes redirectAttributes,
                            Model model) {
        if (result.hasErrors()) {
            User user = userService.findByUsername(authentication.getName());
            model.addAttribute("todos", todoService.getAllTodosByUser(user));
            model.addAttribute("totalCount", todoService.countTodosByUser(user));
            model.addAttribute("completedCount", todoService.countCompletedTodosByUser(user));
            model.addAttribute("incompleteCount", todoService.countIncompleteTodosByUser(user));
            model.addAttribute("username", user.getUsername());
            return "todos";
        }
        
        User user = userService.findByUsername(authentication.getName());
        todoService.createTodo(todo, user);
        redirectAttributes.addFlashAttribute("success", "Todo created successfully!");
        
        return "redirect:/todos";
    }
    
    @PostMapping("/{id}/toggle")
    public String toggleTodo(@PathVariable Long id,
                            Authentication authentication,
                            RedirectAttributes redirectAttributes) {
        try {
            User user = userService.findByUsername(authentication.getName());
            todoService.toggleTodoComplete(id, user);
            redirectAttributes.addFlashAttribute("success", "Todo status updated!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        
        return "redirect:/todos";
    }
    
    @PostMapping("/{id}/delete")
    public String deleteTodo(@PathVariable Long id,
                            Authentication authentication,
                            RedirectAttributes redirectAttributes) {
        try {
            User user = userService.findByUsername(authentication.getName());
            todoService.deleteTodo(id, user);
            redirectAttributes.addFlashAttribute("success", "Todo deleted successfully!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        
        return "redirect:/todos";
    }
    
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id,
                              Authentication authentication,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        try {
            User user = userService.findByUsername(authentication.getName());
            Todo todo = todoService.getTodoById(id, user);
            
            model.addAttribute("todo", todo);
            model.addAttribute("username", user.getUsername());
            
            return "edit-todo";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/todos";
        }
    }
    
    @PostMapping("/{id}/edit")
    public String updateTodo(@PathVariable Long id,
                            @Valid @ModelAttribute("todo") Todo todo,
                            BindingResult result,
                            Authentication authentication,
                            RedirectAttributes redirectAttributes,
                            Model model) {
        if (result.hasErrors()) {
            User user = userService.findByUsername(authentication.getName());
            model.addAttribute("username", user.getUsername());
            return "edit-todo";
        }
        
        try {
            User user = userService.findByUsername(authentication.getName());
            todoService.updateTodo(id, todo, user);
            redirectAttributes.addFlashAttribute("success", "Todo updated successfully!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        
        return "redirect:/todos";
    }
}
