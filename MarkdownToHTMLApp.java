import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MarkdownToHTMLApp {
    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("Markdown to HTML Converter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Set layout manager
        frame.setLayout(new BorderLayout(10, 10));

        // Panels for better organization
        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputPanel.setBackground(Color.decode("#E5D9F2"));
        JPanel outputPanel = new JPanel(new BorderLayout(5, 5));
        outputPanel.setBackground(Color.decode("#F5EFFF"));

        // Markdown input area with a scroll pane
        JTextArea markdownArea = new JTextArea();
        markdownArea.setFont(new Font("Fira Code", Font.BOLD, 16));
        markdownArea.setLineWrap(true);
        markdownArea.setWrapStyleWord(true);
        markdownArea.setBackground(Color.decode("#CDC1FF"));

        JScrollPane markdownScrollPane = new JScrollPane(markdownArea);
        markdownScrollPane.setBorder(BorderFactory.createTitledBorder("Markdown Input"));
        inputPanel.add(markdownScrollPane, BorderLayout.CENTER);

        // HTML output area with a scroll pane
        JTextArea htmlArea = new JTextArea();
        htmlArea.setFont(new Font("Fira Code", Font.BOLD, 16));
        htmlArea.setEditable(false);
        htmlArea.setLineWrap(true);
        htmlArea.setWrapStyleWord(true);
        htmlArea.setBackground(Color.decode("#CDC1FF"));

        JScrollPane htmlScrollPane = new JScrollPane(htmlArea);
        htmlScrollPane.setBorder(BorderFactory.createTitledBorder("HTML Output"));
        outputPanel.add(htmlScrollPane, BorderLayout.CENTER);

        // Convert button
        JButton convertButton = new JButton("Convert to HTML");
        convertButton.setFont(new Font("Fira Code", Font.BOLD, 18));
        convertButton.setBackground(Color.decode("#A594F9"));
        convertButton.setForeground(Color.WHITE);
        convertButton.setFocusPainted(false);

        convertButton.addActionListener(e -> {
            String markdown = markdownArea.getText();
            if (markdown.trim().isEmpty()) {
                htmlArea.setText("Please enter Markdown text to convert.");
            } else {
                String html = MarkdownToHTML.convert(markdown);
                htmlArea.setText(html);
            }
        });

        // Placeholder for markdown input
        markdownArea.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (markdownArea.getText().equals("Enter Markdown text here...")) {
                    markdownArea.setText("");
                    markdownArea.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (markdownArea.getText().trim().isEmpty()) {
                    markdownArea.setForeground(Color.GRAY);
                    markdownArea.setText("Enter Markdown text here...");
                }
            }
        });

        // Set initial placeholder text
        markdownArea.setForeground(Color.GRAY);
        markdownArea.setText("Enter Markdown text here...");

        // Add components to frame
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(convertButton, BorderLayout.CENTER);
        frame.add(outputPanel, BorderLayout.SOUTH);

        // Adjust layout proportions
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.4;
        gbc.fill = GridBagConstraints.BOTH;
        frame.add(inputPanel, gbc);

        gbc.gridy = 1;
        gbc.weighty = 0.1;
        frame.add(convertButton, gbc);

        gbc.gridy = 2;
        gbc.weighty = 0.5;
        frame.add(outputPanel, gbc);

        // Display the frame
        frame.setVisible(true);
    }
}
