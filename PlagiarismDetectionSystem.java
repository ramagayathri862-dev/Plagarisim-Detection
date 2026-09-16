package p1;
import java.util.*;

/*
 * ============================================================
 *        PLAGIARISM DETECTION SYSTEM
 * ============================================================
 *
 * Algorithms Used:
 * 1. Text Preprocessing
 * 2. KMP (Knuth-Morris-Pratt)
 * 3. Rabin-Karp
 * 4. N-Gram Matching
 * 5. Jaccard Similarity
 * 6. Cosine Similarity
 * 7. Levenshtein / Edit Distance
 * 8. Longest Common Subsequence (LCS)
 *
 * All algorithms are implemented in ONE JAVA FILE.
 *
 * ============================================================
 */

public class PlagiarismDetectionSystem {

    // =========================================================
    // MAIN METHOD
    // =========================================================

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("==================================================");
        System.out.println("        PLAGIARISM DETECTION SYSTEM");
        System.out.println("==================================================");

        System.out.println("\nEnter Document 1:");
        String document1 = scanner.nextLine();

        System.out.println("\nEnter Document 2:");
        String document2 = scanner.nextLine();

        if (document1.trim().isEmpty() || document2.trim().isEmpty()) {
            System.out.println("\nError: Documents cannot be empty.");
            scanner.close();
            return;
        }

        // Perform analysis
        analyzeDocuments(document1, document2);

