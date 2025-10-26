# Leads Management System

A reactive Spring Boot application for managing leads with JWT authentication, built using Spring WebFlux and R2DBC with PostgreSQL.

## 🚀 Features

- **Reactive Programming**: Built with Spring WebFlux for non-blocking I/O operations
- **JWT Authentication**: Secure API endpoints with JWT tokens
- **Database Integration**: PostgreSQL with R2DBC for reactive database access
- **RESTful API**: Complete CRUD operations for lead management
- **Error Handling**: Comprehensive exception handling with custom error responses
- **CORS Support**: Cross-origin requests enabled for frontend integration
- **Auto Database Schema**: Automatic database schema creation on startup

## 🛠 Tech Stack

- **Java 21**
- **Spring Boot 3.5.6**
- **Spring WebFlux** (Reactive Web)
- **Spring Security** (Authentication & Authorization)
- **Spring Data R2DBC** (Reactive Database Access)
- **PostgreSQL** (Database)
- **JWT** (JSON Web Tokens for authentication)
- **Lombok** (Reduce boilerplate code)
- **Maven** (Build tool)

## 📋 Prerequisites

Before running this application, make sure you have the following installed:

- **Java 21** or higher
- **Maven 3.6+** (or use the included Maven wrapper)
- **PostgreSQL 12+**
- **Git** (for cloning the repository)

## 🏗 Project Structure

```
leads-management-system/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/mamabologtub/leads_management_system/
│   │   │       ├── LeadsManagementSystemApplication.java
│   │   │       ├── config/
│   │   │       │   └── SecurityConfig.java
│   │   │       ├── controllers/
│   │   │       │   ├── AuthController.java
│   │   │       │   └── LeadsController.java
│   │   │       ├── dtos/
│   │   │       │   └── LeadsDto.java
│   │   │       ├── entities/
│   │   │       │   └── Lead.java
│   │   │       ├── repositories/
│   │   │       │   └── LeadsRepository.java
│   │   │       ├── services/
│   │   │       │   ├── LeadsService.java
│   │   │       │   └── impl/
│   │   │       │       └── LeadsServiceImpl.java
│   │   │       └── util/
│   │   │           └── JwtUtil.java
│   │   └── resources/
│   │       ├── application.yml
│   │       └── schema.sql
│   └── test/
│       └── java/
│           └── com/mamabologtub/leads_management_system/
│               └── LeadsManagementSystemApplicationTests.java
├── pom.xml
└── README.md
```

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/MamaboloGtub/leads-management-system.git
cd leads-management-system
```

### 2. Database Setup

1. **Install PostgreSQL** and create a database named `postgres`
2. **Update Configuration** in `src/main/resources/application.yml`:
   
   ```yaml
   spring:
     r2dbc:
       url: r2dbc:postgresql://localhost:5432/postgres
       username: postgres
       password: Tshepo123  # Change this to your PostgreSQL password
   ```

### 3. Build and Run

```bash
# Build the application
.\mvnw clean install

# Run the application
.\mvnw spring-boot:run
```

The application will start on port 8080.

## 🔐 Authentication

The application uses JWT authentication with a hardcoded user for demo purposes.

### Login Credentials
- **Email**: `admin@flux.com`
- **Password**: `password`

### Get Authentication Token

**POST** `/api/auth/login`

```json
{
  "email": "admin@flux.com",
  "password": "password"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

## 📡 API Endpoints

### Authentication Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/auth/login` | Login and get JWT token | No |

### Leads Management Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | `/api/leads` | Get all leads | No |
| GET | `/api/leads/{id}` | Get lead by ID | Yes |
| POST | `/api/leads` | Create new lead | Yes |
| PUT | `/api/leads/{id}` | Update existing lead | Yes |
| DELETE | `/api/leads/{id}` | Delete lead | Yes |

**Note**: Authenticated endpoints require JWT token in the `Authorization` header: `Bearer <token>`

### API Usage Examples

#### 1. Login to get token

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email": "admin@flux.com", "password": "password"}'
```

#### 2. Create a new lead

```bash
curl -X POST http://localhost:8080/api/leads \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "name": "John Doe",
    "email": "john.doe@example.com",
    "leadSource": "Website",
    "leadStatus": "NEW"
  }'
```

#### 3. Get all leads (no auth required)

```bash
curl -X GET http://localhost:8080/api/leads
```

#### 4. Get lead by ID (auth required)

```bash
curl -X GET http://localhost:8080/api/leads/1 \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

#### 5. Update a lead

```bash
curl -X PUT http://localhost:8080/api/leads/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "name": "John Doe Updated",
    "email": "john.doe.updated@example.com",
    "leadSource": "Social Media",
    "leadStatus": "CONTACTED"
  }'
```

#### 6. Delete a lead

```bash
curl -X DELETE http://localhost:8080/api/leads/1 \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

## 🗄 Database Schema

The application automatically creates the following table on startup:

```sql
CREATE TABLE leads (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    lead_source VARCHAR(100),
    lead_status VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

## 🔧 Configuration

Key configuration properties in `application.yml`:

```yaml
spring:
  application:
    name: leads-management-system
  main:
    web-application-type: reactive
  r2dbc:
    url: r2dbc:postgresql://localhost:5432/postgres
    username: postgres
    password: Tshepo123
  sql:
    init:
      mode: ALWAYS

server:
  port: 8080

jwt:
  secret: my-very-very-secret-key-for-leads
  expiration-ms: 3600000  # 1 hour

api:
  source: leads-management-system-api
```

## �️ Error Handling

The application includes comprehensive error handling:

- **Custom Exceptions**: `BaseException`, `LeadNotFoundException`, `DuplicateEmailException`, `InvalidDataException`
- **Global Exception Handler**: `ApiAdvice` for consistent error responses
- **Structured Error Responses**: All errors return structured JSON responses with error codes and messages

## 🧪 Running Tests

```bash
# Run all tests
.\mvnw test
```
