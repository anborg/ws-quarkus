CREATE SEQUENCE WO_SEQ INCREMENT BY 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1 NOCACHE


-- Unable to render TABLE DDL for object APPSCIS.WO with DBMS_METADATA attempting internal generator.
ALTER TABLE APPSCIS.WO
ADD CONSTRAINT WO__ADD_BY_NN CHECK
("ADD_BY" IS NOT NULL)
ENABLE
ALTER TABLE APPSCIS.WO
ADD CONSTRAINT WO__ADD_DT_NN CHECK
("ADD_DT" IS NOT NULL)
ENABLE
ALTER TABLE APPSCIS.WO
ADD CONSTRAINT WO__MOD_BY_NN CHECK
("MOD_BY" IS NOT NULL)
ENABLE
ALTER TABLE APPSCIS.WO
ADD CONSTRAINT WO__MOD_DT_NN CHECK
("MOD_DT" IS NOT NULL)
ENABLE
ALTER TABLE APPSCIS.WO
ADD CONSTRAINT WO__MTR_WRK_TP_CD_NN CHECK
("MTR_WRK_TP_CD" IS NOT NULL)
ENABLE
ALTER TABLE APPSCIS.WO
ADD CONSTRAINT WO__SVC_PT_ID_NN CHECK
("SVC_PT_ID" IS NOT NULL)
ENABLE
ALTER TABLE APPSCIS.WO
ADD CONSTRAINT WO__WO_DT_NN CHECK
("WO_DT" IS NOT NULL)
ENABLECOMMENT ON COLUMN APPSCIS.WO.WO_ID IS 'AUTO-GENERATED WORK ORDER ID (PK)';
COMMENT ON COLUMN APPSCIS.WO.SVC_PT_ID IS 'SERVICE POINT ID (FOREIGN KEY FROM SVC_PT; REQUIRED)';
COMMENT ON COLUMN APPSCIS.WO.EAM_WO IS 'EAM WORK ORDER (PK)';
COMMENT ON COLUMN APPSCIS.WO.WO_DT IS 'DATE FIELD ACTIVITY CREATED';
COMMENT ON COLUMN APPSCIS.WO.WO_DSPTCH_DT IS 'DATE WORK SHOULD START';
COMMENT ON COLUMN APPSCIS.WO.MTR_WRK_TP_CD IS 'WORK ORDER TYPE CODE (FOREIGN KEY FROM REF_MTR_WRK_TP)';
COMMENT ON COLUMN APPSCIS.WO.DSPTCH_GRP_CD IS 'DISPATCH GROUP CODE (FOREIGN KEY FROM REF_DSPTCH_GRP)';
COMMENT ON COLUMN APPSCIS.WO.FLD_ACT_ID IS 'UNIQUE CCNB FIELD ACTIVITY ID';
COMMENT ON COLUMN APPSCIS.WO.CCNB_FA_PR_CD IS 'WORK PRIORITY CODE (FOREIGN KEY FROM REF_CCNB_FA_PR)';
COMMENT ON COLUMN APPSCIS.WO.CCNB_FA_TP_CD IS 'FIELD ACTIVITY TYPE CODE (FOREIGN KEY FROM REF_CCNB_FA_TP)';
COMMENT ON COLUMN APPSCIS.WO.CCNB_FA_STAT_CD IS 'FIELD ACTIVITY STATUS CODE (FOREIGN KEY FROM REF_CCNB_FA_STAT)';
COMMENT ON COLUMN APPSCIS.WO.DSPTCH_GRP_EXT_CD IS 'FIELD ACTIVITY DISPATCH GROUP CODE (FOREIGN KEY FROM REF_DSPTCH_GRP_EXT)';
COMMENT ON COLUMN APPSCIS.WO.COMMENTS IS 'FIELD ACTIVITY COMMENTS';
COMMENT ON COLUMN APPSCIS.WO.INSTRUCTIONS IS 'FIELD ACTIVITY DETAILED INSTRUCTIONS';
COMMENT ON COLUMN APPSCIS.WO.NTG_PROJ_CD IS 'NEPTUNE PROJECT CODE ( FOREIGN KEY FROM REF_NTG_PROJ)';
COMMENT ON COLUMN APPSCIS.WO.ADD_DT IS 'DATE/TIME RECORD ADDED (REQUIRED)';
COMMENT ON COLUMN APPSCIS.WO.ADD_BY IS 'USER ID OF PERSON WHO ADDED THE RECORD (REQUIRED)';ALTER TABLE APPSCIS.WO
ADD CONSTRAINT WO__U1 UNIQUE
(
  FLD_ACT_ID
)
USING INDEX APPSCIS.WO__U1
ENABLECREATE UNIQUE INDEX APPSCIS.WO_PK ON APPSCIS.WO (WO_ID ASC)
LOGGING
TABLESPACE APPSCIS_DATA
PCTFREE 10
INITRANS 2
STORAGE
(
  INITIAL 65536
  NEXT 1048576
  MINEXTENTS 1
  MAXEXTENTS UNLIMITED
  BUFFER_POOL DEFAULT
)
NOPARALLEL
CREATE UNIQUE INDEX APPSCIS.WO__U1 ON APPSCIS.WO (FLD_ACT_ID ASC)
LOGGING
TABLESPACE APPSCIS_DATA
PCTFREE 10
INITRANS 2
STORAGE
(
  INITIAL 65536
  NEXT 1048576
  MINEXTENTS 1
  MAXEXTENTS UNLIMITED
  BUFFER_POOL DEFAULT
)
NOPARALLELCREATE TABLE APPSCIS.WO
(
  WO_ID NUMBER(12, 0) NOT NULL
, SVC_PT_ID NUMBER(12, 0) NOT NULL
, EAM_WO VARCHAR2(10 CHAR)
, WO_DT DATE DEFAULT SYSDATE NOT NULL
, WO_DSPTCH_DT DATE
, MTR_WRK_TP_CD VARCHAR2(4 CHAR) DEFAULT 'ZZZZ' NOT NULL
, WO_STAT_CD VARCHAR2(2 CHAR)
, DSPTCH_GRP_CD VARCHAR2(10 CHAR)
, CD_SRC VARCHAR2(10 CHAR)
, FLD_ACT_ID VARCHAR2(10 CHAR)
, CCNB_FA_PR_CD VARCHAR2(2 CHAR)
, CCNB_FA_TP_CD VARCHAR2(8 CHAR)
, CCNB_FA_STAT_CD VARCHAR2(2 CHAR)
, DSPTCH_GRP_EXT_CD VARCHAR2(8 CHAR)
, COMMENTS VARCHAR2(1000 CHAR)
, INSTRUCTIONS VARCHAR2(4000 CHAR)
, NTG_PROJ_CD VARCHAR2(4 CHAR)
, VERSION NUMBER(5, 0) DEFAULT 1
, EWO_PROCESS_IND VARCHAR2(4 CHAR)
, EWO_STAT_CD VARCHAR2(50 CHAR)
, EWO_FEEDBACK_COMMENT VARCHAR2(4000 CHAR)
, MOD_DT DATE DEFAULT SYSDATE NOT NULL
, MOD_BY VARCHAR2(50 CHAR) DEFAULT USER NOT NULL
, ADD_DT DATE DEFAULT SYSDATE NOT NULL
, ADD_BY VARCHAR2(50 CHAR) DEFAULT USER NOT NULL
, MKM_PROJ_CODE VARCHAR2(20 BYTE)
, FLAGGED VARCHAR2(4 BYTE)
, ALT_WO_ID VARCHAR2(10 BYTE)
)
LOGGING
TABLESPACE APPSCIS_DATA
PCTFREE 10
INITRANS 1
STORAGE
(
  INITIAL 65536
  NEXT 1048576
  MINEXTENTS 1
  MAXEXTENTS UNLIMITED
  BUFFER_POOL DEFAULT
)
NOCOMPRESS
NO INMEMORY
NOPARALLELALTER TABLE APPSCIS.WO
ADD CONSTRAINT WO__SVC_PT_FK FOREIGN KEY
(
  SVC_PT_ID
)
REFERENCES APPSCIS.SVC_PT
(
  SVC_PT_ID
)
ENABLEALTER TABLE APPSCIS.WO
ADD CONSTRAINT WO_PK PRIMARY KEY
(
  WO_ID
)
USING INDEX APPSCIS.WO_PK
ENABLECOMMENT ON TABLE APPSCIS.WO IS 'FIELD ACTIVITY DATA FROM CCNB'
