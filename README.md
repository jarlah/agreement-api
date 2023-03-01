# Agreement rest api

## How to call the agreement api

```
~  curl -X POST -d '{ "customerPid": "11233", "customerName": "Jarl", "agreementPrice": 123.233 }' -H "Content-Type: application/json" -H "X-Correlation-Id: a3dfbdf3-3fae-40e2-aa6e-de76c1220f65" http://localhost:8080/api/agreement
{"id":"1414eef5-dcfd-4b4d-9c98-f07e1ef1bab4","agreementPrice":123.233,"customerId":"6a1dfe10-fbf7-4776-aab6-95fb9b3708c4"}%
~ 
```

### TODO

- [x] Add correlationId in logging when logging anything after reaching controller
- [ ] Add api doc with openapi