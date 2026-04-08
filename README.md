<div align="center">

# 🔗 Zurl API

<p>
  <img src="https://img.shields.io/badge/Java-21-orange?logo=openjdk&logoColor=white" alt="Java 21" />
  <img src="https://img.shields.io/badge/Spring%20Boot-4.0.2-6DB33F?logo=springboot&logoColor=white" alt="Spring Boot 4.0.2" />
  <img src="https://img.shields.io/badge/PostgreSQL-Database-4169E1?logo=postgresql&logoColor=white" alt="PostgreSQL" />
  <img src="https://img.shields.io/badge/Redis-Cache-DC382D?logo=redis&logoColor=white" alt="Redis" />
  <img src="https://img.shields.io/badge/Maven-Build-C71A36?logo=apachemaven&logoColor=white" alt="Maven" />
</p>

<p>
  A compact backend service for shortening long URLs into clean, shareable links
  and resolving them with fast redirects.
</p>
</div>

---

## What It Does

- ✂️ Shortens long URLs into fixed **7-character keys**
- 🔁 Redirects short links using **HTTP 302**
- ⚡ Checks Redis first for faster repeated lookups
- 🗄️ Stores URL mappings in PostgreSQL
- 🛡️ Validates both incoming URLs and short keys
- 📦 Returns clean JSON responses for success and error cases
- 🔐 Uses a Feistel-style encoding approach backed by an app secret

---

## 🧱 Tech Stack

- Java 21
- Spring Boot 4
- Spring Web
- Spring Data JPA
- Spring Data Redis
- Redis
- PostgreSQL
- Maven

---

## 🌐 API Surface

### `POST /url`

Create a short URL.

#### Request

```json
{
  "longUrl": "https://example.com/some/very/long/path"
}
```

#### Response

```json
{
  "shortUrl": "http://localhost:222/AbC12_x"
}
```

---

### `GET /{key}`

Redirect to the original URL.

#### Example

```http
GET /AbC12_x
```

#### Response

```http
302 Found
Location: https://example.com/some/very/long/path
```

---

## ⚡ How Redirect Resolution Works

1. A long URL is stored in PostgreSQL
2. Its database ID is encoded into a short 7-character key
3. The final short URL is returned using the configured base URL
4. On redirect, Redis is checked first
5. If Redis misses, PostgreSQL is used and the result is cached

This keeps the main flow simple while improving repeat-read performance.

---

## 🗂️ Project Structure

```text
src/main/java/com/zurl/zurl_api
├── config/              # Redis configuration
├── constant/            # API paths and application constants
├── controller/          # REST endpoints
├── dto/                 # Request and response models
├── entity/              # JPA entities
├── exception/           # Custom exceptions
├── exceptionHandler/    # Centralized error handling
├── repo/                # Database repository layer
├── service/             # Service contracts
├── serviceImpl/         # Core business logic
├── validator/           # URL and key validation
└── ZurlApiApplication.java
```

---

## ⚙️ Configuration

The app is configured through `src/main/resources/application.yaml`.

---

## ❌ Error Response Format

```json
{
  "message": [
    "Invalid URL"
  ],
  "timestamp": "2026-04-07T10:30:00Z"
}

```

## 👨‍💻 Closing Note

Zurl API is a focused backend project that demonstrates API design, validation, caching, persistence, and service-layer structure in a practical use case.
