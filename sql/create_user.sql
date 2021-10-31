
alter session set "_ORACLE_SCRIPT"=true;

drop user "integ";
SELECT banner FROM v$version WHERE ROWNUM = 1;

CREATE USER integ IDENTIFIED BY integ123;

-- GRANT CREATE TABLE TO "integ";
GRANT ALL PRIVILEGES TO integ;