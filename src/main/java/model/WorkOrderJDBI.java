package model;
import io.quarkus.runtime.annotations.RegisterForReflection;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.sql.Date;
import java.time.Instant;
import java.util.StringJoiner;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@RegisterForReflection
//Note: If you change order of fields,
public class WorkOrderJDBI {
    public Long id;
    public Long servicePointId;
    public String eamWorkOrderId;
    public Instant createDate;
    public Instant dispatchDate;
    public String dispatchGroupCode;
    public String meterWorkTypeCode;
    public String meterServiceProjectCode;
    public String comments;
    public String instructions;
    public String status;
    public Instant addDate;
    public String addBy;
    public Instant modDate;
    public String modBy;
}
