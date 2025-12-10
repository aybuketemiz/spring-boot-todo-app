package com.todo.service;

import com.todo.model.Todo;
import com.todo.model.User;
import com.todo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TodoService {
    
    private final TodoRepository todoRepository;
    
    @Autowired
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }
    
    public List<Todo> getAllTodosByUser(User user) {
        return todoRepository.findByUserOrderByCreatedDateDesc(user);
    }
    
    public List<Todo> getTodosByUserAndCompleted(User user, Boolean completed) {
        return todoRepository.findByUserAndCompletedOrderByCreatedDateDesc(user, completed);
    }
    
    @Transactional
    public Todo createTodo(Todo todo, User user) {
        todo.setUser(user);
        return todoRepository.save(todo);
    }
    
    @Transactional
    public Todo updateTodo(Long id, Todo todoDetails, User user) {
        Todo todo = todoRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new IllegalArgumentException("Todo not found or access denied"));
        
        todo.setTitle(todoDetails.getTitle());
        todo.setDescription(todoDetails.getDescription());
        todo.setCompleted(todoDetails.getCompleted());
        
        return todoRepository.save(todo);
    }
    
    @Transactional
    public void toggleTodoComplete(Long id, User user) {
        Todo todo = todoRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new IllegalArgumentException("Todo not found or access denied"));
        
        todo.setCompleted(!todo.getCompleted());
        todoRepository.save(todo);
    }
    
    @Transactional
    public void deleteTodo(Long id, User user) {
        Todo todo = todoRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new IllegalArgumentException("Todo not found or access denied"));
        
        todoRepository.delete(todo);
    }
    
    public Todo getTodoById(Long id, User user) {
        return todoRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new IllegalArgumentException("Todo not found or access denied"));
    }
    
    public long countTodosByUser(User user) {
        return todoRepository.countByUser(user);
    }
    
    public long countCompletedTodosByUser(User user) {
        return todoRepository.countByUserAndCompleted(user, true);
    }
    
    public long countIncompleteTodosByUser(User user) {
        return todoRepository.countByUserAndCompleted(user, false);
    }
}
