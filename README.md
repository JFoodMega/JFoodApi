# jFood API

REST API серверной части приложения для удалённого оформления заказов в ресторане и контроля их исполнения.

Flutter-клиент (iOS / Android) → **этот репозиторий** → PostgreSQL

---

## О проекте

jFood автоматизирует полный цикл работы ресторана: клиент оформляет заказ через мобильное приложение, менеджер назначает курьера, курьер доставляет и закрывает заказ — без звонков и ручного контроля.

### Роли

| Роль | Что делает |
|------|-----------|
| `CLIENT` | Регистрируется, просматривает меню, оформляет заказы, отслеживает статус |
| `MODERATOR` | Видит все активные заказы, назначает курьеров |
| `COURIER` | Получает назначенные заказы, выставляет статус занятости, отмечает доставку |
| `ADMIN` | Управляет блюдами, курьерами, менеджерами и клиентами |

### Жизненный цикл заказа

```
CREATED → COURIER_ASSIGNED → ON_THE_WAY → DELIVERED
                                      ↘ CANCELED
```

---

## Стек

- **Java 17**
- **Spring Boot**
- **Spring Data JPA / Hibernate**
- **PostgreSQL 14**
- **Docker / Docker Compose**
- **Maven**

---

## Запуск

### Через Docker Compose

```bash
git clone https://github.com/JFoodMega/JFoodApi.git
cd JFoodApi
docker-compose up --build
```

API будет доступен на `http://localhost:8080`


## Переменные окружения

| Переменная | Описание | По умолчанию |
|-----------|----------|-------------|
| `DB_HOST` | Хост базы данных | `localhost` |
| `DB_PORT` | Порт базы данных | `5432` |
| `DB_NAME` | Имя базы данных | `jfood` |
| `DB_USER` | Пользователь БД | `postgres` |
| `DB_PASSWORD` | Пароль БД | `postgres` |
| `SERVER_PORT` | Порт сервера | `8080` |

---

## API

### Авторизация

| Метод | Путь | Описание |
|-------|------|----------|
| `POST` | `/auth/login` | Вход. Возвращает роль и профиль пользователя |

```json
// POST /auth/login
{ "login": "user123", "password": "pass" }

// Response
{ "role": "CLIENT", "profile": { ... } }
```

---

### Клиенты

| Метод | Путь | Описание |
|-------|------|----------|
| `POST` | `/clients` | Регистрация клиента |
| `PATCH` | `/clients/{clientId}` | Обновить данные клиента |
| `GET` | `/clients/admin` | Список всех клиентов (admin) |
| `DELETE` | `/clients/admin/{clientId}` | Удалить клиента (admin) |
| `GET` | `/clients/{clientId}/addresses` | Адреса доставки клиента |
| `POST` | `/clients/{clientId}/addresses` | Добавить адрес |
| `PATCH` | `/clients/{clientId}/addresses/{addressId}` | Обновить адрес |
| `DELETE` | `/clients/{clientId}/addresses/{addressId}` | Удалить адрес |

---

### Заказы

| Метод | Путь | Описание |
|-------|------|----------|
| `POST` | `/orders` | Создать заказ |
| `GET` | `/orders/{id}` | Получить заказ по id |
| `GET` | `/orders/client/{clientId}` | Заказы клиента (с пагинацией) |
| `GET` | `/orders/admin` | Все заказы (admin, с пагинацией) |
| `PATCH` | `/orders/{orderId}/status` | Обновить статус заказа |
| `DELETE` | `/orders/admin/{id}` | Удалить заказ (admin) |

```json
// POST /orders
{ "clientId": 1, "dishIds": [2, 5, 8] }

// PATCH /orders/{orderId}/status
{ "status": "ON_THE_WAY" }
```

---

### Блюда

| Метод | Путь | Описание |
|-------|------|----------|
| `GET` | `/dishes` | Список всех блюд |
| `POST` | `/dishes/admin` | Добавить блюдо (admin) |
| `PATCH` | `/dishes/admin/{dishId}` | Обновить блюдо (admin) |
| `DELETE` | `/dishes/admin/{dishId}` | Удалить блюдо (admin) |

---

### Курьеры

| Метод | Путь | Описание |
|-------|------|----------|
| `POST` | `/couriers/admin` | Создать курьера (admin) |
| `GET` | `/couriers/available` | Список доступных курьеров |
| `GET` | `/couriers/{courierId}` | Получить курьера по id |
| `PATCH` | `/couriers/{courierId}` | Обновить данные курьера |
| `PATCH` | `/couriers/{courierId}/availability` | Установить статус занятости |
| `GET` | `/couriers/{courierId}/orders` | Заказы курьера |
| `DELETE` | `/couriers/admin/{courierId}` | Удалить курьера (admin) |

```json
// PATCH /couriers/{courierId}/availability
{ "isAvailable": true }
```

---

### Менеджеры

| Метод | Путь | Описание |
|-------|------|----------|
| `POST` | `/moderators/admin` | Создать менеджера (admin) |
| `GET` | `/moderators/admin` | Список всех менеджеров (admin) |
| `GET` | `/moderators/{moderatorId}` | Получить менеджера по id |
| `PATCH` | `/moderators/{moderatorId}` | Обновить данные менеджера |
| `DELETE` | `/moderators/admin/{moderatorId}` | Удалить менеджера (admin) |
| `GET` | `/moderators/orders` | Все заказы (moderator) |
| `POST` | `/moderators/orders/{orderId}/assign` | Назначить курьера на заказ |

```json
// POST /moderators/orders/{orderId}/assign?courierId=3&moderatorId=1
```

---

