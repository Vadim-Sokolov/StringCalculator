package esg;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringCalculator {

    public Integer add(String numbers) throws StringCalculatorException {
        if (numbers.isEmpty()) {
            return 0;
        }
        return addNumbers(numbers);
    }

    private Integer addNumbers(String numbers) throws StringCalculatorException {
        var commaDelimitedNumbers = replaceCustomDelimiterWithComma(numbers);
        String[] numbersArray = commaDelimitedNumbers.split("[,\n]");

        findNegativeNumbers(numbersArray);

        return Arrays.stream(numbersArray)
                .map(s -> s.replaceAll("\\s", ""))
                .mapToInt(Integer::parseInt)
                .filter(num -> num <= 1000)
                .sum();
    }

    private String replaceCustomDelimiterWithComma(String numbers) {

        if (numbers.startsWith("//")) {
            Matcher matcher = Pattern.compile("\\[([^]]+)]").matcher(numbers);
            var delimiterEndIndex = numbers.indexOf("\n");
            var stringToReturn = numbers.substring(delimiterEndIndex + 1);

            while (matcher.find()) {
                String customDelimiter = matcher.group(1);
                stringToReturn = stringToReturn.replace(customDelimiter, ",");
            }
            return stringToReturn;
        }
        return numbers;
    }

    private void findNegativeNumbers(String[] numbersArray) throws StringCalculatorException {

        var negativeNumbers = Arrays.stream(numbersArray)
                .map(s -> s.replaceAll("\\s", ""))
                .filter(num -> num.startsWith("-"))
                .collect(Collectors.joining(","));

        if (!negativeNumbers.isEmpty()) {
            throw new StringCalculatorException("Negatives not allowed: " + negativeNumbers);
        }
    }
}
