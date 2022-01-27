package net.rejoyce.example.lotto.data;

import lombok.extern.slf4j.Slf4j;
import net.rejoyce.example.lotto.model.Result;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class ResultsRepository {

    private static List<Result> allResults;

    /**
     * This will scrape the results of the irish lotto draw since 1988
     */
    public static void scrapeAllResults() {
        scrapeAllResults(1988);
    }

    /**
     * This will allow a scraping of a more limited set of Results - eg : for testing
     */
    public static void scrapeAllResults(final int start) {
        allResults = new ArrayList<>();

        String siteURL = "https://irish.national-lottery.com/irish-lotto/results-archive-";
        for (int i = start; i <= 2022; i++) {
            allResults.addAll(scrapeResult(siteURL + i));
        }
        log.info("{} draw results in total.", allResults.size());
    }

    private static List<Result> scrapeResult(final String url) {
        List<Result> allYearResults = new ArrayList<>();

        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.info("Page Title: {}", doc.title());

        Element element = doc.getElementsByClass("box full").get(0);
        Elements elements = element.getElementsByClass("noBefore colour");
        for (int i = 0; i < elements.size(); i++) {

            String val = elements.get(i).wholeText().trim();
            int start = val.indexOf("day");
            String rawDate = val.substring(start + 3);

            DateTimeFormatter formatter =
                    DateTimeFormatter
                            .ofPattern("d['st']['nd']['rd']['th'] MMMM yyyy", Locale.ENGLISH);
            LocalDate date = LocalDate.parse(rawDate, formatter);

            String str = elements.get(i).siblingElements().get(0).child(0).getElementsByTag("li").text();

            allYearResults.add(new Result(date, getNumbers(str)));
            System.out.println("date = " + date + ", numbers " + str);
        }
        log.info("{} results found", +elements.size());
        // With the document fetched, we use JSoup's title() method to fetch the title

        return allYearResults;
    }

    private static int[] getNumbers(final String str) {
        String s[] = str.split(" ");
        int out[] = new int[s.length];
        for (int i = 0; i < s.length; i++)
            out[i] = Integer.parseInt(s[i]);
        return out;
    }

    public static List<Result> queryForNumber(final int number) {
        return allResults.stream().filter(x -> Arrays.stream(x.getNumbers()).anyMatch(n -> n == number)).sorted(Comparator.comparing(Result::getDate)).collect(Collectors.toList());
    }

    public static String getMetaForNumber(final int number) {
        List<Result> result = queryForNumber(number);
        final int resultSize = result.size();
        String times = null, first = null, last = null;
        if (resultSize > 0) {
            times = new StringBuffer().append(number).append(" has appeared ").append(result.size()).append(" time").append((resultSize > 1 ? "s" : "")).append("<br/>").toString();
            first = new StringBuffer().append(number).append(" was first drawn on ").append(result.get(0).getDate()).append("<br/>").toString();
            last = new StringBuffer().append(number).append(" was last drawn on ").append(result.get(resultSize-1).getDate()).append("<br/>").toString();
        }
        if (times != null) {
            return times + first + last;

        } else {
            return new StringBuffer().append(number).append(" has never been drawn.").toString();
        }
    }
}
