package model;
//import io.quarkus.hibernate.reactive.panache.PanacheEntity;
//import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.sql.Date;
import java.time.Instant;
import java.util.StringJoiner;


@Entity
@Table(name = "WO")
@NamedQuery(name = "WorkOrder.findActionable"
        , query = "FROM Workorder wo WHERE wo.createDate  >=  to_date(: sinceDate,'yyyy-MM-dd')  " +
        "AND (wo.status = 'N' or wo.status = 'P')" +
        " ORDER BY wo.id"
        , hints = @QueryHint(name = "org.hibernate.cacheable", value = "false")) //trunc(:sinceDate - 1,'DD')
@NamedQuery(name = "WorkOrder.findById", query = "FROM Workorder wo WHERE wo.id = : id")
//@NamedQuery(name = "WorkOrder.updateEamId", query = "Workorder SET eamId = : eamId WHERE id = : id")

@Cacheable
public class Workorder { //extends PanacheEntityBase {
    @Id
    @GeneratedValue(generator = "WO_SEQ")
    @Min(value = 0L, message = "The value must be positive")
    @Column(name = "WO_ID", length = 12, unique = true)
    public Long id;
    @Min(value = 0L, message = "The value must be positive")
    @Column(name = "SVC_PT_ID", length = 12)
    public Long servicePointId;

    @Column(name = "EAM_WO", length = 12)
    public String eamId;

    @Column(name = "WO_DT")//, insertable = false
    public Instant createDate;

    @Column(name = "WO_DSPTCH_DT")
    public Date dispatchDate;

    @Column(name = "DSPTCH_GRP_CD", length = 10)
    public String dispatchGroupCode;

    @Column(name = "MTR_WRK_TP_CD", length = 12)
    public String meterWorkTypeCode;

    @Column(name = "NTG_PROJ_CD", length = 4)
    public String meterServiceProjectCode;

    @Column(length = 1000)
    public String comments;

    @Column(length = 4000)
    public String instructions;


    @Column(name = "WO_STAT_CD", length = 10)
    public String status;


    @Column(name = "ADD_DT")//, insertable = false
    public Instant addDate;

    @Column(name = "ADD_BY") //, insertable = false
    public String addBy;

    @Column(name = "MOD_DT", insertable = false, updatable = false)//
    public Instant modDate;

    @Column(name = "MOD_BY") //, insertable = false
    public String modBy;

    @Override
    public String toString() {
        return new StringJoiner(", ", Workorder.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("servicePointId=" + servicePointId)
                .add("eamWorkOrderId='" + eamId + "'")
                .add("createDate=" + createDate)
                .add("dispatchDate=" + dispatchDate)
                .add("dispatchGroupCode='" + dispatchGroupCode + "'")
                .add("meterWorkTypeCode='" + meterWorkTypeCode + "'")
                .add("meterServiceProjectCode='" + meterServiceProjectCode + "'")
                .add("comments='" + comments + "'")
                .add("instructions='" + instructions + "'")
                .add("status='" + status + "'")
                .add("addDate=" + addDate)
                .add("addBy='" + addBy + "'")
                .add("modDate=" + modDate)
                .add("modBy='" + modBy + "'")
                .toString();
    }
}
