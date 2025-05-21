package de.sirmelonchen.repository;

import de.sirmelonchen.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Expense repository.
 */
public interface ExpenseRepository extends JpaRepository<Expense, Long> {}
