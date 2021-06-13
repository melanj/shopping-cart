#!/bin/bash

# list products, pageable parameters are supported in this API, default size is 20
curl -c ./user1_cookie.txt -u user1:user1Pass  'http://localhost:8080/api/products' 2>/dev/null | jq

# the first customer add one product to his shopping cart.
curl -c ./user1_cookie.txt -u user1:user1Pass  'http://localhost:8080/api/mycart' 2>/dev/null | jq
curl -b ./user1_cookie.txt -X POST -H "Content-Type: application/json" -d "{\"quantity\": 2,\"product\": {\"id\": 97}}" 'http://localhost:8080/api/mycart/items' -v
curl -b ./user1_cookie.txt 'http://localhost:8080/api/mycart' 2>/dev/null | jq

# the second customer add two different products, with different quantities to his shopping cart.
curl -c ./user2_cookie.txt -u user2:user2Pass  'http://localhost:8080/api/mycart' 2>/dev/null | jq
curl -b ./user2_cookie.txt -X POST -H "Content-Type: application/json" -d "{\"quantity\": 5,\"product\": {\"id\": 14}}" 'http://localhost:8080/api/mycart/items' -v
curl -b ./user2_cookie.txt -X POST -H "Content-Type: application/json" -d "{\"quantity\": 6,\"product\": {\"id\": 28}}" 'http://localhost:8080/api/mycart/items' -v
curl -b ./user2_cookie.txt 'http://localhost:8080/api/mycart' 2>/dev/null | jq

