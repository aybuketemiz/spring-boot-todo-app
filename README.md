# Spring Boot Todo Application

A full-featured todo application built with Spring Boot 3.x, featuring user authentication, authorization, and a modern Bootstrap 5 interface.

## 🚀 Features

- **User Authentication & Authorization**
  - User registration with email validation
  - Secure login/logout functionality
  - Session-based authentication
  - Password encryption using BCrypt
  - CSRF protection

- **Todo Management**
  - Create, read, update, and delete todos
  - Mark todos as complete/incomplete
  - Filter todos by status (all, completed, incomplete)
  - Each user can only access their own todos
  - Real-time todo statistics (total, completed, incomplete)

- **Modern UI**
  - Responsive design with Bootstrap 5
  - Clean and intuitive interface
  - Font Awesome icons
  - Success/error message notifications
  - Mobile-friendly layout

- **Multi-Profile Configuration**
  - Development profile with H2 in-memory database
  - Production profile with PostgreSQL support

## 🛠️ Technologies

- **Backend:**
  - Java 17
  - Spring Boot 3.2.0
  - Spring Security (Session-based authentication)
  - Spring Data JPA
  - Hibernate

- **Frontend:**
  - Thymeleaf
  - Bootstrap 5
  - Font Awesome

- **Databases:**
  - H2 Database (Development)
  - PostgreSQL (Production)

- **Build Tool:**
  - Maven

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- PostgreSQL 12+ (for production profile only)

## 🔧 Installation & Setup

### Option 1: Run with H2 Database (Development Profile - Recommended for Testing)

1. **Clone the repository:**
   ```bash
   git clone https://github.com/aybuketemiz/spring-boot-todo-app.git
   cd spring-boot-todo-app
   ```

2. **Build the project:**
   ```bash
   mvn clean install
   ```

3. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```
   
   Or with explicit profile:
   ```bash
   mvn spring-boot:run -Dspring-boot.run.profiles=dev
   ```

4. **Access the application:**
   - Application: http://localhost:8080
   - H2 Console: http://localhost:8080/h2-console
     - JDBC URL: `jdbc:h2:mem:tododb`
     - Username: `sa`
     - Password: (leave empty)

### Option 2: Run with PostgreSQL (Production Profile)

1. **Install and setup PostgreSQL:**
   ```bash
   # Create database
   sudo -u postgres psql
   CREATE DATABASE tododb;
   CREATE USER postgres WITH PASSWORD 'postgres';
   GRANT ALL PRIVILEGES ON DATABASE tododb TO postgres;
   \q
   ```

2. **Update database credentials (if different):**
   Edit `src/main/resources/application-prod.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/tododb
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

3. **Build the project:**
   ```bash
   mvn clean install
   ```

4. **Run with production profile:**
   ```bash
   mvn spring-boot:run -Dspring-boot.run.profiles=prod
   ```
   
   Or set the profile in `application.properties`:
   ```properties
   spring.profiles.active=prod
   ```

5. **Access the application:**
   - Application: http://localhost:8080

## 📖 Usage Guide

### Getting Started

1. **Navigate to the home page** at http://localhost:8080
2. **Register a new account:**
   - Click "Register Now"
   - Fill in username (3-50 characters)
   - Provide a valid email
   - Create a password (minimum 6 characters)
3. **Login** with your credentials
4. **Start managing your todos!**

### Managing Todos

- **Add a Todo:** Fill in the title and optional description in the left panel and click "Add Todo"
- **Mark as Complete:** Click the green checkmark button
- **Edit a Todo:** Click the edit (pencil) icon
- **Delete a Todo:** Click the delete (trash) icon
- **Filter Todos:** Use the filter buttons (All, Incomplete, Completed)

## 🔐 Security Features

- Passwords are encrypted using BCrypt
- CSRF protection enabled for all forms
- Session-based authentication
- Authorization: Users can only access their own todos
- H2 Console protected in production (disabled)

## 📊 Project Structure

```
src/main/java/com/todo/
├── TodoApplication.java          # Main application class
├── config/
│   └── SecurityConfig.java       # Spring Security configuration
├── controller/
│   ├── AuthController.java       # Authentication endpoints
│   └── TodoController.java       # Todo CRUD endpoints
├── model/
│   ├── User.java                 # User entity
│   └── Todo.java                 # Todo entity
├── repository/
│   ├── UserRepository.java       # User data access
│   └── TodoRepository.java       # Todo data access
├── service/
│   ├── UserService.java          # User business logic
│   └── TodoService.java          # Todo business logic
└── dto/
    └── UserRegistrationDto.java  # User registration data transfer object

src/main/resources/
├── application.properties         # Default configuration
├── application-dev.properties     # Development profile (H2)
├── application-prod.properties    # Production profile (PostgreSQL)
└── templates/
    ├── index.html                 # Home page
    ├── login.html                 # Login page
    ├── register.html              # Registration page
    ├── todos.html                 # Todo management page
    └── edit-todo.html             # Edit todo page
```

## 🌐 API Endpoints

### Public Endpoints
- `GET /` - Home page
- `GET /login` - Login page
- `POST /login` - Process login
- `GET /register` - Registration page
- `POST /register` - Process registration
- `POST /logout` - Logout

### Protected Endpoints (Require Authentication)
- `GET /todos` - List all todos for current user
- `GET /todos?filter={status}` - Filter todos (completed/incomplete)
- `POST /todos` - Create a new todo
- `GET /todos/{id}/edit` - Show edit form for a todo
- `POST /todos/{id}/edit` - Update a todo
- `POST /todos/{id}/toggle` - Toggle todo completion status
- `POST /todos/{id}/delete` - Delete a todo

## 🧪 Testing

The application comes with built-in testing capabilities. To run tests:

```bash
mvn test
```

## 🐛 Troubleshooting

### Common Issues

1. **Port 8080 already in use:**
   ```properties
   # Add to application.properties
   server.port=8081
   ```

2. **PostgreSQL connection issues:**
   - Verify PostgreSQL is running: `sudo service postgresql status`
   - Check database exists: `psql -U postgres -l`
   - Verify credentials in `application-prod.properties`

3. **Build failures:**
   ```bash
   # Clean and rebuild
   mvn clean install -U
   ```

## 📝 Configuration

### Application Profiles

The application supports multiple profiles for different environments:

- **dev** (default): Uses H2 in-memory database, perfect for development and testing
- **prod**: Uses PostgreSQL, suitable for production deployment

### Switching Profiles

Change the active profile in `src/main/resources/application.properties`:
```properties
spring.profiles.active=dev  # or prod
```

Or use command line:
```bash
java -jar target/spring-boot-todo-app-1.0.0.jar --spring.profiles.active=prod
```

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👨‍💻 Author

**Aybuke Temiz**

## 🙏 Acknowledgments

- Spring Boot team for the excellent framework
- Bootstrap team for the beautiful UI components
- Font Awesome for the icons

---

**Note:** This application is developed for educational purposes as part of an internship project.