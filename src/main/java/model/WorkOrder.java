package model;
import javax.persistence.*;


@Entity
@Table(name = "WORK_ORDERS")
@NamedQuery(name = "WorkOrder.findAll", query = "SELECT wo FROM WorkOrder wo ORDER BY wo.id", hints = @QueryHint(name = "org.hibernate.cacheable", value = "true"))
@Cacheable
public class WorkOrder {
    @Id

    @SequenceGenerator(name = "work_order_sequence", sequenceName = "work_order_sequence", allocationSize = 1, initialValue = 10)
    @GeneratedValue(generator = "work_order_sequence")
    @Column(length = 10, unique = true)
    public Integer id;

    @Column(length = 100)
    public String description;

    @Column(length = 10)
    public String status;


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("WorkOrder{");
        sb.append("id=").append(id);
        sb.append(", description='").append(description).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