        scanner.close();
    }

    // =========================================================
    // MAIN ANALYSIS METHOD
    // =========================================================

    public static void analyzeDocuments(String document1, String document2) {

        System.out.println("\n\n==================================================");
        System.out.println("             ANALYSIS STARTED");
        System.out.println("==================================================");

        // -----------------------------------------------------
        // STEP 1: PREPROCESSING
        // -----------------------------------------------------

        String cleanDoc1 = cleanText(document1);
        String cleanDoc2 = cleanText(document2);

        System.out.println("\n1. TEXT PREPROCESSING");
        System.out.println("----------------------------------------------");
        System.out.println("Document 1 after preprocessing:");
        System.out.println(cleanDoc1);

        System.out.println("\nDocument 2 after preprocessing:");
        System.out.println(cleanDoc2);

        // -----------------------------------------------------
        // STEP 2: TOKENIZATION
        // -----------------------------------------------------

        List<String> words1 = tokenize(cleanDoc1);
        List<String> words2 = tokenize(cleanDoc2);

        System.out.println("\n2. TOKENIZATION");
        System.out.println("----------------------------------------------");
        System.out.println("Document 1 words: " + words1.size());
        System.out.println("Document 2 words: " + words2.size());

        // -----------------------------------------------------
        // STEP 3: N-GRAM
        // -----------------------------------------------------

        int n = 3;

        Set<String> ngrams1 = generateNGrams(cleanDoc1, n);
        Set<String> ngrams2 = generateNGrams(cleanDoc2, n);

        System.out.println("\n3. N-GRAM MATCHING");
        System.out.println("----------------------------------------------");
        System.out.println("N-Gram size: " + n);
        System.out.println("Document 1 N-Grams: " + ngrams1.size());
        System.out.println("Document 2 N-Grams: " + ngrams2.size());

        Set<String> commonNGrams = new HashSet<>(ngrams1);
        commonNGrams.retainAll(ngrams2);

        System.out.println("Common N-Grams: " + commonNGrams.size());

        // -----------------------------------------------------
        // STEP 4: JACCARD SIMILARITY
        // -----------------------------------------------------

        double jaccard = calculateJaccard(ngrams1, ngrams2);

        System.out.println("\n4. JACCARD SIMILARITY");
        System.out.println("----------------------------------------------");
        System.out.printf("Jaccard Similarity: %.2f%%%n", jaccard * 100);

        // -----------------------------------------------------
        // STEP 5: COSINE SIMILARITY
        // -----------------------------------------------------

        double cosine = calculateCosine(cleanDoc1, cleanDoc2);

        System.out.println("\n5. COSINE SIMILARITY");
        System.out.println("----------------------------------------------");
        System.out.printf("Cosine Similarity: %.2f%%%n", cosine * 100);

        // -----------------------------------------------------
        // STEP 6: KMP
        // -----------------------------------------------------

        String kmpPattern = getFirstWords(cleanDoc1, 5);

        int kmpMatches = 0;

        if (!kmpPattern.isEmpty()) {
            kmpMatches = kmpSearch(cleanDoc2, kmpPattern);
        }

        System.out.println("\n6. KMP ALGORITHM");
        System.out.println("----------------------------------------------");
        System.out.println("Pattern used: " + kmpPattern);
        System.out.println("KMP Matches Found: " + kmpMatches);

        // -----------------------------------------------------
        // STEP 7: RABIN-KARP
        // -----------------------------------------------------

        int rabinMatches = 0;

        if (!kmpPattern.isEmpty()) {
            rabinMatches = rabinKarpSearch(cleanDoc2, kmpPattern);
        }

        System.out.println("\n7. RABIN-KARP ALGORITHM");
        System.out.println("----------------------------------------------");
        System.out.println("Pattern used: " + kmpPattern);
        System.out.println("Rabin-Karp Matches Found: " + rabinMatches);

        // -----------------------------------------------------
        // STEP 8: LEVENSHTEIN DISTANCE
        // -----------------------------------------------------

        double editSimilarity = calculateEditSimilarity(cleanDoc1, cleanDoc2);

        int editDistance = levenshteinDistance(cleanDoc1, cleanDoc2);

        System.out.println("\n8. LEVENSHTEIN / EDIT DISTANCE");
        System.out.println("----------------------------------------------");
        System.out.println("Edit Distance: " + editDistance);
        System.out.printf("Edit Similarity: %.2f%%%n",
                editSimilarity * 100);

        // -----------------------------------------------------
        // STEP 9: LCS
        // -----------------------------------------------------

        double lcsSimilarity = calculateLCSSimilarity(cleanDoc1, cleanDoc2);

        int lcsLength = calculateLCS(cleanDoc1, cleanDoc2);

        System.out.println("\n9. LONGEST COMMON SUBSEQUENCE");
        System.out.println("----------------------------------------------");
        System.out.println("LCS Length: " + lcsLength);
        System.out.printf("LCS Similarity: %.2f%%%n",
                lcsSimilarity * 100);

        // -----------------------------------------------------
        // STEP 10: FINAL PLAGIARISM SCORE
        // -----------------------------------------------------

        /*
         * Weighted similarity score:
         *
         * Jaccard       = 25%
         * Cosine        = 30%
         * Edit Distance = 15%
         * LCS            = 15%
         * KMP            = 7.5%
         * Rabin-Karp     = 7.5%
         */

        double kmpScore = kmpMatches > 0 ? 1.0 : 0.0;
        double rabinScore = rabinMatches > 0 ? 1.0 : 0.0;

        double finalScore =
                (jaccard * 0.25) +
                (cosine * 0.30) +
                (editSimilarity * 0.15) +
                (lcsSimilarity * 0.15) +
                (kmpScore * 0.075) +
                (rabinScore * 0.075);

        // Convert into percentage
        double plagiarismPercentage = finalScore * 100;

        // -----------------------------------------------------
        // STEP 11: RESULT
        // -----------------------------------------------------

        String status;

        if (plagiarismPercentage >= 70) {
            status = "HIGH PLAGIARISM";
        }
        else if (plagiarismPercentage >= 40) {
            status = "MODERATE PLAGIARISM";
        }
        else {
            status = "LOW PLAGIARISM";
        }

        System.out.println("\n\n==================================================");
        System.out.println("             FINAL PLAGIARISM REPORT");
        System.out.println("==================================================");

        System.out.printf("\nJaccard Similarity       : %.2f%%%n",
                jaccard * 100);

        System.out.printf("Cosine Similarity        : %.2f%%%n",
                cosine * 100);

        System.out.printf("Edit Distance Similarity : %.2f%%%n",
                editSimilarity * 100);

        System.out.printf("LCS Similarity            : %.2f%%%n",
                lcsSimilarity * 100);

        System.out.println("KMP Matches              : " + kmpMatches);

        System.out.println("Rabin-Karp Matches       : " + rabinMatches);

        System.out.println("Common N-Grams           : "
                + commonNGrams.size());

        System.out.println("\n----------------------------------------------");

        System.out.printf("FINAL SIMILARITY SCORE   : %.2f%%%n",
                plagiarismPercentage);

        System.out.println("RESULT                   : " + status);

        System.out.println("==================================================");

        System.out.println("\nNote:");
        System.out.println("This score is a similarity indicator.");
        System.out.println("It should be used as evidence for review,");
        System.out.println("not as an automatic accusation of plagiarism.");
    }

    // =========================================================
    // 1. TEXT PREPROCESSING
    // =========================================================

    public static String cleanText(String text) {

        // Convert all characters to lowercase
        text = text.toLowerCase();

        // Remove special characters
        text = text.replaceAll("[^a-z0-9\\s]", " ");

        // Replace multiple spaces with one space
        text = text.replaceAll("\\s+", " ");

        // Remove leading/trailing spaces
        text = text.trim();

        return text;
    }

    // =========================================================
    // 2. TOKENIZATION
    // =========================================================

    public static List<String> tokenize(String text) {

        if (text == null || text.trim().isEmpty()) {
            return new ArrayList<>();
        }

        return Arrays.asList(text.split("\\s+"));
    }

    // =========================================================
    // 3. N-GRAM ALGORITHM
    // =========================================================

    public static Set<String> generateNGrams(String text, int n) {

        Set<String> ngrams = new HashSet<>();

        List<String> words = tokenize(text);

        if (words.size() < n) {
            return ngrams;
        }

        for (int i = 0; i <= words.size() - n; i++) {

            StringBuilder gram = new StringBuilder();

            for (int j = 0; j < n; j++) {

                if (j > 0) {
                    gram.append(" ");
                }

                gram.append(words.get(i + j));
            }

            ngrams.add(gram.toString());
        }

        return ngrams;
    }

    // =========================================================
    // 4. JACCARD SIMILARITY
    // =========================================================

    public static double calculateJaccard(
            Set<String> set1,
            Set<String> set2) {

        if (set1.isEmpty() && set2.isEmpty()) {
            return 1.0;
        }

        Set<String> intersection =
                new HashSet<>(set1);

        intersection.retainAll(set2);

        Set<String> union =
                new HashSet<>(set1);

        union.addAll(set2);

        if (union.isEmpty()) {
            return 0.0;
        }

        return (double) intersection.size()
                / union.size();
    }

    // =========================================================
    // 5. COSINE SIMILARITY
    // =========================================================

    public static double calculateCosine(
            String text1,
            String text2) {

        Map<String, Integer> frequency1 =
                wordFrequency(text1);

        Map<String, Integer> frequency2 =
                wordFrequency(text2);

        Set<String> allWords =
                new HashSet<>();

        allWords.addAll(frequency1.keySet());
        allWords.addAll(frequency2.keySet());

        double dotProduct = 0.0;
        double magnitude1 = 0.0;
        double magnitude2 = 0.0;

        for (String word : allWords) {

            int value1 =
                    frequency1.getOrDefault(word, 0);

            int value2 =
                    frequency2.getOrDefault(word, 0);

            dotProduct += value1 * value2;
        }

        for (int value : frequency1.values()) {
            magnitude1 += value * value;
        }

        for (int value : frequency2.values()) {
            magnitude2 += value * value;
        }

        magnitude1 = Math.sqrt(magnitude1);
        magnitude2 = Math.sqrt(magnitude2);

        if (magnitude1 == 0 || magnitude2 == 0) {
            return 0.0;
        }

        return dotProduct /
                (magnitude1 * magnitude2);
    }

    // =========================================================
    // WORD FREQUENCY USING HASHMAP
    // =========================================================

    public static Map<String, Integer> wordFrequency(
            String text) {

        Map<String, Integer> frequency =
                new HashMap<>();

        List<String> words = tokenize(text);

        for (String word : words) {

            frequency.put(
                    word,
                    frequency.getOrDefault(word, 0) + 1
            );
        }

        return frequency;
    }

    // =========================================================
    // 6. KMP ALGORITHM
    // =========================================================

    public static int kmpSearch(
            String text,
            String pattern) {

        if (text == null ||
                pattern == null ||
                pattern.isEmpty()) {

            return 0;
        }

        int[] lps =
                computeLPS(pattern);

        int i = 0;
        int j = 0;

        int count = 0;

        while (i < text.length()) {

            if (text.charAt(i) ==
                    pattern.charAt(j)) {

                i++;
                j++;

                if (j == pattern.length()) {

                    count++;

                    j = lps[j - 1];
                }

            }
            else {

                if (j != 0) {

                    j = lps[j - 1];

                }
                else {

                    i++;
                }
            }
        }

        return count;
    }

    // =========================================================
    // KMP LPS ARRAY
    // =========================================================

    public static int[] computeLPS(
            String pattern) {

        int[] lps =
                new int[pattern.length()];

        int length = 0;

        int i = 1;

        while (i < pattern.length()) {

            if (pattern.charAt(i) ==
                    pattern.charAt(length)) {

                length++;

                lps[i] = length;

                i++;
            }
            else {

                if (length != 0) {

                    length =
                            lps[length - 1];

                }
                else {

                    lps[i] = 0;

                    i++;
                }
            }
        }

        return lps;
    }

    // =========================================================
    // 7. RABIN-KARP ALGORITHM
    // =========================================================

    public static int rabinKarpSearch(
            String text,
            String pattern) {

        if (text == null ||
                pattern == null ||
                pattern.isEmpty() ||
                pattern.length() > text.length()) {

            return 0;
        }

        final int PRIME = 101;

        int patternHash = 0;
        int textHash = 0;

        int h = 1;

        int patternLength =
                pattern.length();

        // Calculate h = pow(256, patternLength-1) % PRIME
        for (int i = 0;
                i < patternLength - 1;
                i++) {

            h = (h * 256) % PRIME;
        }

        // Calculate initial hashes
        for (int i = 0;
                i < patternLength;
                i++) {

            patternHash =
                    (256 * patternHash
                            + pattern.charAt(i))
                            % PRIME;

            textHash =
                    (256 * textHash
                            + text.charAt(i))
                            % PRIME;
        }

        int count = 0;

        for (int i = 0;
                i <= text.length() - patternLength;
                i++) {

            if (patternHash == textHash) {

                boolean match = true;

                for (int j = 0;
                        j < patternLength;
                        j++) {

                    if (text.charAt(i + j)
                            != pattern.charAt(j)) {

                        match = false;
                        break;
                    }
                }

                if (match) {
                    count++;
                }
            }

            // Calculate next rolling hash
            if (i <
                    text.length() - patternLength) {

                textHash =
                        (256 *
                                (textHash
                                        - text.charAt(i) * h)
                                + text.charAt(
                                        i + patternLength))
                                % PRIME;

                if (textHash < 0) {
                    textHash += PRIME;
                }
            }
        }

        return count;
    }

    // =========================================================
    // 8. LEVENSHTEIN / EDIT DISTANCE
    // =========================================================

    public static int levenshteinDistance(
            String a,
            String b) {

        int m = a.length();
        int n = b.length();

        int[][] dp =
                new int[m + 1][n + 1];

        // First column
        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }

        // First row
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= m; i++) {

            for (int j = 1; j <= n; j++) {

                int cost =
                        a.charAt(i - 1)
                                == b.charAt(j - 1)
                                ? 0 : 1;

                dp[i][j] =
                        Math.min(
                                Math.min(
                                        dp[i - 1][j] + 1,
                                        dp[i][j - 1] + 1
                                ),
                                dp[i - 1][j - 1]
                                        + cost
                        );
            }
        }

        return dp[m][n];
    }

    // =========================================================
    // EDIT DISTANCE SIMILARITY
    // =========================================================

    public static double calculateEditSimilarity(
            String a,
            String b) {

        int distance =
                levenshteinDistance(a, b);

        int maxLength =
                Math.max(a.length(), b.length());

        if (maxLength == 0) {
            return 1.0;
        }

        return 1.0 -
                ((double) distance / maxLength);
    }

    // =========================================================
    // 9. LONGEST COMMON SUBSEQUENCE
    // =========================================================

    public static int calculateLCS(
            String a,
            String b) {

        int m = a.length();
        int n = b.length();

        int[][] dp =
                new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {

            for (int j = 1; j <= n; j++) {

                if (a.charAt(i - 1)
                        == b.charAt(j - 1)) {

                    dp[i][j] =
                            dp[i - 1][j - 1] + 1;

                }
                else {

                    dp[i][j] =
                            Math.max(
                                    dp[i - 1][j],
                                    dp[i][j - 1]
                            );
                }
            }
        }

        return dp[m][n];
    }

    // =========================================================
    // LCS SIMILARITY
    // =========================================================

    public static double calculateLCSSimilarity(
            String a,
            String b) {

        int lcs =
                calculateLCS(a, b);

        int maxLength =
                Math.max(a.length(), b.length());

        if (maxLength == 0) {
            return 1.0;
        }

        return (double) lcs / maxLength;
    }

    // =========================================================
    // GET FIRST N WORDS
    // =========================================================

    public static String getFirstWords(
            String text,
            int numberOfWords) {

        List<String> words =
                tokenize(text);

        if (words.isEmpty()) {
            return "";
        }

        int limit =
                Math.min(numberOfWords,
                        words.size());

        StringBuilder result =
                new StringBuilder();

        for (int i = 0; i < limit; i++) {

            if (i > 0) {
                result.append(" ");
            }

            result.append(words.get(i));
        }

        return result.toString();
    }
}
