import java.util.regex.*;

public class MarkdownToHTML {
    public static String convert(String markdown) {
        // Trim unnecessary spaces
        markdown = markdown.trim();

        // Convert headings
        markdown = markdown.replaceAll("(?m)^### (.*?)$", "<h3>$1</h3>");
        markdown = markdown.replaceAll("(?m)^## (.*?)$", "<h2>$1</h2>");
        markdown = markdown.replaceAll("(?m)^# (.*?)$", "<h1>$1</h1>");

        // Convert bold and italic text
        markdown = markdown.replaceAll("\\*\\*([^*]+)\\*\\*", "<b>$1</b>");
        markdown = markdown.replaceAll("\\*([^*]+)\\*", "<i>$1</i>");

        // Handle unordered lists with proper nesting
        StringBuilder html = new StringBuilder();
        String[] lines = markdown.split("\n");
        int currentIndent = 0;
        boolean openTopLevelList = false;

        for (String line : lines) {
            Matcher listMatcher = Pattern.compile("^(\\s*)- (.+)$").matcher(line);

            if (listMatcher.matches()) {
                int indent = listMatcher.group(1).length() / 2; // 2 spaces per nesting level
                String content = listMatcher.group(2);

                if (!openTopLevelList && indent == 0) {
                    html.append("<ul>"); // Open top-level <ul> if not already open
                    openTopLevelList = true;
                }

                // Open new <ul> if deeper indentation
                while (indent > currentIndent) {
                    html.append("<ul>");
                    currentIndent++;
                }

                // Close <ul> if less indentation
                while (indent < currentIndent) {
                    html.append("</ul>");
                    currentIndent--;
                }

                // Add list item
                html.append("<li>").append(content).append("</li>");
            } else {
                // Close any open lists when line is not a list item
                while (currentIndent > 0) {
                    html.append("</ul>");
                    currentIndent--;
                }
                if (openTopLevelList) {
                    html.append("</ul>"); // Close top-level list
                    openTopLevelList = false;
                }

                // Append non-list content
                html.append(line).append("\n");
            }
        }

        // Close any remaining open lists
        while (currentIndent > 0) {
            html.append("</ul>");
            currentIndent--;
        }
        if (openTopLevelList) {
            html.append("</ul>");
        }

        // Replace the list content in the markdown
        markdown = html.toString();

        // Convert links
        markdown = markdown.replaceAll("\\[(.*?)\\]\\((.*?)\\)", "<a href=\"$2\">$1</a>");

        // Convert blockquotes
        markdown = markdown.replaceAll("(?m)^> (.*?)$", "<blockquote>$1</blockquote>");

        // Convert horizontal rules
        markdown = markdown.replaceAll("(?m)^---$", "<hr>");

        // Wrap plain text in <p> tags, excluding lists and other HTML tags
        markdown = markdown.replaceAll("(?m)^(?!<(h\\d|ul|li|blockquote|hr|a|b|i|img|pre|code))(.+?)$", "<p>$2</p>");

        // Remove unnecessary empty <p> tags
        markdown = markdown.replaceAll("(?m)<p>\\s*</p>", "");

        return markdown.trim();
    }
}
