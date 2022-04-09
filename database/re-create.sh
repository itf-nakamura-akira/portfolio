#!/usr/bin/bash

DATABASE=construction_dx

mysql --defaults-extra-file=./user.cnf -h construction-dx-db sys < ./docker-entrypoint-initdb.d/1.create-db.sql
mysql --defaults-extra-file=./user.cnf -h construction-dx-db $DATABASE < ./docker-entrypoint-initdb.d/2.create-table.sql
mysql --defaults-extra-file=./user.cnf -h construction-dx-db $DATABASE < ./docker-entrypoint-initdb.d/3.insert-data.sql

mysql --defaults-extra-file=./user.cnf -h construction-dx-db-test sys < ./docker-entrypoint-initdb.d/1.create-db.sql
mysql --defaults-extra-file=./user.cnf -h construction-dx-db-test $DATABASE < ./docker-entrypoint-initdb.d/2.create-table.sql
mysql --defaults-extra-file=./user.cnf -h construction-dx-db-test $DATABASE < ./docker-entrypoint-initdb.d/3.insert-data.sql
