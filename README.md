# Leads Management System

A reactive Spring Boot application for managing leads with JWT authentication, built using Spring WebFlux and R2DBC with PostgreSQL.

## 🚀 Features

- **Reactive Programming**: Built with Spring WebFlux for non-blocking I/O operations
- **JWT Authentication**: Secure API endpoints with JWT tokens
- **Database Integration**: PostgreSQL with R2DBC for reactive database access
- **RESTful API**: Complete CRUD operations for lead management
- **Validation**: Request validation using Spring Boot Validation
- **Security**: Spring Security integration for authentication and authorization
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

### Required Software
1. **Java 21** or higher
   - Download from [Oracle JDK](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html) or [OpenJDK](https://adoptium.net/)
   - Verify installation: `java -version`

2. **Maven 3.6+** (or use the included Maven wrapper)
   - Download from [Apache Maven](https://maven.apache.org/download.cgi)
   - Verify installation: `mvn -version`

3. **PostgreSQL 12+**
   - Download from [PostgreSQL official site](https://www.postgresql.org/download/)
   - Or use Docker: `docker run --name postgres -e POSTGRES_PASSWORD=yourpassword -p 5432:5432 -d postgres`

4. **Git** (for cloning the repository)
   - Download from [Git official site](https://git-scm.com/downloads)

### Optional Tools
- **Postman** or **Insomnia** (for API testing)
- **pgAdmin** (PostgreSQL administration tool)
- **Docker** (if you prefer containerized PostgreSQL)

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

### Step 1: Clone the Repository

```bash
git clone https://github.com/MamaboloGtub/leads-management-system.git
cd leads-management-system
```

### Step 2: Database Setup

#### Option A: Local PostgreSQL Installation

1. **Install PostgreSQL** (if not already installed)
2. **Create Database** (Optional - app uses default 'postgres' database)
3. **Update Configuration** (if needed):
   
   Edit `src/main/resources/application.yml`:
   ```yaml
   spring:
     r2dbc:
       url: r2dbc:postgresql://localhost:5432/postgres
       username: postgres
       password: YourPassword  # Change this to your PostgreSQL password
   ```

#### Option B: Docker PostgreSQL

```bash
# Run PostgreSQL in Docker
docker run --name leads-postgres -e POSTGRES_PASSWORD=Tshepo123 -p 5432:5432 -d postgres:15

# Verify container is running
docker ps
```

### Step 3: Configure Application Properties

Update the database credentials in `src/main/resources/application.yml`:

```yaml
spring:
  r2dbc:
    url: r2dbc:postgresql://localhost:5432/postgres
    username: postgres
    password: Tshepo123  # Update with your PostgreSQL password
```

### Step 4: Build the Application

#### Using Maven Wrapper (Recommended)

```bash
# On Windows
.\mvnw clean install

# On macOS/Linux
./mvnw clean install
```

#### Using System Maven

```bash
mvn clean install
```

### Step 5: Run the Application

#### Option A: Using Maven Wrapper

```bash
# On Windows
.\mvnw spring-boot:run

# On macOS/Linux
./mvnw spring-boot:run
```

#### Option B: Using System Maven

```bash
mvn spring-boot:run
```

#### Option C: Using Java directly

```bash
# First build the JAR
.\mvnw clean package

# Then run the JAR
java -jar target/leads-management-system-0.0.1-SNAPSHOT.jar
```

### Step 6: Verify Application is Running

The application should start on port 8080. You should see output similar to:

```
Started LeadsManagementSystemApplication in X.XXX seconds (JVM running for X.XXX)
```

You can verify by opening: http://localhost:8080

## 🔐 Authentication

The application uses JWT authentication with a simple hardcoded user for demo purposes.

### Login Credentials
- **Username**: `admin`
- **Password**: `password`

### Get Authentication Token

**POST** `/api/auth/login`

```json
{
  "username": "admin",
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

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/login` | Login and get JWT token |

### Leads Management Endpoints

All endpoints require JWT token in the `Authorization` header: `Bearer <token>`

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/leads` | Get all leads |
| GET | `/api/leads/{id}` | Get lead by ID |
| POST | `/api/leads` | Create new lead |
| PUT | `/api/leads/{id}` | Update existing lead |
| DELETE | `/api/leads/{id}` | Delete lead |

### API Usage Examples

#### 1. Login to get token

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "password"}'
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

#### 3. Get all leads

```bash
curl -X GET http://localhost:8080/api/leads \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

#### 4. Get lead by ID

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

### Application Properties

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
```

### Environment Variables

You can override configuration using environment variables:

```bash
export SPRING_R2DBC_URL=r2dbc:postgresql://localhost:5432/your_database
export SPRING_R2DBC_USERNAME=your_username
export SPRING_R2DBC_PASSWORD=your_password
export JWT_SECRET=your-jwt-secret
export SERVER_PORT=8080
```

## 🧪 Running Tests

```bash
# Run all tests
.\mvnw test

# Run tests with coverage
.\mvnw test jacoco:report

# Run specific test class
.\mvnw test -Dtest=LeadsManagementSystemApplicationTests
```

## 🐳 Docker Support

### Create Dockerfile

```dockerfile
FROM openjdk:21-jre-slim

WORKDIR /app

COPY target/leads-management-system-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
```

### Build and Run with Docker

```bash
# Build the application
.\mvnw clean package

# Build Docker image
docker build -t leads-management-system .

# Run with Docker Compose (create docker-compose.yml first)
docker-compose up
```

### Docker Compose Setup

Create `docker-compose.yml`:

```yaml
version: '3.8'
services:
  postgres:
    image: postgres:15
    environment:
      POSTGRES_PASSWORD: Tshepo123
      POSTGRES_DB: postgres
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data

  app:
    build: .
    ports:
      - "8080:8080"
    depends_on:
      - postgres
    environment:
      SPRING_R2DBC_URL: r2dbc:postgresql://postgres:5432/postgres
      SPRING_R2DBC_USERNAME: postgres
      SPRING_R2DBC_PASSWORD: Tshepo123

volumes:
  postgres_data:
```

## 🛠 Development

### IDE Setup

#### IntelliJ IDEA
1. Import as Maven project
2. Enable Lombok plugin
3. Enable annotation processing
4. Set Project SDK to Java 21

#### VS Code
1. Install Java Extension Pack
2. Install Spring Boot Extension Pack
3. Open project folder

### Hot Reload

The application includes Spring Boot DevTools for hot reload during development. Changes to Java files will automatically restart the application.

## 🚨 Troubleshooting

### Common Issues

#### 1. Database Connection Error
```
Error: Connection refused
```
**Solution**: Ensure PostgreSQL is running and credentials are correct.

#### 2. Port Already in Use
```
Error: Port 8080 is already in use
```
**Solution**: 
- Kill process using port 8080: `netstat -ano | findstr :8080`
- Or change port in `application.yml`: `server.port: 8081`

#### 3. Java Version Error
```
Error: Java version not supported
```
**Solution**: Ensure Java 21+ is installed and set as default.

#### 4. Maven Build Fails
```
Error: Could not resolve dependencies
```
**Solution**: 
- Clear Maven cache: `.\mvnw dependency:purge-local-repository`
- Check internet connection
- Verify Maven settings

#### 5. JWT Token Issues
```
Error: 401 Unauthorized
```
**Solution**:
- Ensure you're including the Bearer token in Authorization header
- Check token hasn't expired (default: 1 hour)
- Verify JWT secret configuration

### Logs

View application logs:
- Console output during startup
- Enable debug logging by adding to `application.yml`:
  ```yaml
  logging:
    level:
      com.mamabologtub: DEBUG
      org.springframework.security: DEBUG
  ```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Author

**Tshepo M Mahudu**
- GitHub: [@MamaboloGtub](https://github.com/MamaboloGtub)

## 🙏 Acknowledgments

- Spring Boot team for the excellent framework
- PostgreSQL team for the reliable database
- JWT.io for JWT implementation guidance

---

## 📞 Support

If you encounter any issues or have questions:

1. Check the [Troubleshooting](#-troubleshooting) section
2. Search existing issues on GitHub
3. Create a new issue with detailed description
4. Contact the maintainer

---

**Happy Coding! 🚀**