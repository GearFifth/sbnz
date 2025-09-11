-- psql -U postgres -f init_database.sql
CREATE DATABASE slo_guide_database;

\c slo_guide_database;

CREATE USER slo_guide_user WITH PASSWORD 'sl0gu1de';

GRANT ALL PRIVILEGES ON DATABASE slo_guide_database TO slo_guide_user;

ALTER DATABASE slo_guide_database OWNER TO slo_guide_user;