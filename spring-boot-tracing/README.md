# Elastic Stack Setup for Application Monitoring

This setup includes Elasticsearch, Kibana, and APM Server for comprehensive application monitoring, logging, and tracing.

## Components

- Elasticsearch (port 9200): Database and search engine
- Kibana (port 5601): Visualization and management UI
- APM Server (port 8200): Application performance monitoring

## Usage

1. Start the stack:
```bash
docker compose up -d
```

2. Access the services:
- Kibana: http://localhost:5601
- Elasticsearch: http://localhost:9200
- APM Server: http://localhost:8200

## Application Configuration

To connect your application to the APM server, use these settings:

```properties
elastic.apm.service_name=your-service-name
elastic.apm.server_url=http://localhost:8200
elastic.apm.environment=development
```

## Health Check

The stack includes health checks to ensure Elasticsearch is running before starting dependent services.
