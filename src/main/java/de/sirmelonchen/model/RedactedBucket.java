package de.sirmelonchen.model;

/**
 * The type Redacted bucket.
 */
public class RedactedBucket {
    private final String name;
    private final String content;
    private final String amount = "[REDACTED]";
    private final String availableAmount = "[REDACTED]";

    /**
     * Instantiates a new Redacted bucket.
     *
     * @param bucket the bucket
     */
    public RedactedBucket(Bucket bucket) {
        this.name = bucket.getName();
        this.content = redactNumbers(bucket.getContent());
    }

    private String redactNumbers(String input) {
        if (input == null) return null;
        return input.replaceAll("\\d+", "[REDACTED]");
    }

    /**
     * Gets name.
     *
     * @return the name
     */
// Getter
    public String getName() { return name; }

    /**
     * Gets content.
     *
     * @return the content
     */
    public String getContent() { return content; }

    /**
     * Gets amount.
     *
     * @return the amount
     */
    public String getAmount() { return amount; }

    /**
     * Gets available amount.
     *
     * @return the available amount
     */
    public String getAvailableAmount() { return availableAmount; }
}
