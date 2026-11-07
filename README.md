# HR Platform – Module RH (Projet 2, G-B)

Plateforme de gestion RH des employés ingénieurs, basée sur une architecture orientée services (SOA).

## Services
| Service | Techno | API | Port |
|---|---|---|---|
| auth-service | Spring Boot | REST | 8080 |
| employee-service | Spring Boot | SOAP | 8081 |
| position-service | Spring Boot | SOAP | 8082 |
| skills-service | FastAPI | REST | 8083 |
| training-service | Spring Boot | REST | 8084 |
| api-gateway | Spring Cloud Gateway | REST | 8000 |

## Lancer le projet
(à compléter avec Docker Compose)