--136517

INSERT INTO WO (
WO_ID,SVC_PT_ID,MTR_WRK_TP_CD
,WO_DT,DSPTCH_GRP_CD,WO_STAT_CD
,NTG_PROJ_CD,INSTRUCTIONS,COMMENTS
,ADD_DT,ADD_BY,MOD_DT
,MOD_BY
)  VALUES (
WO_SEQ.nextval,136517,'SVCN'
,to_date('20211026000000','yyyyMMddHH24miss') ,'NEPTUNE','N'
,'A399','Testing Work Order Instructions','Testing Work Order Comments'
,to_date('20211026143157','yyyyMMddHH24miss') ,'APW',to_date('20211026143157','yyyyMMddHH24miss') ,'APW'
);


select * from WO where WO_DT >= to_date('2021-06-01','yyyy-MM-dd');
select * from WO where WO_DT >= to_date('2021-07-01','yyyy-MM-dd');