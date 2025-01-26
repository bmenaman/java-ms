#!/bin/bash

# Default values
HOST=${1:-localhost}
PORT=${2:-8080}
ENDPOINT="/actuator/health"
MAX_RETRIES=30
RETRY_INTERVAL=1

echo "Checking health endpoint at http://$HOST:$PORT$ENDPOINT"

for i in $(seq 1 $MAX_RETRIES); do
    response=$(curl -s -w "%{http_code}" "http://$HOST:$PORT$ENDPOINT")
    http_code=${response: -3}
    body=${response:0:${#response}-3}
    
    if [ "$http_code" -eq 200 ]; then
        if echo "$body" | grep -q '"status":"UP"'; then
            echo "Health check succeeded after $i attempts"
            echo "Response: $body"
            exit 0
        fi
    fi
    
    echo "Attempt $i/$MAX_RETRIES failed (HTTP $http_code)"
    sleep $RETRY_INTERVAL
done

echo "Health check failed after $MAX_RETRIES attempts"
exit 1 