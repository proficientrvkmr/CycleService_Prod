package com.orobind.core.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;

@Entity
@Table(name = "transaction", catalog = "orobind")
public class Transaction implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer           id;
    private Order      order;
    private String            code;
    private String            paymentMode;
    private String            paymentModeSubtype;
    private Integer           paymentGatewayId;
    private String            pgOutgoingParams;
    private String            pgIncomingParams;
    private String            pgResponseMessage;
    private int               paymentAmount;
    private boolean           captured;
    private boolean           pendingUserResponse;
    private String            paymentStatus;
    private Date              created;
    private Date              updated;
    private String            sessionFetchUrl;
    private Set<SessionTransactionMapping> sessionTransactionMapping = new HashSet<SessionTransactionMapping>();
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", nullable = false)
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	
	@Column(name = "payment_mode")
	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	
	@Column(name = "payment_mode_subtype")
	public String getPaymentModeSubtype() {
		return paymentModeSubtype;
	}
	public void setPaymentModeSubtype(String paymentModeSubtype) {
		this.paymentModeSubtype = paymentModeSubtype;
	}
	/*public PaymentGateway getPaymentGateway() {
		return paymentGateway;
	}
	public void setPaymentGateway(PaymentGateway paymentGateway) {
		this.paymentGateway = paymentGateway;
	}*/
	
	@Column(name = "pg_outgoing_params")
	public String getPgOutgoingParams() {
		return pgOutgoingParams;
	}
	public void setPgOutgoingParams(String pgOutgoingParams) {
		this.pgOutgoingParams = pgOutgoingParams;
	}
	
	@Column(name = "pg_incoming_params")
	public String getPgIncomingParams() {
		return pgIncomingParams;
	}
	public void setPgIncomingParams(String pgIncomingParams) {
		this.pgIncomingParams = pgIncomingParams;
	}
	
	@Column(name = "pg_response_message")
	public String getPgResponseMessage() {
		return pgResponseMessage;
	}
	public void setPgResponseMessage(String pgResponseMessage) {
		this.pgResponseMessage = pgResponseMessage;
	}
	
	@Column(name = "payment_amount")
	public int getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(int paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	
	@Column(name = "captured")
	public boolean isCaptured() {
		return captured;
	}
	public void setCaptured(boolean captured) {
		this.captured = captured;
	}
	
	@Column(name = "pending_user_response")
	public boolean isPendingUserResponse() {
		return pendingUserResponse;
	}
	public void setPendingUserResponse(boolean pendingUserResponse) {
		this.pendingUserResponse = pendingUserResponse;
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
	
	@Column(name = "code", unique = true, length = 45)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	@Column(name = "payment_gateway_id")
	public Integer getPaymentGatewayId() {
        return paymentGatewayId;
    }
    public void setPaymentGatewayId(Integer paymentGatewayId) {
        this.paymentGatewayId = paymentGatewayId;
    }
    @Column(name = "payment_status")
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "transaction", cascade = { CascadeType.ALL })
    public Set<SessionTransactionMapping> getSessionTransactionMapping() {
        return sessionTransactionMapping;
    }
    public void setSessionTransactionMapping(Set<SessionTransactionMapping> sessionTransactionMapping) {
        this.sessionTransactionMapping = sessionTransactionMapping;
    }
    
    @Column(name = "session_fetch_url")
    public String getSessionFetchUrl() {
        return sessionFetchUrl;
    }
    public void setSessionFetchUrl(String sessionFetchUrl) {
        this.sessionFetchUrl = sessionFetchUrl;
    }
}
