package ex04;

import java.util.UUID;

public class Transaction {
    public enum TransferCategory {
        DEBIT, CREDIT
    }
    private UUID id;
    private User recipient;
    private User sender;
    private TransferCategory transferCategory;
    private int amount;

    public Transaction(User sender, User recipient, TransferCategory category, int amount, UUID id) {
        this.id = id;
        this.sender = sender;
        this.recipient = recipient;
        this.transferCategory = category;
        this.amount = amount;
    }

    public void setAmount(int amount) {
        if ((transferCategory == Transaction.TransferCategory.DEBIT && amount < 0) || (transferCategory == Transaction.TransferCategory.CREDIT && amount > 0)) {
            throw new IllegalArgumentException("DEBIT requires positive : CREDIT requires negative amount");
        }
        if ((transferCategory == Transaction.TransferCategory.DEBIT && sender.getBalance() < amount) ||
                (transferCategory == Transaction.TransferCategory.CREDIT && (recipient.getBalance() + amount) < 0)) {
            throw new IllegalArgumentException("Sender does not have sufficient balance for the transaction");
        }
        this.amount = amount;
        if (transferCategory == Transaction.TransferCategory.DEBIT) {
            sender.setBalance(sender.getBalance() - amount);
            recipient.setBalance(recipient.getBalance() + amount);
        } else {
            recipient.setBalance(recipient.getBalance() + amount);
            sender.setBalance(sender.getBalance() - amount);
        }
    }

    @Override
    public String toString() {
        String sign = this.transferCategory == TransferCategory.DEBIT ? "+" : "-";
        return this.sender.getId() + ":" + this.sender.getName() + " -> " + this.recipient.getId() + ":" + this.recipient.getName() + ", " + sign + this.amount + ", " + this.transferCategory.name() + ", transaction ID: " + this.id;
    }

    public UUID getId() {
        return id;
    }

    public User getRecipient() {
        return recipient;
    }

    public User getSender() {
        return sender;
    }

    public TransferCategory getCategory() {
        return transferCategory;
    }

    public int getAmount() {
        return amount;
    }
}
