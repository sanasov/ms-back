--liquibase formatted sql logicalFilePath:V1_004_BRANCH.sql
--changeset I.Obruchnev:1.4 runOnChange:false context:main
--comment add branch tables
CREATE TABLE BRANCH (
  id                   UUID                        NOT NULL
    PRIMARY KEY                                             DEFAULT uuid_generate_v4(),
  name                 VARCHAR(256)                NOT NULL,
  employee_Id          VARCHAR(10)                 NOT NULL,
  city                 VARCHAR(128)                NOT NULL,
  address              VARCHAR(512)                NOT NULL,
  contacts             JSONB,
  time_zone            TEXT                        NOT NULL,
  work_time            TEXT                        NOT NULL,
  cash_service         BOOLEAN                     NOT NULL,
  get_card_possibility BOOLEAN                     NOT NULL,
  marketing_name       TEXT                        NOT NULL,
  format               TEXT                        NOT NULL,
  head_position_id     VARCHAR(32),

  created_by           VARCHAR(100)                NOT NULL,
  created_at           TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
  updated_by           VARCHAR(100)                NOT NULL,
  updated_at           TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
  deleted_by           VARCHAR(100),
  deleted_at           TIMESTAMP WITHOUT TIME ZONE
)
WITH (OIDS = FALSE
);

CREATE INDEX uc_branch_name
  ON BRANCH (name) WHERE deleted_at IS NULL;

CREATE TABLE KKO (
  id         UUID                        NOT NULL
    PRIMARY KEY                                   DEFAULT uuid_generate_v4(),
  branch_id  UUID                        NOT NULL
    CONSTRAINT fk_kko_branch_id
    REFERENCES BRANCH (id),
  code       TEXT                        NOT NULL,
  name       TEXT                        NOT NULL,
  work_time  TEXT                        NOT NULL,

  created_by VARCHAR(100)                NOT NULL,
  created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
  updated_by VARCHAR(100)                NOT NULL,
  updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
  deleted_by VARCHAR(100),
  deleted_at TIMESTAMP WITHOUT TIME ZONE
)
WITH (OIDS = FALSE
);

CREATE UNIQUE INDEX uc_kko_code
  ON KKO (code) WHERE deleted_at IS NULL;
