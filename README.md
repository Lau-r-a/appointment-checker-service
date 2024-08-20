# appointment-checker-service
backend service for appointment checker

## Getting started

### Launching the service
Prerequisite: docker and docker compose installed
1. Fill values in ``example.env`` and rename to ``.env``. ``MONGO_DB_URI`` doesn't have to be changed since its not used when run with docker-compose.
2. Run with ``docker-compose up``

Default Configuration:
* API Spec: ``http://localhost:8080/api-docs``
* Open API UI: ``http://localhost:8080/swagger-ui/index.html``

### Authentication Flow in OpenApi UI

1. Open Browser Networking Debugger
2. Login using ``security_auth`` Oauth2 Flow
3. Read accessToken from Response in Networking Debugger
4. Set accessToken in ``Discord Bearer Authentication`` as value
5. Perform requests