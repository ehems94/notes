#!/bin/bash

OTEL_VERSION="1.32.1"
AGENT_FILE="opentelemetry-javaagent.jar"

if [ ! -f "$AGENT_FILE" ]; then
    echo "Downloading OpenTelemetry Java agent version $OTEL_VERSION..."
    curl -L "https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v${OTEL_VERSION}/${AGENT_FILE}" -o "$AGENT_FILE"
    echo "Download complete!"
else
    echo "OpenTelemetry Java agent already exists."
fi
