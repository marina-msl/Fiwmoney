# Fiwmoney

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.1-green)
![Spring AI](https://img.shields.io/badge/Spring%20AI-1.1.2-blue)
![Status](https://img.shields.io/badge/Status-Em%20Desenvolvimento-yellow)
![Licença](https://img.shields.io/badge/Licença-Educacional-lightgrey)

- The application has been developed with Vue.js, CSS, Java Script and HTML in frontend and the backend is Java, SpringBoot, Spring Security, Lombok etc.
- The application purpose is to expand my portifolio and also build an useful tool for myself. I'm a big fan of investiments and I realized the need of having a application which could let me know when the stocks are cheapers then my average price (It's time to go shopping!).
- Once this application is to just enrich my knowlege, feel free to make any sugestions, since the clean code and SOLID principles, tecnologies or anything else!

# Getting start with Fiwmoney
The application has two differents dictories, backend and frotend.

Clone the whole projet to your machine.

**Getting start with frontend:** To visit the frontend, [click here](https://github.com/marina-msl/Fiwmoney_frontend)

**Getting start with backend:** initialize the aplication in the IDE of your choice.

## Configuration

The `application.properties` file contains properties to control which repository implementation is used:

| Property | Values | Description |
|---|---|---|
| `wallet.repository` | `jpa` \| `memory` | `jpa` persists wallets in PostgreSQL. `memory` stores in memory (data lost on restart, useful for local testing without a database). |
| `user.repository` | `jpa` \| `memory` | `jpa` persists users in PostgreSQL. `memory` stores in memory (same trade-offs as above). |

**This project use an API to check the stock's prices:** to check the API, [click here](https://github.com/marina-msl/search-stock-api)

# Sequence Diagram


<img width="547" alt="image" src="https://github.com/user-attachments/assets/4b4e16f6-ab1d-4139-8098-5a19732d3c53" />



### Next steps - some ideas, or not:
- [ ] Before thinking about authentication, I'm going to focus on CI/CD:
    - Set up with git actions
    - Implement some tests
    - Handle deployment
    - - [ ] Deploy with docker in AWS or GCP?
    - - [ ] Unity tests?
- [ ] change to Mongo DB?
- [ ] Fix email sending — currently not working
- [ ] Implement backend endpoints for user profile update (frontend already done)
- [ ] Improve current price editing on the frontend
- [ ] Implement OTP (One-Time Password) authentication

#### Done:

- [X] Frotend? Change to Vue Framework?
- [X] BD no Docker?
- [X] Change to PostgreSQL
- [X] Microservices - the search-api is gonna be separete from fiwmoney ? (Now, yes, let's see the next steps)
- [X] Send notifications or reports based on stock price changes!!
- [X] Working with threads to check the current price
- [X] Add dependencies JWT an Spring Security  (pom.xml)
- [X] Implement JwtUtil, JwtFilter and Security Config
- [X] Implement register and login in frontend
- [X] Implement REST Controller to Register
- [x] Implement UserService, UserRepository, UserDTO
- [X] Spring security and JWT
    - [X] Implement REST Controller to Login
    - [X] Frontend is already sending the headers

#### TODO (technical debt):
- [ ] Fix O(n²) in `updateStockPrices` — deduplicate stock fetch by code
- [ ] Fix hardcoded email in `sendMessage` — should use the user's email from DB
- [ ] Move OpenAI API key to environment variable (currently in application.properties)

# Load Testing

Tests performed with [k6](https://k6.io) on `GET /wallet/{id}` with JWT authentication.

| Metric | 50 VUs | 500 VUs |
|---|---|---|
| Status 200 | 100% | 100% |
| Response < 500ms | 93% | 26% |
| Median response time | 47ms | 1.42s |
| p90 | 353ms | 3.09s |
| p95 | 547ms | 3.46s |
| Max | 922ms | 7.07s |
| HTTP failures | 0% | 0% |

**Analysis:** The server dropped no requests in either scenario. With 50 VUs the API is responsive and within acceptable limits. With 500 VUs response times degrade due to HikariCP's default connection pool (10 connections), causing threads to queue for DB access. Increasing `spring.datasource.hikari.maximum-pool-size` is the first optimization step for higher concurrency.
