package com.mipt.maksimsafronov.IOHWClasses;

import java.io.*;

public class TextFileAnalyzer {

    public static class AnalysisResult {
        private final long lineCount;
        private final long wordCount;
        private final long charCount;

        public AnalysisResult(long lineCount, long wordCount, long charCount) {
            this.lineCount = lineCount;
            this.wordCount = wordCount;
            this.charCount = charCount;
        }

        public long getLineCount() {
            return lineCount;
        }

        public long getWordCount() {
            return wordCount;
        }

        public long getCharCount() {
            return charCount;
        }

        @Override
        public String toString() {
            return "AnalysisResult{" +
                    "lineCount=" + lineCount +
                    ", wordCount=" + wordCount +
                    ", charCount=" + charCount +
                    '}';
        }
    }

    public AnalysisResult analyzeFile(String filePath) throws IOException {
        long lineCount = 0;
        long wordCount = 0;
        long charCount = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                lineCount++;
                charCount += line.length();

                if (!line.trim().isEmpty()) {
                    String[] words = line.trim().split("\\s+");
                    wordCount += words.length;
                }
            }

        }
        return new AnalysisResult(lineCount, wordCount, charCount);

    }

    public void saveAnalysisResult(AnalysisResult result, String outputPath) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputPath))) {
            bw.write(result.toString());
        }
    }
}
