
# SupaMenu API

A robust Spring Boot backend application for a restaurant menu management system. This API provides endpoints for managing restaurants, menus, orders, payments, and user authentication.

## Features

- **Restaurant Management**: Create, update, and manage restaurant profiles
- **Menu Management**: Define and organize menu items and categories
- **Order Processing**: Handle customer orders and track their status
- **Payment Integration**: Process payments for orders
- **User Authentication**: Secure JWT-based authentication and authorization
- **File Storage**: Upload and manage images for restaurants and menu items
- **Email Notifications**: Send email notifications for various events

## Technology Stack

- **Java 24**
- **Spring Boot 3.4.5**
- **Spring Security** with JWT authentication
- **Spring Data JPA** for database access
- **PostgreSQL** for data persistence
- **Redis** for caching
- **Flyway** for database migrations
- **Resilience4j** for rate limiting and circuit breaking
- **Thymeleaf** for email templates
- **Swagger/OpenAPI** for API documentation
- **Maven** for dependency management and build

## Project Structure

```
supamenu-api/
├── src/
│   ├── main/
│   │   ├── java/com/supamenu/backend/
│   │   │   ├── auth/                  # Authentication and security
│   │   │   ├── carts/                 # Shopping cart functionality
│   │   │   ├── commons/               # Common utilities and helpers
│   │   │   ├── config/                # Application configuration
│   │   │   ├── email/                 # Email service and templates
│   │   │   ├── file_storage/          # File upload and storage
│   │   │   ├── menus/                 # Menu and menu item management
│   │   │   ├── orders/                # Order processing
│   │   │   ├── payment/               # Payment processing
│   │   │   ├── restaurants/           # Restaurant management
│   │   │   ├── users/                 # User management
│   │   │   └── SupamenuApplication.java  # Main application class
│   │   ├── resources/
│   │   │   ├── db/migration/          # Flyway database migrations
│   │   │   ├── templates/             # Thymeleaf email templates
│   │   │   ├── static/                # Static resources
│   │   │   ├── application.properties # Common application properties
│   │   │   └── application-dev.properties # Development-specific properties
│   └── test/                          # Test classes
├── uploads/                           # Uploaded files storage
│   ├── menu_items/                    # Menu item images
│   └── restaurants/                   # Restaurant images
├── pom.xml                            # Maven configuration
├── mvnw                               # Maven wrapper script
├── supamenu-compose.yaml              # Docker Compose for development
└── README.md                          # This file
```

## Database Schema

The application uses a PostgreSQL database with the following main entities:

- **Users**: Authentication and user information
- **Restaurants**: Restaurant details and metadata
- **Menu Items**: Food and beverage items offered by restaurants
- **Carts**: Shopping carts for customers
- **Orders**: Customer orders with status tracking
- **Order Items**: Individual items within an order

## Prerequisites

- Java 24 or higher
- Maven 3.8 or higher
- PostgreSQL 13 or higher
- Redis 6 or higher
- Docker and Docker Compose (optional, for development environment)

## Getting Started

### Database Setup

1. Create a PostgreSQL database:
   ```sql
   CREATE DATABASE supamenu_db;
   ```

2. Configure database connection in `application-dev.properties` or use environment variables.

### Redis Setup

1. Ensure Redis is running and accessible.
2. Configure Redis connection in `application-dev.properties` or use environment variables.

### Running the Application

1. Clone the repository:
   ```bash
   git clone https://github.com/yvesHakizimana/supamenu-backend
   cd supamenu-api
   ```

2. Build the application:
   ```bash
   ./mvnw clean install
   ```

3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

4. The API will be available at `http://localhost:8080`

### Development Environment

For development, you can use the provided Docker Compose file to set up a mail server:

```bash
docker-compose -f supamenu-compose.yaml up -d
```

This will start a MailDev server accessible at:
- Web interface: http://localhost:1080
- SMTP server: localhost:1030

## API Documentation

Once the application is running, you can access the Swagger UI at:
```
http://localhost:8080/swagger-ui.html
```

## Configuration

The application uses Spring profiles for different environments:
- `dev` - Development environment (default)
- `prod` - Production environment

Configuration properties are located in:
- `application.properties` - Common properties
- `application-dev.properties` - Development-specific properties
- `application-prod.properties` - Production-specific properties

### Key Configuration Properties

- **Database**: Connection settings for PostgreSQL
- **Redis**: Connection settings for Redis cache
- **JWT**: Secret key and token expiration times
- **File Storage**: Upload directory and allowed file types
- **Email**: SMTP server settings for sending notifications

## Security

The application uses JWT-based authentication. Protected endpoints require a valid JWT token in the Authorization header. The security configuration includes:

- Token-based authentication
- Role-based access control
- Rate limiting for sensitive endpoints
- CORS configuration for frontend integration

## File Storage

The application supports file uploads for restaurant and menu item images. Uploaded files are stored in the `uploads` directory by default, organized in subdirectories by entity type.

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a new Pull Request

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Contact

For questions, feedback, or collaboration:

- **Email**: yvhakizimana123@gmail.com
- **GitHub**: [yvesHakizimana](https://github.com/yvesHakizimana)
- **Project Issues**: Please report bugs through the GitHub issue tracker

Feel free to reach out with any questions about using or contributing to the project.