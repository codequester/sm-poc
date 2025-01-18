#!/bin/bash

#for i in {1..3}
while true
do
    curl http://frontend-service.apps.cluster-dgtvc.dgtvc.sandbox930.opentlc.com/status
    echo ""
    sleep 2s
done