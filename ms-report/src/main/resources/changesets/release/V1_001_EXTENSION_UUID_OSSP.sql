--liquibase formatted sql logicalFilePath:V1_001_EXTENSION_UUID_OSSP.sql
--changeset A.Pyreev:1.1 runOnChange:false context:main
--comment ADD extension uuid-ossp
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
