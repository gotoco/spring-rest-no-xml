package org.finance.app.core.domain.businessprocess.loangrant;

import org.finance.app.core.domain.common.Money;

import javax.persistence.*;
import java.util.Date;

@Entity
public class GrantingOfLoanData {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name="ip")
    public String getIp() {
        return ip;
    }

    @Column(name="date_of_application")
    @Temporal(javax.persistence.TemporalType.DATE)
    public Date getDateOfApplication() {
        return dateOfApplication;
    }

    @AttributeOverrides({
            @AttributeOverride(name = "denomination", column = @Column(name = "purchaseTotalCost_denomination")),
            @AttributeOverride(name = "currencyCode", column = @Column(name = "purchaseTotalCost_currencyCode")) })
    private Money totalCost;

    private String ip;
    private Date dateOfApplication;

}
