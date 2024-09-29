package ex03;

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

    public Transaction(User sender, User recipient, TransferCategory category, int amount) {
        this.id = UUID.randomUUID();
        this.sender = sender;
        this.recipient = recipient;
        this.transferCategory = category;
        setAmount(amount);
    }

    public void setAmount(int amount) {
        if ((transferCategory == Transaction.TransferCategory.DEBIT && amount < 0) || (transferCategory == Transaction.TransferCategory.CREDIT && amount > 0)) {
            throw new IllegalArgumentException("DEBIT requires positive, CREDIT requires negative amount");
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

    public String toString() {
        return getSender().getId() + ":" + getSender().getName() + " -> " + getRecipient().getId() + ":" + getRecipient().getName() + ", " + getAmount() + ", " + getCategory().name() + ", " + id;
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
