
## Что конкретно умеет ADMIN
### Управление клиентами
Получить всех клиентов: GET /clients/admin

Удалить клиента: DELETE /clients/admin/{clientId}

### Управление курьерами

Удалить курьера: DELETE /couriers/admin/{courierId}

### Управление модераторами

Создать модератора: POST /moderators/admin

Обновить модератора: PATCH /admin/moderators/{moderatorId}

Удалить модератора: DELETE /moderators/admin/{moderatorId}

Получить всех модераторов: GET /moderators/admin

### Управление блюдами

Создать блюдо: POST /dishes/admin

Обновить блюдо: PATCH /dishes/admin/{dishId}

Удалить блюдо: DELETE /dishes/admin/{dishId}

### Управление заказами

Получить все заказы постранично: GET /orders/admin

Удалить заказ: DELETE /orders/admin/{id}

### Для запуска докера
Клонируешь репозиторий
Потом в терминале находишься в папке с проектом и выполняешь команду:
```bash
docker compose up --build

```
После этого приложение будет доступно по адресу http://localhost:8080/...
База данных будет доступна по адресу localhost:5432 инфа по базе в application.properties
Каждый запуск пустая
    