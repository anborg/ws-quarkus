package jdbi;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;

public class WorkorderMapper implements RowMapper<WorkorderJdbi> {

    @Override
    public WorkorderJdbi map(ResultSet rs, StatementContext ctx) throws SQLException {

        Long woId = rs.getLong("WO_ID");
        Long servicePtId = rs.getLong("SVC_PT_ID");
        String eamWoId = rs.getString("EAM_WO");
        Instant createDate = null;
        Instant dispatchDate = null;
        String dispatchGroupCode = null;
        String meterWorkTypeCode = null;
        String meterServiceProjectCode = null;
        String comments = null;
        String instructions = null;
        String status = null;
        Instant addDate = null;
        String addBy = null;
        Instant modDate = null;
        String modBy = null;


        return new WorkorderJdbi(
                woId,
                servicePtId,
                eamWoId,
                createDate,
                dispatchDate,
                dispatchGroupCode,
                meterWorkTypeCode,
                meterServiceProjectCode,
                comments,
                instructions,
                status,
                addDate,
                addBy,
                modDate,
                modBy
        );
    }
}