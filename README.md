# WashTrack 🧺

A multi-tenant SaaS platform for laundry business management, built with Spring Boot and Angular 19.

> **Status:** Active development — MVP in progress

---

## Overview

WashTrack helps laundry businesses manage their day-to-day operations: customer intake, order tracking, folio generation, and service history — all from a single platform that supports multiple independent tenants.

Designed as a real-world SaaS product with a focus on clean backend architecture, secure authentication, and scalable multi-tenancy.

---

## Tech Stack

**Backend**
- Java 21 + Spring Boot 3
- Spring Security + JWT (io.jsonwebtoken 0.11.5)
- MySQL with stored procedures for business logic
- RESTful API with tenant isolation via request attributes

**Frontend**
- Angular 19
- Frontend pagination (backend pagination planned)

**Infrastructure**
- Railway (planned deployment)
- HTTPS proxy architecture

---

## Features

- **Multi-tenant architecture** — each business operates in full isolation; tenant context resolved per request
- **JWT authentication** — stateless session management with Spring Security
- **Auto-generated folios** — ticket numbering handled via MySQL trigger on order creation
- **Order & service tracking** — full lifecycle from intake to delivery
- **Customer management** — registration, history, and per-tenant data isolation
- **Stored procedures** — business logic encapsulated in MySQL SPs with consistent error handling pattern (`SQLEXCEPTION` handler + OUT params + final SELECT for Spring)

---

## Architecture

```
┌─────────────────────────────────────┐
│           Angular 19 SPA            │
└────────────────┬────────────────────┘
                 │ HTTP + JWT
┌────────────────▼────────────────────┐
│        Spring Boot REST API         │
│  Spring Security · Tenant Filter    │
└────────────────┬────────────────────┘
                 │
┌────────────────▼────────────────────┐
│              MySQL                  │
│  Stored Procedures · Triggers       │
│  Per-tenant data isolation          │
└─────────────────────────────────────┘
```

---

## Getting Started

### Prerequisites

- Java 17+
- Node.js 18+ / Angular CLI
- MySQL 8+

### Backend setup

```bash
git clone https://github.com/amg-argenis/washtrack.git
cd washtrack/backend

# Configure your DB connection in application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/washtrack
spring.datasource.username=your_user
spring.datasource.password=your_password

# Run
./mvnw spring-boot:run
```

### Frontend setup

```bash
cd washtrack/frontend
npm install
ng serve
```

The app will be available at `http://localhost:4200`.

---

## API Overview

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/auth/login` | Authenticate and receive JWT |
| GET | `/api/orders` | List orders for current tenant |
| POST | `/api/orders` | Create new order (auto-generates folio) |
| GET | `/api/customers` | List customers for current tenant |
| POST | `/api/customers` | Register new customer |

> All protected endpoints require `Authorization: Bearer <token>` header.

---

## Key Design Decisions

**Multi-tenancy via request attributes** — tenant ID is extracted from the JWT and injected into each request context, ensuring complete data isolation without separate schemas.

**Business logic in stored procedures** — critical operations (folio generation, order creation) live in MySQL SPs, keeping the service layer thin and the database the single source of truth.

**Stateless auth** — Spring Security configured with `SessionCreationPolicy.STATELESS`; no server-side session storage.

---

## Roadmap

- [ ] BCrypt password migration (currently plain text — pre-production)
- [ ] Backend pagination (send `page`/`size` to stored procedures)
- [ ] Railway deployment with HTTPS
- [ ] Admin dashboard with business analytics
- [ ] PDF receipt generation

---

## Author

**Argenis** — Java & Spring Boot Developer based in Mexico City.

Open to remote opportunities.

[![LinkedIn](www.linkedin.com/in/argenis-muñoz-b37817309)
[![GitHub](https://github.com/amg-argenis)

---

*Built as a real SaaS product — not a tutorial project.*
