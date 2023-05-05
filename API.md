
# API Reference

## User microservice

### Get user information

- Method: `GET`
- URL: `/api/v1/user/{id}`
- Description: finds a user in the database by UUID.

Example request:
```http
  GET /api/v1/user/4569dfb0-e8d9-11ed-a05b-0242ac120003
```

Example response:
```
HTTP/1.1 200 OK
Content-Type: application/json
{
  "id": "4569dfb0-e8d9-11ed-a05b-0242ac120003"
  "firstName": "John",
  "lastName": "Smith",
  "email": "john.smith@email.com",
  "password": "db89bcb5cd75107bd1809b2ac42e6a0d",
  "created": "2023-05-02 09:00:05",
  "updated": null,
  "lastLogin": "2023-05-02 09:03:10",
  "role": 1,
  "archived": null
}
```

Possible errors:
| Error code | Description                |
| :-------- | :------------------------- |
| 404 Not found | A user with this id could not be found |
| 500 Internal server error | An unexpected error occurred in the server |

### User registration

- Method: `POST`
- URL: `/api/v1/user/register`
- Description: creates a new user in the database.
- Parameters:

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `firstName` | `string` | **Required**. User's first name |
| `lastName` | `string` | **Required**. User's last name |
| `email` | `string` | **Required**. User's email address |
| `password` | `string` | **Required**. User's login password |

Example request:
```http
  POST /api/v1/user/register
  {
    "firstName": "John",
    "lastName": "Smith",
    "email": "john.smith@email.com",
    "password": "PaSsW0Rd12?"
  }
```

Example response:
```
HTTP/1.1 201 Created
Content-Type: application/json
{
  "id": "4569dfb0-e8d9-11ed-a05b-0242ac120003"
  "firstName": "John",
  "lastName": "Smith",
  "email": "john.smith@email.com",
  "password": "db89bcb5cd75107bd1809b2ac42e6a0d",
  "created": "2023-05-02 09:00:05",
  "updated": null,
  "lastLogin": null,
  "role": 1,
  "archived": null
}
```

Possible errors:
| Error code | Description                |
| :-------- | :------------------------- |
| 400 Bad request | Required fields were invalid, not specified |
| 409 Conflict | A user with this email address already exists |
| 500 Internal server error | An unexpected error occurred in the server |

### Update user

- Method: `PUT`
- URL: `/api/v1/user/{id}`
- Description: updates a user in the database.
- Parameters:

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `firstName` | `string` | User's first name |
| `lastName` | `string` | User's last name |
| `email` | `string` | User's email address |
| `password` | `string` | User's login password |

Example request:
```http
  PUT /api/v1/user/register
  {
    "firstName": "Peter",
    "lastName": "Jones",
    "email": "peter.jones@email.com",
    "password": "NewPaSsW0Rd12?"
  }
```

Example response:
```
HTTP/1.1 202 Accepted
Content-Type: application/json
{
  "id": "4569dfb0-e8d9-11ed-a05b-0242ac120003"
  "firstName": "Peter",
  "lastName": "Jones",
  "email": "peter.jones@email.com",
  "password": "29475d0840d36027354f11f10fc445de",
  "created": "2023-05-02 09:00:05",
  "updated": "2023-05-02 09:18:13",
  "lastLogin": "2023-05-02 09:03:10",
  "role": 1,
  "archived": null
}
```

Possible errors:
| Error code | Description                |
| :-------- | :------------------------- |
| 400 Bad request | Input fields were invalid |
| 404 Not found | A user with this id could not be found |
| 409 Conflict | A user with this email address already exists |
| 500 Internal server error | An unexpected error occurred in the server |

### Delete user

- Method: `DELETE`
- URL: `/api/v1/user/{id}`
- Description: deletes a user in the database by UUID.

Example request:
```http
  DELETE /api/v1/user/4569dfb0-e8d9-11ed-a05b-0242ac120003
```

Example response:
```
HTTP/1.1 204 No Content
```

Possible errors:
| Error code | Description                |
| :-------- | :------------------------- |
| 404 Not found | A user with this id could not be found |
| 500 Internal server error | An unexpected error occurred in the server |


## Appointment microservice

### Create a new appointment

