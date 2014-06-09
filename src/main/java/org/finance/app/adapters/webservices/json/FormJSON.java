package org.finance.app.adapters.webservices.json;

import org.finance.app.core.domain.common.Client;
import org.finance.app.core.domain.common.Money;
import org.joda.time.DateTime;

import javax.xml.bind.annotation.XmlRootElement;
import java.net.InetAddress;


@XmlRootElement
public class FormJSON {

    private Client personalData;

    private Money applyingAmount;

    private InetAddress applyingIpAddress;

    private Integer maturityInDays;

    private DateTime submissionDate;

}
