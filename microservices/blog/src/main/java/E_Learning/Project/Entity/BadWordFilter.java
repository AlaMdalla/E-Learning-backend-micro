package E_Learning.Project.Entity;

public class BadWordFilter {


        // Static list of bad words (can be moved to a configuration file or database)
        private static final String[] BAD_WORDS = {
                "bad", "trash", "hate", "damn", "crap", // Add more bad words as needed
                "fool", "jerk", "stupid", "idiot"
        };

        // Method to check and censor bad words
        public static String filterBadWords(String content) {
            if (content == null || content.trim().isEmpty()) {
                return content;
            }

            String lowerCaseContent = content.toLowerCase();
            StringBuilder censoredContent = new StringBuilder(content);

            for (String badWord : BAD_WORDS) {
                int index = lowerCaseContent.indexOf(badWord.toLowerCase());
                while (index >= 0) {
                    // Replace the bad word with asterisks (e.g., "bad" -> "****")
                    for (int i = index; i < index + badWord.length(); i++) {
                        censoredContent.setCharAt(i, '*');
                    }
                    index = lowerCaseContent.indexOf(badWord.toLowerCase(), index + 1);
                }
            }

            return censoredContent.toString();
        }

        // Method to check if content contains bad words (returns true if found)
        public static boolean containsBadWords(String content) {
            if (content == null || content.trim().isEmpty()) {
                return false;
            }

            String lowerCaseContent = content.toLowerCase();
            for (String badWord : BAD_WORDS) {
                if (lowerCaseContent.contains(badWord.toLowerCase())) {
                    return true;
                }
            }
            return false;
        }
    }
