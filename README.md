# Hospital Management System

[![GitHub license](https://img.shields.io/github/license/Sushma-Ej/HospitalManagementSystem)](https://github.com/Sushma-Ej/HospitalManagementSystem/blob/main/LICENSE)
[![GitHub stars](https://img.shields.io/github/stars/Sushma-Ej/HospitalManagementSystem)](https://github.com/Sushma-Ej/HospitalManagementSystem/stargazers)
[![GitHub issues](https://img.shields.io/github/issues/Sushma-Ej/HospitalManagementSystem)](https://github.com/Sushma-Ej/HospitalManagementSystem/issues)

A comprehensive Hospital Management System built with Spring Boot and Thymeleaf that handles patient appointments, doctor consultations, and prescription management.

## Table of Contents
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Prerequisites](#prerequisites)
- [Setup Instructions](#setup-instructions)
- [Default Login Credentials](#default-login-credentials)
- [Deployment](#deployment)
- [Project Structure](#project-structure)
- [Contributing](#contributing)
- [License](#license)
- [Support](#support)

A comprehensive Hospital Management System built with Spring Boot and Thymeleaf that handles patient appointments, doctor consultations, and prescription management.

## ✨ Features

- **User Authentication**: Secure login for admin, doctors, and patients
- **Appointment Management**: Schedule, view, and manage appointments
- **Prescription Management**: Doctors can create and update prescriptions
- **Patient Records**: Maintain and view patient medical history
- **Responsive Design**: Works on desktop and mobile devices

## 🏗️ Architecture

This project follows the **Model-View-Controller (MVC)** architectural pattern, which separates the application into three main components:

- **Model**: Represents the data and business logic (entities, repositories, services)
- **View**: Handles the presentation layer (Thymeleaf templates)
- **Controller**: Manages user input and coordinates between Model and View

### Applied Design Principles

#### SOLID Principles
1. **Single Responsibility Principle (SRP)**
   - Each class has a single responsibility (e.g., `PrescriptionService` handles prescription-related business logic)
   - Controllers handle specific HTTP endpoints

2. **Open/Closed Principle (OCP)**
   - The system is open for extension but closed for modification
   - New features can be added by extending existing classes

3. **Liskov Substitution Principle (LSP)**
   - Subtypes are substitutable for their base types
   - Demonstrated in repository interfaces and their implementations

4. **Interface Segregation Principle (ISP)**
   - Specific interfaces for different clients
   - Example: Separate repository interfaces for different entities

5. **Dependency Inversion Principle (DIP)**
   - High-level modules don't depend on low-level modules; both depend on abstractions
   - Dependency injection is used throughout the application

#### GRASP Patterns
1. **Controller**
   - `PrescriptionController`, `DoctorController` handle system events

2. **Creator**
   - Factories and services are responsible for creating objects

3. **Information Expert**
   - Responsibility is assigned to the class with the most information
   - Example: `PrescriptionService` handles prescription-related operations

4. **Low Coupling**
   - Components are loosely coupled through interfaces
   - Example: Services depend on repository interfaces, not implementations

5. **High Cohesion**
   - Related functionality is kept together
   - Example: All prescription-related operations are in `PrescriptionService`

## 🛠️ Tech Stack

- **Backend**: Spring Boot 3.x, Spring Security, Spring Data JPA
- **Frontend**: Thymeleaf, Bootstrap 5, JavaScript
- **Database**: MySQL/PostgreSQL (configurable)
- **Build Tool**: Maven
- **Deployment**: Vercel (Frontend), Railway/Heroku (Backend)

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6.0 or higher
- MySQL 8.0 or PostgreSQL 13+
- Node.js 16+ (for frontend assets)

## 🚀 Setup Instructions

1. **Clone the repository**
   ```bash
   git clone https://github.com/Sushma-Ej/HospitalManagementSystem.git
   cd HospitalManagementSystem
   ```

2. **Database Setup**
   - Create a new MySQL/PostgreSQL database
   - Update `application.properties` with your database credentials
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/hospital_db
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

3. **Build and Run**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

4. **Access the Application**
   - Open http://localhost:8080 in your browser

## 🔑 Default Login Credentials

### Patient
- **Username**: tej
- **Password**: password
- **Role**: PATIENT

### Doctor
- **Username**: sush
- **Password**: password
- **Role**: DOCTOR

### Receptionist
- **Username**: vajra
- **Password**: password
- **Role**: RECEPTIONIST

## 🚀 Deployment

### Backend Deployment (Railway/Heroku)

1. **Railway**
   - Create a new Railway project
   - Connect your GitHub repository
   - Add PostgreSQL database
   - Set environment variables
   - Deploy

2. **Heroku**
   ```bash
   heroku create
   git push heroku main
   heroku addons:create heroku-postgresql:hobby-dev
   heroku config:set SPRING_PROFILES_ACTIVE=prod
   ```

### Frontend Deployment (Vercel)

1. Install Vercel CLI
   ```bash
   npm install -g vercel
   ```

2. Deploy
   ```bash
   vercel
   vercel --prod
   ```

## 📂 Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/example/HospitalManagementSystem/
│   │       ├── config/       # Configuration classes
│   │       ├── controller/   # MVC Controllers
│   │       ├── model/        # Entity classes
│   │       ├── repository/   # Data access layer
│   │       ├── security/     # Security configuration
│   │       └── service/      # Business logic
│   └── resources/
│       ├── static/           # Static resources
│       └── templates/        # Thymeleaf templates
└── test/                     # Test files
```

## Clean Up

To clean up unnecessary files and optimize the project:

1. Remove IDE-specific files (`.idea/`, `*.iml`)
2. Clear Maven cache if needed:
   ```bash
   mvn clean
   ```
3. Remove any unused dependencies from `pom.xml`

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request


## 💬 Support

For support, email sushmaej174@gmail.com or open an issue in the GitHub repository.
