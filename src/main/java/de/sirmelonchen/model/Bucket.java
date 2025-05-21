package de.sirmelonchen.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Bucket.
 */
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

    /**
     * Gets remaining amount.
     *
     * @return the remaining amount
     */
    public BigDecimal getRemainingAmount() {
        BigDecimal spent = expenses.stream()
                .map(expense -> BigDecimal.valueOf(expense.getAmount()))  // Hier konvertieren
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return amount.subtract(spent);
    }

    /**
     * Gets available amount.
     *
     * @return the available amount
     */
    public BigDecimal getAvailableAmount() {
        BigDecimal spent = expenses.stream()
                .map(e -> BigDecimal.valueOf(e.getAmount())) // falls getAmount() double/Double liefert
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return amount.subtract(spent);
    }


    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets amount.
     *
     * @return the amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Sets amount.
     *
     * @param amount the amount
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * Gets workspace.
     *
     * @return the workspace
     */
    public Workspace getWorkspace() {
        return workspace;
    }

    /**
     * Sets workspace.
     *
     * @param workspace the workspace
     */
    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    /**
     * Gets expenses.
     *
     * @return the expenses
     */
    public List<Expense> getExpenses() {
        return expenses;
    }

    /**
     * Sets expenses.
     *
     * @param expenses the expenses
     */
    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }
}
