package model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "TRANSACTIONS")
public class Transaction {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "TRANSACTION_ID", nullable = false, updatable = false)
  private long transactionId;
  private long senderAccountId;
  private long receiverAccountId;
  private BigDecimal transactionAmount;

  public Transaction() {
  }

  public Transaction(long senderAccountId, long receiverAccountId, BigDecimal transactionAmount) {
    this.senderAccountId = senderAccountId;
    this.receiverAccountId = receiverAccountId;
    this.transactionAmount = transactionAmount;
  }

  public long getSenderAccountId() {
    return senderAccountId;
  }

  public void setSenderAccountId(long senderAccountId) {
    this.senderAccountId = senderAccountId;
  }

  public long getReceiverAccountId() {
    return receiverAccountId;
  }

  public void setReceiverAccountId(long receiverAccountId) {
    this.receiverAccountId = receiverAccountId;
  }

  public BigDecimal getTransactionAmount() {
    return transactionAmount;
  }

  public void setTransactionAmount(BigDecimal transactionAmount) {
    this.transactionAmount = transactionAmount;
  }

  public long getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(long transactionId) {
    this.transactionId = transactionId;
  }
}
