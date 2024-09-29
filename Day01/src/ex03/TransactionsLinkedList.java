package ex03;

public class TransactionsLinkedList implements TransactionsList {
    private static class Node {
        Transaction transaction;
        Node next;

        Node(Transaction transaction) {
            this.transaction = transaction;
        }
    }

    private Node head = null;
    private Node tail = null;
    private int size = 0;

    @Override
    public void addTransaction(Transaction transaction) {
        Node newNode = new Node(transaction);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void removeTransactionById(String id) throws TransactionNotFoundException {
        if (head == null) {
            throw new TransactionNotFoundException("Transaction list is empty.");
        }
        Node temp = head, prev = null;
        if (temp != null && temp.transaction.getId().toString().equals(id)) {
            head = temp.next;
            if (temp == tail) {
                tail = prev;
            }
            size--;
            return;
        }
        while (temp != null && !temp.transaction.getId().toString().equals(id)) {
            prev = temp;
            temp = temp.next;
        }
        if (temp == null) {
            throw new TransactionNotFoundException("Transaction with ID " + id + " not found.");
        }
        prev.next = temp.next;
        if (temp == tail) {
            tail = prev;
        }
        size--;
    }

    @Override
    public Transaction[] toArray() {
        Transaction[] transactionsArray = new Transaction[size];
        Node temp = head;
        int i = 0;
        while (temp != null) {
            transactionsArray[i++] = temp.transaction;
            temp = temp.next;
        }
        return transactionsArray;
    }

    @Override
    public int size() {
        return size;
    }
}
