# Create image for postgres database
FROM postgres:12.3-alpine
# create schema on database
COPY schema.sql /docker-entrypoint-initdb.d/
