# Agreement rest api

## How to call the agreement api

```
~  curl -X POST -d '{ "customerName": "Jarl", "agreementPrice": 123.233 }' -H "Content-Type: application/json" http://localhost:8080/agreement
{"agreementId":"30afe2a6-618a-4237-b918-60c7700eb8ee","agreementPrice":123.233,"customer":{"name":"Jarl"}}%
~ 
```