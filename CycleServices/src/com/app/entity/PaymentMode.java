package com.app.entity;


import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * PaymentMode generated by hbm2java
 */
@Entity
@Table(name = "payment_mode", catalog = "orobind", uniqueConstraints = @UniqueConstraint(columnNames = "code"))
public class PaymentMode implements java.io.Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = 7524337747386224233L;

	public enum Type {
		CREDIT_CARD("cc"), DEBIT_CARD("dc"), NETBANKING("nb"), CASH_ON_DELIVERY(
				"cd"), IVR_CUSTOMER_CARE("ic"), MOBILE_CREDIT_CARD("mc"), MOBILE_DEDIT_CARD(
				"md"), DOCOMO_PREPAID_POSTPAID("pp"), EMI_OPTIONS("em"), ANDROID_CREDIT_CARD(
				"ac"), ANDROID_DEBIT_CARD("ad");
		private String code;

		private Type(String code) {
			this.code = code;
		}

		public String code() {
			return this.code;
		}
	}

	private Integer id;
	private PaymentGateway paymentGateway;
	private PaymentGateway algoPaymentGateway;
	private String code;
	private String displayName;
	private boolean enabled;
	private String subtypeDisplayText;
	private String allowedGateways;
	private String helpText;
	private boolean display;
	private String deviceType;
	private Date created;
	private Date updated;

	private Set<PaymentModeSubtype> paymentModeSubtypes = new HashSet<PaymentModeSubtype>();

	public PaymentMode() {
	}

	public PaymentMode(PaymentGateway paymentGateway, String code,
			String displayName, boolean enabled) {
		this.paymentGateway = paymentGateway;
		this.code = code;
		this.displayName = displayName;
		this.enabled = enabled;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "payment_gateway_id", nullable = false)
	public PaymentGateway getPaymentGateway() {
		return this.paymentGateway;
	}

	public void setPaymentGateway(PaymentGateway paymentGateway) {
		this.paymentGateway = paymentGateway;
	}

	public void setAlgoPaymentGateway(PaymentGateway algoPaymentGateway) {
		this.algoPaymentGateway = algoPaymentGateway;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "algo_payment_gateway_id", nullable = false)
	public PaymentGateway getAlgoPaymentGateway() {
		return algoPaymentGateway;
	}

	@Column(name = "code", unique = true, nullable = false, length = 2)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "display_name", nullable = false, length = 100)
	public String getDisplayName() {
		return this.displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@Column(name = "enabled", nullable = false)
	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setPaymentModeSubtypes(
			Set<PaymentModeSubtype> paymentModeSubtypes) {
		this.paymentModeSubtypes = paymentModeSubtypes;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "paymentMode")
	@OrderBy("priority")
	public Set<PaymentModeSubtype> getPaymentModeSubtypes() {
		return paymentModeSubtypes;
	}

	public void setSubtypeDisplayText(String subtypeDisplayText) {
		this.subtypeDisplayText = subtypeDisplayText;
	}

	@Column(name = "subtype_display_text", nullable = false, length = 100)
	public String getSubtypeDisplayText() {
		return subtypeDisplayText;
	}

	@Column(name = "allowed_gateways", length = 200)
	public String getAllowedGateways() {
		return this.allowedGateways;
	}

	public void setAllowedGateways(String allowedGateways) {
		this.allowedGateways = allowedGateways;
	}

	public void setHelpText(String helpText) {
		this.helpText = helpText;
	}

	@Column(name = "help_text", nullable = false, length = 500)
	public String getHelpText() {
		return helpText;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("[id: " + id);
		str.append(", code: " + code);
		str.append(", displayname: " + displayName);
		str.append(", enaled: " + enabled);
		str.append(", paymentGateway: " + paymentGateway);
		str.append(", algoPaymentGateway: " + algoPaymentGateway);
		str.append(", paymentModeSubtypes: [");
		for (PaymentModeSubtype subType : paymentModeSubtypes) {
			str.append(subType.getCode() + ",");
		}
		str.append("]]");
		return str.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PaymentMode other = (PaymentMode) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

	public boolean isDisplay() {
		return display;
	}

	public void setDisplay(boolean display) {
		this.display = display;
	}

	@Column(name = "device_type")
	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	
	@Column(name = "created")
	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@Column(name = "updated")
	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}
}
