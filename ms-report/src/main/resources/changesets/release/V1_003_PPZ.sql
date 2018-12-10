--liquibase formatted sql logicalFilePath:V1_003_PPZ.sql
--changeset S.Anasov:1.3 runOnChange:true context:main
--comment ADD table PPZ
CREATE TABLE PPZ (
  id            UUID NOT NULL
                       CONSTRAINT ppz_pkey PRIMARY KEY
                       DEFAULT uuid_generate_v4(),
  agent_id      UUID CONSTRAINT fk_ppz_agent_id
                       REFERENCES AGENT (id),
  title         VARCHAR(255) NOT NULL,
  code_aladin    VARCHAR(255),
  code_pif      INTEGER NOT NULL,
  relevance_pif BOOLEAN,
  code_uk       INTEGER NOT NULL,
  relevance_uk  BOOLEAN,
  created_by    VARCHAR(100) NOT NULL,
  created_at    TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
  updated_by    VARCHAR(100) NOT NULL,
  updated_at    TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
  deleted_by    VARCHAR(100),
  deleted_at    TIMESTAMP WITHOUT TIME ZONE
)
WITH (OIDS = FALSE);

CREATE UNIQUE INDEX uc_ppz_title ON PPZ (title) WHERE deleted_at IS NULL;
CREATE UNIQUE INDEX uc_ppz_code_aladin ON PPZ (code_aladin) WHERE deleted_at IS NULL;
