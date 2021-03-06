package hr.pishe.domain.entity.jpa;

import hr.pishe.domain.enumeration.InvoiceStatus;
import hr.pishe.domain.enumeration.PaymentType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A InvoiceEntity.
 */
@Entity
@Table(name = "invoice")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class InvoiceEntity extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type")
    private PaymentType paymentType;

    @Column(name = "payment_date")
    private ZonedDateTime paymentDate;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "tax")
    private Long tax;

    @Column(name = "discount")
    private Long discount;

    @Column(name = "total")
    private Long total;

    @Column(name = "tracking_code")
    private String trackingCode;

    @Column(name = "reference_id")
    private String referenceId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private InvoiceStatus status;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
    private Set<InvoiceItemEntity> invoiceItem = new HashSet<>();

    @ManyToOne
    private CompanyEntity company;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public ZonedDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(ZonedDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getTax() {
        return tax;
    }

    public void setTax(Long tax) {
        this.tax = tax;
    }

    public Long getDiscount() {
        return discount;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public String getTrackingCode() {
        return trackingCode;
    }

    public void setTrackingCode(String trackingCode) {
        this.trackingCode = trackingCode;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }

    public Set<InvoiceItemEntity> getInvoiceItem() {
        return invoiceItem;
    }

    public void setInvoiceItem(Set<InvoiceItemEntity> invoiceItem) {
        this.invoiceItem = invoiceItem;
    }

    public CompanyEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InvoiceEntity invoiceEntity = (InvoiceEntity) o;
        if (invoiceEntity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), invoiceEntity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InvoiceEntity{" +
            "id=" + id +
            ", paymentType=" + paymentType +
            ", paymentDate=" + paymentDate +
            ", amount=" + amount +
            ", tax=" + tax +
            ", discount=" + discount +
            ", total=" + total +
            ", trackingCode='" + trackingCode + '\'' +
            ", referenceId='" + referenceId + '\'' +
            ", status=" + status +
            ", company=" + company +
            '}';
    }
}
