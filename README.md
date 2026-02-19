## Clients
#### POST /clients

Создать клиента.
Body: CreateClientDto.
Response: 201 Created, body ResponseClientDto.

#### POST /clients/login - вход пользователя!

#### PATCH /clients/{clientId}

Обновить данные клиента.
Body: UpdateClientDto.
Response: 200 OK, body ResponseClientDto.

#### DELETE /clients/{clientId}

Удалить клиента.
Response: 204 No Content.

#### GET /clients

Получить список всех клиентов.
Response: 200 OK, body List<ResponseClientDto>.(адреса не подшружаются)

#### GET /clients/{clientId}/addresses

Получить адреса клиента.
Response: 200 OK, body List<AddressDto>.

#### POST /clients/{clientId}/addresses

Добавить адрес клиенту.
Body: UpdateAddressDto.
Response: 201 Created, body AddressDto.

#### PATCH /clients/{clientId}/addresses/{addressId}

Обновить адрес клиента.
Body: UpdateAddressDto.
Response: 200 OK, body AddressDto.

#### DELETE /clients/{clientId}/addresses/{addressId}

Удалить адрес клиента.
Response: 204 No Content.

## Dishes

#### POST /dishes

Создать блюдо.
Body: CreateDishDto.
Response: 201 Created, body ResponseDishDto.

#### PATCH /dishes/{dishId}

Обновить блюдо.
Body: UpdateDishDto.
Response: 200 OK, body ResponseDishDto.

#### DELETE /dishes/{dishId}

Удалить блюдо.
Response: 204 No Content.

#### GET /dishes

Получить список блюд.
Response: 200 OK, body List<ResponseDishDto>.

## Orders

#### POST /orders

Создать заказ.
Body: CreateOrderDto (например: clientId, dishIds).
Response: 201 Created, body ResponseOrderDto.

#### PATCH /orders/{id}/status?status=CREATED|COURIER_ASSIGNED|...

Обновить только статус заказа.
Query param: status (enum OrderStatus).
Response: 200 OK, body ResponseOrderDto.(не меняется статус, исправлю)

#### GET /orders/{id}

Получить заказ по id.
Response: 200 OK, body ResponseOrderDto.

#### GET /orders?page=0&size=20&sort=createdAt,desc

Получить список заказов постранично.
Query params: стандартные для Pageable (page, size, sort).
Response: 200 OK, body Page<ResponseOrderDto>.

#### GET /orders/client/{clientId}?page=0&size=20&sort=createdAt,desc

Получить заказы конкретного клиента постранично.
Response: 200 OK, body Page<ResponseOrderDto>.

#### DELETE /orders/{id}

Удалить заказ.
Response: 204 No Content.

### Для запуска докера
Клонируешь репозиторий
Потом в терминале находишься в папке с проектом и выполняешь команду:
```bash
docker compose up --build

```
После этого приложение будет доступно по адресу http://localhost:8080/...
База данных будет доступна по адресу localhost:5432 инфа по базе в application.properties
Каждый запуск пустая
    