package de.sirmelonchen.controller;

import de.sirmelonchen.model.Bucket;
import de.sirmelonchen.model.Expense;
import de.sirmelonchen.model.User;
import de.sirmelonchen.model.Workspace;
import de.sirmelonchen.repository.BucketRepository;
import de.sirmelonchen.repository.ExpenseRepository;
import de.sirmelonchen.service.BucketService;
import de.sirmelonchen.service.UserService;
import de.sirmelonchen.service.WorkspaceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/buckets")
public class BucketController {

    private final BucketRepository bucketRepository;
    private final ExpenseRepository expenseRepository;
    private final BucketService bucketService;

    public BucketController(BucketRepository bucketRepository, ExpenseRepository expenseRepository, BucketService bucketService) {
        this.bucketRepository = bucketRepository;
        this.expenseRepository = expenseRepository;
        this.bucketService = bucketService;
    }

    @GetMapping("/{id}")
    public String viewBucket(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetails user) {
        Bucket bucket = bucketRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Bucket not found"));

        String currentUsername = user.getUsername();
        String ownerUsername = bucket.getWorkspace().getUser().getUsername(); // Annahme: Workspace hat ein User-Feld

        if (!currentUsername.equals(ownerUsername)) {
            return "error/403"; // oder eine andere Fehlerseite
        }

        model.addAttribute("bucket", bucket);
        model.addAttribute("remainingAmount", bucket.getRemainingAmount());
        return "bucket-view";
    }

    @PostMapping("/{id}/expenses")
    public String addExpense(@PathVariable Long id,
                             @RequestParam String description,
                             @RequestParam double amount,
                             @AuthenticationPrincipal UserDetails user) {

        Bucket bucket = bucketRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Bucket not found"));

        if (!bucket.getWorkspace().getUser().getUsername().equals(user.getUsername())) {
            return "error/403";
        }

        Expense expense = new Expense();
        expense.setDescription(description);
        expense.setAmount(amount);
        expense.setBucket(bucket);
        expense.setCreatedAt(LocalDateTime.now());
        expenseRepository.save(expense);

        return "redirect:/buckets/" + id;
    }

    @PostMapping("/{bucketId}/expense/{expenseId}/delete")
    public String deleteExpense(@PathVariable Long bucketId,
                                @PathVariable Long expenseId,
                                @AuthenticationPrincipal UserDetails user) {
        bucketService.deleteExpense(bucketId, expenseId, user.getUsername());
        return "redirect:/buckets/" + bucketId;
    }

    @PostMapping("/{bucketId}/delete")
    public String deleteBucket(@PathVariable Long bucketId,
                               @AuthenticationPrincipal UserDetails user) {
        bucketService.deleteBucket(bucketId, user.getUsername());
        return "redirect:/home";  // oder wohin du nach dem Löschen willst
    }


}

