SELECT pg_terminate_backend(pg_stat_activity.pid)
FROM pg_stat_activity
WHERE pg_stat_activity.datname = 'slo_guide_database' AND pid <> pg_backend_pid();

DROP DATABASE IF EXISTS slo_guide_database;



SELECT pg_terminate_backend(pg_stat_activity.pid)
FROM pg_stat_activity
WHERE pg_stat_activity.usename = 'slo_guide_user' AND pid <> pg_backend_pid();

DROP USER IF EXISTS slo_guide_user;