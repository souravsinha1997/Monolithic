# Monolithic
A monolithic version of an e-commerce application designed for managing products, orders, and user interactions in a single Spring Boot service. This project is a simplified version of my earlier microservices-based E-Commerce System, built to demonstrate how the same functionality can be achieved with a monolithic architecture.
URL - https://souravecommerce.onrender.com

- User authentication & authorization.  
- CRUD operations for products.  
- Managing orders & order status.  
- Integration of all components (users, orders, products) in one service.  
- API documentation via Swagger UI.
- H2 database support for development & simple persistence.  


Project can be downloaded and run on local or this can be run using Docker also. And Swagger is added for API documentation.

STEPS:

Run this command in local : docker run -p 8080:8080 souravsinha1997/ecommerce-mono:0.0.3-SNAPSHOT. Project is running on 8080 internally, based on requirement and available ports 8080 can be changed.

Give http://localhost:8080/swagger-ui/index.html#/ in browser. It will open the API documentation and can be tested from there. (Port 8080 needs to be changed based on the selected port in previous step)
