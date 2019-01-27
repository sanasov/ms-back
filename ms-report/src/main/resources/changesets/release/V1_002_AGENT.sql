--liquibase formatted sql logicalFilePath:V1_002_AGENT.sql
--changeset A.Pyreev:1.2 runOnChange:false context:main
--comment ADD table AGENT
CREATE TABLE AGENT (
  id                 UUID NOT NULL
                       CONSTRAINT agent_pkey PRIMARY KEY
                       DEFAULT uuid_generate_v4(),
  code               VARCHAR(3)   NOT NULL,
  short_name         VARCHAR(255) NOT NULL,
  full_name          VARCHAR(255) NOT NULL,
  legal_address      VARCHAR(255) NOT NULL,
  actual_address     VARCHAR(255) NOT NULL,
  license_number     VARCHAR(255) NOT NULL,
  license_date       DATE NOT NULL,
  license_validity   DATE,
  license_issued_by  VARCHAR(255) NOT NULL,
  created_by         VARCHAR(100) NOT NULL,
  created_at         TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
  updated_by         VARCHAR(100) NOT NULL,
  updated_at         TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
  deleted_by         VARCHAR(100),
  deleted_at         TIMESTAMP WITHOUT TIME ZONE
)
WITH (OIDS = FALSE);

CREATE UNIQUE INDEX uc_agent_code ON AGENT (code) WHERE deleted_at IS NULL;
CREATE UNIQUE INDEX uc_agent_license_number ON AGENT (license_number) WHERE deleted_at IS NULL;
