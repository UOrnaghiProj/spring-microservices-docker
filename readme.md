
# Spring Microservices Project - User, Order, Notification

## 📦 Descrizione Generale

Questo progetto implementa un'architettura a microservizi composta da:

- **user-service**: gestione utenti, con cache Redis
- **order-service**: gestione ordini, con invio eventi Kafka
- **notification-service**: ascolto eventi Kafka e logging
- **PostgreSQL** per persistenza dati
- **Kafka + Zookeeper** per comunicazione asincrona
- **Redis** per caching

Il tutto è orchestrato con **Docker Compose**.

---

## ⚙️ Avvio del Progetto

### Requisiti

- Docker
- Docker Compose

### Comando unico per avvio completo

```bash
docker-compose up --build
```

Tutti i servizi vengono avviati sulla rete `backend`.

---

## 🔁 Integrazione Kafka

### Publisher: `order-service`

Dopo la creazione di un ordine, `order-service` invia un messaggio al topic `orders`.

### Consumer: `notification-service`

`notification-service` riceve i messaggi e stampa in console ogni evento di ordine creato.

#### Esempio di log:

```
[NOTIFICATION] New order event received: {"id":1,"product":"Notebook","quantity":3,"userId":1}
```

---

## ⚡ Redis per caching

`user-service` utilizza Redis per cachare i dati utente e ridurre il carico sul database:

```java
@Cacheable("users")
public User getUserById(Long id) {
    return userRepository.findById(id).orElse(null);
}
```

---

## 🐘 Database

- `userdb` → porta `5433`, utente `user`
- `orderdb` → porta `5434`, utente `order`

Entrambi i database sono popolati automaticamente all'avvio tramite script SQL (se presenti).

---

## 🔌 Servizi disponibili

| Servizio              | Porta     | Descrizione                        |
|----------------------|-----------|------------------------------------|
| user-service         | 8081      | CRUD utenti + cache                |
| order-service        | 8082      | CRUD ordini + Kafka producer       |
| notification-service | (interno) | Kafka consumer (log a terminale)   |
| Kafka                | 9092      | Event streaming                    |
| Redis                | 6379      | Cache                              |

---

## 🧪 Esempi di API Call

### 🧍‍♂️ User

```bash
curl http://localhost:8081/users
curl -X POST http://localhost:8081/users -H 'Content-Type: application/json' -d '{"name": "Mario", "email": "mario@example.com"}'
```

### 📦 Order

```bash
curl http://localhost:8082/orders
curl -X POST http://localhost:8082/orders -H 'Content-Type: application/json' -d '{"product": "Notebook", "quantity": 2, "userId": 1}'
```

---

## 📌 Note finali

- Le comunicazioni tra servizi avvengono tramite hostname Docker (`user-service`, `kafka`, ecc.)
- Redis e Kafka sono accessibili solo dalla rete interna Docker
- Per rigenerare da zero i DB: `docker-compose down -v`

---

Per ulteriori estensioni (es. MongoDB, Grafana, Spring Security), sentiti libero di contribuire o chiedere!
