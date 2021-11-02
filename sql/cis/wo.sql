




--136517
-- TEST1 : Should throw: ADD_DT and MOD_DT are readonly. Do not attempt to insert
INSERT INTO WO (
    WO_ID,SVC_PT_ID,MTR_WRK_TP_CD
    ,WO_DT,DSPTCH_GRP_CD,WO_STAT_CD
    ,NTG_PROJ_CD,INSTRUCTIONS,COMMENTS
    ,ADD_DT,ADD_BY,MOD_DT
    ,MOD_BY
)  VALUES (
WO_SEQ.nextval,136517,'SVCN'
,to_date('20210626000000','yyyyMMddHH24miss') ,'NEPTUNE','N'
,'A399','Testing Work Order Instructions','Testing Work Order Comments'
,to_date('20211026143157','yyyyMMddHH24miss') ,'APW',to_date('20211026143157','yyyyMMddHH24miss') ,'APW'
);
-- TEST2 : Should throw: ADD_DT and MOD_DT are readonly. Do not attempt to insert
INSERT INTO WO (
    WO_ID,SVC_PT_ID,MTR_WRK_TP_CD
   ,WO_DT,DSPTCH_GRP_CD,WO_STAT_CD
   ,NTG_PROJ_CD,INSTRUCTIONS,COMMENTS
   ,ADD_BY --,MOD_BY
)  VALUES (
  WO_SEQ.nextval,136517,'SVCN'
  ,to_date('20210626000000','yyyyMMddHH24miss') ,'NEPTUNE','N'
  ,'A399','Testing Work Order Instructions','Testing Work Order Comments'
  ,'APW' --,'APW'
);

update wo set EAM_WO = '111' where WO_ID = 13;

delete from WO wo where wo.WO_ID >1;
commit;



select WO_ID, COMMENTS, ADD_DT, MOD_DT from WO ;


-- where WO_DT >= to_date('2021-06-01','yyyy-MM-dd');

select * from WO where WO_DT >= to_date('2021-07-01','yyyy-MM-dd');

select * from CIS_API_USER;