﻿# item_market

```sql
create table item_market
(
    id               int  null,
    item             text null,
    amount           int  null,
    price            int  null,
    item_seller_uuid TEXT null,
    constraint id
        primary key (id)
);
```