- Method: `POST`
- URL: `/api/v1/appointment/create`
- Description: creates a new appointment in the database.
- Parameters:

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `start` | `datetime` | **Required**. Appointment start time and date |
| `end` | `datetime` | **Required**. Appointment end time and date |
| `specialistId` | `UUID` | **Required**. Specialist's id in the database |
| `roomId` | `int` | **Required**. Appointment room |
| `price` | `int` | **Required**. Appointment price |

Example request:
```http
  POST /api/v1/appointment/create
  {
    "start": "2023-05-01 9:00:00",
    "end": "2023-05-01 10:00:00",
    "specialistId": "4569dfb0-e8d9-11ed-a05b-0242ac120003",
    "roomId": "425",
    "price": 70
  }
```

Example response:
```
HTTP/1.1 201 Created
Content-Type: application/json
{
  "id": "bc41aa80-e8ea-11ed-a05b-0242ac120003"
  "start": "2023-05-01 9:00:00",
  "end": "2023-05-01 10:00:00",
  "specialistId": "4569dfb0-e8d9-11ed-a05b-0242ac120003",
  "roomId": "425",
  "price": 70
}
```

Possible errors:
| Error code | Description                |
| :-------- | :------------------------- |
| 400 Bad request | Required fields were invalid, not specified |
| 409 Conflict | This specialist or this room might be booked at that time|
| 500 Internal server error | An unexpected error occurred in the server |

### Get appointment information

- Method: `GET`
- URL: `/api/v1/appointment/{id}`
- Description: finds an appointment in the database by UUID.

Example request:
```http
  GET /api/v1/appointment/4569dfb0-e8d9-11ed-a05b-0242ac120003
```

Example response:
```
HTTP/1.1 200 OK
Content-Type: application/json
{
  "id": "4569dfb0-e8d9-11ed-a05b-0242ac120003",
  "start": "2023-05-01 9:00:00",
  "end": "2023-05-01 10:00:00",
  "specialistId": "bc41aa80-e8ea-11ed-a05b-0242ac120003",
  "roomId": "425",
  "price": 70
}
```

Possible errors:
| Error code | Description                |
| :-------- | :------------------------- |
| 404 Not found | An appointment with this id could not be found |
| 500 Internal server error | An unexpected error occurred in the server |

### Get all free appointments

- Method: `GET`
- URL: `/api/v1/appointment/all`
- Description: finds all free appointments.

Example request:
```http
  GET /api/v1/appointment/all
```

Example response:
```
HTTP/1.1 200 OK
Content-Type: application/json
[
  {
    "id": "4569dfb0-e8d9-11ed-a05b-0242ac120003",
    "start": "2023-05-01 9:00:00",
    "end": "2023-05-01 10:00:00",
    "specialistId": "bc41aa80-e8ea-11ed-a05b-0242ac120003",
    "roomId": "425",
    "price": 70
  },
  {
    "id": "1eb8e89a-e904-11ed-a05b-0242ac120003",
    "start": "2023-05-03 9:00:00",
    "end": "2023-05-03 10:00:00",
    "specialistId": "bc41aa80-e8ea-11ed-a05b-0242ac120003",
    "roomId": "444",
    "price": 65
  }
]
```

Possible errors:
| Error code | Description                |
| :-------- | :------------------------- |
| 500 Internal server error | An unexpected error occurred in the server |

### Update an appointment

- Method: `PUT`
- URL: `/api/v1/appointment/{id}`
- Description: updates an new appointment in the database.
- Parameters:

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `start` | `datetime` | Appointment start time and date |
| `end` | `datetime` | Appointment end time and date |
| `specialistId` | `UUID` | Specialist's id in the database |
| `roomId` | `int` | Appointment room |
| `price` | `int` | Appointment price |

Example request:
```http
  PUT /api/v1/appointment/bc41aa80-e8ea-11ed-a05b-0242ac120003
  {
    "start": "2023-05-01 9:00:00",
    "end": "2023-05-01 10:00:00",
    "specialistId": "4569dfb0-e8d9-11ed-a05b-0242ac120003",
    "roomId": "225",
    "price": 35
  }
```

Example response:
```
HTTP/1.1 202 Accepted
Content-Type: application/json
{
  "id": "bc41aa80-e8ea-11ed-a05b-0242ac120003",
  "start": "2023-05-01 9:00:00",
  "end": "2023-05-01 10:00:00",
  "specialistId": "4569dfb0-e8d9-11ed-a05b-0242ac120003",
  "roomId": "225",
  "price": 35
}
```

