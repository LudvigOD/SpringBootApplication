
## How to run

Backend server uses Spring Boot. To start it, run:

```./gradlew :backend:bootRun```

from the project root. Then go to http://localhost:8080/api/time/startNbr/01 for a working example!


## API Documentation

### Participants API

Base URL: `/api/races/{raceId}/participants`

#### Create a Participant

**Endpoint:** `POST /api/races/{raceId}/participants`

**Description:** Adds a new participant to a race.

**Path Parameters:**
- `raceId` (int) - The ID of the race.

**Request Body:**
```json
{
  "startNbr": "string",
  "name": "string"
}
```

**Response:**
- `200 OK` on success.

---

#### Fetch All Participants

**Endpoint:** `GET /api/races/{raceId}/participants`

**Description:** Retrieves all participants for a given race.

**Path Parameters:**
- `raceId` (int) - The ID of the race.

**Response:**
```json
[
  {
    "startNbr": "string",
    "name": "string"
  }
]
```

**Response Codes:**
- `200 OK` on success.

---

#### Update a Participant

**Endpoint:** `PUT /api/races/{raceId}/participants/{participantId}`

**Description:** Updates the details of a participant.

**Path Parameters:**
- `raceId` (int) - The ID of the race.
- `participantId` (long) - The ID of the participant.

**Request Body:**
```json
{
  "startNbr": "string",
  "name": "string"
}
```

**Response Codes:**
- `200 OK` on success.
- `404 Not Found` if participant not found.

---

#### Delete a Participant

**Endpoint:** `DELETE /api/races/{raceId}/participants/{participantId}`

**Description:** Deletes a participant from a race.

**Path Parameters:**
- `raceId` (int) - The ID of the race.
- `participantId` (long) - The ID of the participant.

**Response Codes:**
- `200 OK` on success.
- `404 Not Found` if participant not found.

---

### Times API

Base URL: `/api/races/{raceId}/times`

#### Register a Time

**Endpoint:** `POST /api/races/{raceId}/times`

**Description:** Registers a new time for a participant.

**Path Parameters:**
- `raceId` (int) - The ID of the race.

**Request Body:**
```json
{
  "stationId": "integer",
  "startNbr": "string",
  "time": "ISO8601 timestamp"
}
```

**Response Codes:**
- `200 OK` on success.

---

#### Fetch All Times

**Endpoint:** `GET /api/races/{raceId}/times`

**Description:** Retrieves all times for a race, optionally filtering by station or participant.

**Path Parameters:**
- `raceId` (int) - The ID of the race.

**Query Parameters:**
- `stationId` (Optional, int) - Filter by station ID.
- `startNbr` (Optional, string) - Filter by participant start number.

**Response:**
```json
[
  {
    "stationId": "integer",
    "startNbr": "string",
    "time": "ISO8601 timestamp"
  }
]
```

**Response Codes:**
- `200 OK` on success.

---
