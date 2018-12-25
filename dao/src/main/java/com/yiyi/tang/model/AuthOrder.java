package com.yiyi.tang.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table(name = "TD_AUTH_ORDER")
public class AuthOrder {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT LAST_INSERT_ID()")
    private Long id;

    @Column(name = "PARTNER_ID")
    private String partnerId;

    @Column(name = "MERCHANT_ORDER_NO")
    private String merchantOrderNo;

    @Column(name = "ORDER_NO")
    private String orderNo;

    @Column(name = "STATUS")
    private BigDecimal status;

    @Column(name = "AUTH_CHANNEL")
    private String authChannel;

    @Column(name = "PAYEE_FEE")
    private BigDecimal payeeFee;

    @Column(name = "PAYER_FEE")
    private BigDecimal payerFee;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "MSG")
    private String msg;

    @Column(name = "ELEMENTS")
    private String elements;

    @Column(name = "CREATE_TIME")
    private Date createTime;

    @Column(name = "UPDATE_TIME")
    private Date updateTime;
}