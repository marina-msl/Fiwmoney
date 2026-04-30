# Fiwmoney — CLAUDE.md

## Project overview
Fiwmoney is a personal finance backend for tracking stocks. It notifies when the current price of a stock drops below the user's average purchase price.

Stack: Java 17, Spring Boot 3.3.1, Spring Security + JWT, PostgreSQL, Lombok, Maven.

## Architecture
Hexagonal Architecture:
- `domain/` — entities, ports (interfaces), exceptions. No framework dependencies.
- `application/` — use case services, application-layer exceptions.
- `adapters/in/` — controllers, exception handlers (GlobalExceptionHandler).
- `adapters/out/` — JPA entities, repository implementations, external API calls (FetchImpl).
- `dto/` — request/response records shared between layers.

### Key rules
- Domain entities have no JPA annotations — mapping happens in `adapters/repositories/`.
- Domain exceptions extend `AbstractException` (domain layer). Application exceptions also extend it.
- `GlobalExceptionHandler` handles `AbstractException` using `errorCode` (int) to resolve `HttpStatus`.
- Wallet ID is never exposed in URLs or responses — always resolved from the JWT username via `SecurityContextHolder`.
- All endpoints under `/api/v1/**` require authentication. `/auth/**` and `/actuator/**` are public.

## External services
- `search-stock-api` runs on `localhost:8090` — separate microservice for stock prices.
- Circuit breaker (`searchStockApi`) wraps `FetchImpl.getStockPrice` via Resilience4j.

## Git conventions
- Commit messages must always be in **English**.
- Use conventional commits: `feat`, `fix`, `refactor`, `chore`, `docs`.
- Scope examples: `feat(exception)`, `fix(auth)`, `chore(postman)`.

## Running locally
- Requires Docker running PostgreSQL on port 5432 and Redis on port 6379.
- Use profile `jpa` (default) for PostgreSQL. Profile `memory` disables the database.
- `search-stock-api` must be running separately on port 8090 for stock price features to work.

## What not to do
- Never use @Autowired on fields, always use constructor injection
- Never generate SQL directly in services, always use repositories
- Never add JPA annotations to domain entities, mapping belongs in adapters/out/
- Never import Spring or framework classes in the domain layer
- Never return JPA entities directly from controllers, always map to DTOs/records
- Never put business logic in controllers or adapters, it belongs in use cases
- Never catch generic Exception, always use specific exceptions extending AbstractException
- Never expose wallet ID in URLs or responses, always resolve from JWT
- Never hardcode credentials or secrets, use environment variables
- Never commit messages in Portuguese, always English