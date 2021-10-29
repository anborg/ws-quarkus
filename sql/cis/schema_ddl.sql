drop sequence WO_SEQ;
CREATE SEQUENCE WO_SEQ START WITH 1;

drop table WO;
create table WO (
      WO_ID NUMBER(12, 0) primary key
    , SVC_PT_ID NUMBER(12, 0) NOT NULL
    , EAM_WO VARCHAR2(10 CHAR)
    , WO_DT DATE 
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

    , MKM_PROJ_CODE VARCHAR2(20 BYTE)
    , FLAGGED VARCHAR2(4 BYTE)
    , ALT_WO_ID VARCHAR2(10 BYTE)
    -- change tracking info
    , ADD_DT DATE DEFAULT SYSDATE NOT NULL
    , ADD_BY VARCHAR2(50 CHAR) DEFAULT USER

    , MOD_DT DATE
    , MOD_BY VARCHAR2(50 CHAR)
);

CREATE OR REPLACE TRIGGER WO_before_ins_upd
    BEFORE INSERT or UPDATE on WO
    REFERENCING OLD AS old_row NEW AS new_row
    FOR EACH ROW
BEGIN
    -- validate and reject
--     IF (:new_row.ADD_DT IS NOT NULL  OR  :new_row.MOD_DT IS NOT NULL)  THEN
--         RAISE_APPLICATION_ERROR(-20001, 'ADD_DT and MOD_DT are readonly. Do not attempt to insert');
--     end if;

    IF INSERTING -- if insert id should be new
    THEN
        -- when inserting MOD_BY should not be present. TODO later.
        IF :new_row.WO_ID IS NULL THEN -- insert id, if not present
            SELECT WO_seq.NEXTVAL INTO :new_row.WO_ID from DUAL;
        END IF;
        -- CREATE TS
        SELECT SYSDATE into :new_row.ADD_DT from DUAL;
    END IF;
    IF UPDATING --
    THEN
        -- when updating ADD_BY should not be present. TODO later.
        -- UPDATE TS
        select SYSDATE into :new_row.MOD_DT from DUAL;
    end if;
    --     SELECT SYS_CONTEXT ( 'USERENV', 'OS_USER')
--     INTO :new_row.lastupdatedby
--     FROM DUAL;
END;


