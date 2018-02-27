# Checkout
Example of restful service running on Spring & MongoDB

*System requirements - MongoDB operating on port 27017*

### CURL Examples

* add item
```
curl -vd '{"code":"C","name":"Product C","price":"300"}' -H "Content-Type: application/json" -X POST http://localhost:8080/items
```
* list items
```
curl -w "\n" -s http://localhost:8080/items
```

* add promotion of type "Buy N*productX for Y"
```
curl -vd '{"name":"Buy 3 A for 70"}' -H "Content-Type: application/json" -X POST http://localhost:8080/promotions
curl -vd '{"promotionId":"5a92bd358c93eb26dcd8efa1", "itemId":"5a92be708c93eb26dcd8efa6", "quantity": 3, "overallPriceForQuantity": 70, "dateFrom" : "2018-02-24"}' -H "Content-Type: application/json" -X POST http://localhost:8080/promotions/5a92bd358c93eb26dcd8efa1/definition
```
* add promotion of type "Buy N*productX + M*productY for Z"
```
curl -vd '{"name":"Buy 2A + 1C for 90"}' -H "Content-Type: application/json" -X POST http://localhost:8080/promotions
curl -vd '{"promotionId":"5a92cd468c93eb2fbc34f48b", "itemId":"5a92be708c93eb26dcd8efa6", "quantity": 2, "dateFrom" : "2018-02-24"}' -H "Content-Type: application/json" -X POST http://localhost:8080/promotions/5a92cd468c93eb2fbc34f48b/definition
curl -vd '{"promotionId":"5a92cd468c93eb2fbc34f48b", "itemId":"5a92beca8c93eb26dcd8efa8", "quantity": 1, "dateFrom" : "2018-02-24"}' -H "Content-Type: application/json" -X POST http://localhost:8080/promotions/5a92cd468c93eb2fbc34f48b/definition
```

* create empty cart
```
curl -vX POST http://localhost:8080/carts
```
* list unpaid carts
```
curl -w "\n" -s http://localhost:8080/carts/
```

* add item to cart
```
curl -vX PUT http://localhost:8080/carts/5a8df5d08c93eb0d68e2cdb2/5a8f36b58c93eb3340553bd1
```
* show cart
```
curl -w "\n" -s http://localhost:8080/carts/5a8df5d08c93eb0d68e2cdb2
```
* display cart checkout
```
curl -w "\n" -s http://localhost:8080/carts/5a8df5d08c93eb0d68e2cdb2/checkout
```

## Sale definitions
There are two types of sales available:
* Simple: `"Buy N * productX for Y"`
* Bundle: `"Buy N1 * productX1 + N2 * productX2 + ... for Y"`

Attached pdf with the database model used: [checkout-data-model.pdf](doc/checkout-data-model.pdf)

The model enables flexible defining of Sales of the aforementioned types.

When defining the sales definitions you need to pay attention to the restrictions / rules:
- There can be only one `PromotionDefinition` component for the "Simple" sale type
`promotionDefinition.overallPriceForQuantity! = null`
- There can be many `PromotionDefinitions` for everyone for the "Bundle" sale type
`promotionDefinition.overallPriceForQuantity == null`. The price is defined in related to Promotion Bundle.price
The priority for billing is Simple type promotions, in order of the largest volumes (quantities), followed by Bundle.

## Test data
Test data is available in `mongodb/dump`

Restore test data using command:
```
mongorestore --host localhost --port 27017 mongodb/dump
```