# TrackFlow

# 📋 TrackFlow - A Secure Issue Tracking Application

TrackFlow is a full-stack, collaborative issue and project management platform. It features a secure RESTful API built with **Spring Boot** and a dynamic, responsive frontend built with **React**. Security is handled by **Spring Security** and **JWT**, providing role-based access for users.

<img width="1090" height="467" alt="image" src="https://github.com/user-attachments/assets/4d3390d1-105d-4d55-b45e-5a897274055f" />

## ✨ Features

- **Project & Issue Management**: Create projects, assign issues, and track progress.
- **Role-Based Access Control**: Differentiated permissions for `ADMIN` and `USER` roles.
- **JWT Authentication**: Secure, token-based authentication for all API endpoints.
- **Collaborative Comments**: Users can communicate within issue threads.
- **Containerized Backend**: The database is managed with Docker for easy setup.
- **Responsive UI**: Modern user interface built with React and Material-UI.

## 🛠️ Tech Stack

- **Backend**: Java, Spring Boot, Spring Security, JWT, PostgreSQL
- **Frontend**: React, Material-UI
- **Tools**: Docker, Maven, Git

## ▶️ How to Run

The application is split into a backend (Spring Boot) and a frontend (React).

### 1. Run the Backend

The backend database is containerized for simplicity.

1.  **Navigate to the backend directory and start the database:**
    ```bash
    # From the project's root directory
    cd backend
    docker-compose up
    ```
2.  **Run the Spring Boot application:**
    Open the project in your favorite IDE (like IntelliJ IDEA) and run the main application class. The backend API will be available at [http://localhost:8080](http://localhost:8080)

### 2. Run the Frontend

1.  **Navigate to the frontend directory:**
    ```bash
    # From the project's root directory
    cd frontend
    ```
2.  **Install dependencies and start the development server:**
    ```bash
    npm install
    npm run dev
    ```
The React application will now be running and accessible at [http://localhost:8080](http://localhost:8080)

<img width="1090" height="467" alt="image" src="https://github.com/user-attachments/assets/4d3390d1-105d-4d55-b45e-5a897274055f" />

<img width="1090" height="838" alt="image" src="https://github.com/user-attachments/assets/70d9d2b4-4604-478a-b0a7-bda015330d35" />

<img width="1090" height="492" alt="image" src="https://github.com/user-attachments/assets/1aad2b8a-906e-4964-9147-5c3ecc9f61a7" />

<img width="1090" height="598" alt="image" src="https://github.com/user-attachments/assets/09fb6b4e-5a35-4308-8bf1-edb8c6bdd57c" />

<img width="1090" height="751" alt="image" src="https://github.com/user-attachments/assets/6c0357df-57e5-4492-81f0-1fd03965732d" />

<img width="1090" height="764" alt="image" src="https://github.com/user-attachments/assets/6dd9dd4d-0672-4f26-a1db-8d02a5c47b05" />

<img width="1090" height="678" alt="image" src="https://github.com/user-attachments/assets/5f45cd3c-1762-46a7-87d8-0cab4aa7dea2" />




