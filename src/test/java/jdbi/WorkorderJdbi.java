package jdbi;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@RegisterForReflection
//Note: If you change order of fields,
public class WorkorderJdbi {
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
