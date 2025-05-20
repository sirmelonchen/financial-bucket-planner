package de.sirmelonchen.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Bucket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private BigDecimal amount;

    private BigDecimal availableAmount;

    @ManyToOne
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;

    @OneToMany(mappedBy = "bucket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Expense> expenses = new ArrayList<>();

    public BigDecimal getRemainingAmount() {
        BigDecimal spent = expenses.stream()
                .map(expense -> BigDecimal.valueOf(expense.getAmount()))  // Hier konvertieren
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return amount.subtract(spent);
    }

    public BigDecimal getAvailableAmount() {
        BigDecimal spent = expenses.stream()
                .map(e -> BigDecimal.valueOf(e.getAmount())) // falls getAmount() double/Double liefert
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return amount.subtract(spent);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Workspace getWorkspace() {
        return workspace;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }
}
