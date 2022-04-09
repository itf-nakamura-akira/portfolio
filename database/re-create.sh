#!/usr/bin/bash

DATABASE=portfolio

mysql --defaults-extra-file=./user.cnf -h portfolio-db sys < ./docker-entrypoint-initdb.d/1.create-db.sql
mysql --defaults-extra-file=./user.cnf -h portfolio-db $DATABASE < ./docker-entrypoint-initdb.d/2.create-table.sql
mysql --defaults-extra-file=./user.cnf -h portfolio-db $DATABASE < ./docker-entrypoint-initdb.d/3.insert-data.sql

mysql --defaults-extra-file=./user.cnf -h portfolio-db-test sys < ./docker-entrypoint-initdb.d/1.create-db.sql
mysql --defaults-extra-file=./user.cnf -h portfolio-db-test $DATABASE < ./docker-entrypoint-initdb.d/2.create-table.sql
mysql --defaults-extra-file=./user.cnf -h portfolio-db-test $DATABASE < ./docker-entrypoint-initdb.d/3.insert-data.sql