Possible errors:
| Error code | Description                |
| :-------- | :------------------------- |
| 400 Bad request | Required fields were invalid, not specified |
| 409 Conflict | This specialist or this room might be booked at that time |
| 500 Internal server error | An unexpected error occurred in the server |

### Delete appointment

- Method: `DELETE`
- URL: `/api/v1/appointment/{id}`
- Description: deletes an appointment in the database by UUID.

Example request:
```http
  DELETE /api/v1/appointment/4569dfb0-e8d9-11ed-a05b-0242ac120003
```

Example response:
```
HTTP/1.1 204 No Content
```

Possible errors:
| Error code | Description                |
| :-------- | :------------------------- |
| 404 Not found | An appointment with this id could not be found |
| 409 Conflict | Could not delete, booked appointment |
| 500 Internal server error | An unexpected error occurred in the server |

### Create a new booking

- Method: `POST`
- URL: `/api/v1/booking/create`
- Description: creates a new booking for an appointment.
- Parameters:

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `appointmentId` | `UUID` | **Required**. Appointment id |
| `userId` | `UUID` | **Required**. User id |
| `description` | `string` | **Required**. Description and comments from the user |

Example request:
```http
  POST /api/v1/appointment/create
  {
    "appointmentId": "80dd72f4-e8fd-11ed-a05b-0242ac120003",
    "userId": "8ef618be-e8fd-11ed-a05b-0242ac120003",
    "description": "Yearly checkup"
  }
```

Example response:
```
HTTP/1.1 201 Created
Content-Type: application/json
{
  "id": "4569dfb0-e8d9-11ed-a05b-0242ac120003",
  "appointmentId": "80dd72f4-e8fd-11ed-a05b-0242ac120003",
  "userId": "8ef618be-e8fd-11ed-a05b-0242ac120003",
  "description": "Yearly checkup",
  "bookingDate": "2023-05-02 09:00:05",
  "cancellationDate": null
}
```

Possible errors:
| Error code | Description                |
| :-------- | :------------------------- |
| 400 Bad request | Required fields were invalid, not specified |
| 409 Conflict | This specialist or this room might be booked at that time or this appointment might be booked |
| 500 Internal server error | An unexpected error occurred in the server |

### Get booking information

- Method: `GET`
- URL: `/api/v1/booking/{id}`
- Description: finds a booking in the database by UUID.

Example request:
```http
  GET /api/v1/booking/4569dfb0-e8d9-11ed-a05b-0242ac120003
```

Example response:
```
HTTP/1.1 200 OK
Content-Type: application/json
{
  "id": "4569dfb0-e8d9-11ed-a05b-0242ac120003",
  "appointmentId": "80dd72f4-e8fd-11ed-a05b-0242ac120003",
  "userId": "8ef618be-e8fd-11ed-a05b-0242ac120003",
  "description": "Yearly checkup",
  "bookingDate": "2023-05-02 09:00:05",
  "cancellationDate": null
}
```

Possible errors:
| Error code | Description                |
| :-------- | :------------------------- |
| 404 Not found | A booking with this id could not be found |
| 500 Internal server error | An unexpected error occurred in the server |

### Update a booking

- Method: `PUT`
- URL: `/api/v1/booking/{id}`
- Description: updates a booking entry in the database.
- Parameters:

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `description` | `string` | Description and comments from the user |
| `cancellationDate` | `datetime` | Booking cancellation time and date |

Example request:
```http
  PUT /api/v1/appointment/bc41aa80-e8ea-11ed-a05b-0242ac120003
  {
    "description": "Nutrition consult needed",
    "cancellationDate": "2023-05-02 10:00:00"
  }
```

Example response:
```
HTTP/1.1 202 Accepted
Content-Type: application/json
{
  {
  "id": "4569dfb0-e8d9-11ed-a05b-0242ac120003",
  "appointmentId": "80dd72f4-e8fd-11ed-a05b-0242ac120003",
  "userId": "8ef618be-e8fd-11ed-a05b-0242ac120003",
  "description": "Nutrition consult needed",
  "bookingDate": "2023-05-02 09:00:05",
  "cancellationDate": 2023-05-02 10:00:00,
}

}
```

Possible errors:
| Error code | Description                |
| :-------- | :------------------------- |
| 400 Bad request | Request body was invalid, not specified |
| 500 Internal server error | An unexpected error occurred in the server |
