package com.crimsonlogic.bankingandloanmanagementsystem.utility;

import java.util.List;

public class TableUtil {

    private TableUtil() {}

    /**
     * Prints records in a dynamic ASCII table format.
     */
    public static void printTable(String title, List<String> headers, List<List<String>> rows) {
        if (rows == null || rows.isEmpty()) {
            System.out.println("\n--> [Information]: No records found to display for " + title + ".");
            return;
        }

        int columnCount = headers.size();
        int[] columnWidths = new int[columnCount];

        // 1. Calculate header widths
        for (int i = 0; i < columnCount; i++) {
            columnWidths[i] = headers.get(i).length();
        }

        // 2. Adjust widths based on cell content
        for (List<String> row : rows) {
            for (int i = 0; i < columnCount && i < row.size(); i++) {
                String val = row.get(i) != null ? row.get(i) : "N/A";
                if (val.length() > columnWidths[i]) {
                    columnWidths[i] = val.length();
                }
            }
        }

        // 3. Build border line
        StringBuilder borderBuilder = new StringBuilder("+");
        for (int width : columnWidths) {
            borderBuilder.append("-".repeat(width + 2)).append("+");
        }
        String borderLine = borderBuilder.toString();

        // 4. Print Title Banner
        System.out.println("\n" + "=".repeat(borderLine.length()));
        System.out.println(" ".repeat(Math.max(0, (borderLine.length() - title.length()) / 2)) + title.toUpperCase());
        System.out.println("=".repeat(borderLine.length()));

        // 5. Print Header Row
        System.out.println(borderLine);
        System.out.print("|");
        for (int i = 0; i < columnCount; i++) {
            System.out.printf(" %-" + columnWidths[i] + "s |", headers.get(i));
        }
        System.out.println();
        System.out.println(borderLine);

        // 6. Print Data Rows
        for (List<String> row : rows) {
            System.out.print("|");
            for (int i = 0; i < columnCount; i++) {
                String val = (i < row.size() && row.get(i) != null) ? row.get(i) : "N/A";
                System.out.printf(" %-" + columnWidths[i] + "s |", val);
            }
            System.out.println();
        }

        System.out.println(borderLine);
    }
}