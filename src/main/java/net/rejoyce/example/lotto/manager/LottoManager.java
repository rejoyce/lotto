package net.rejoyce.example.lotto.manager;

import net.rejoyce.example.lotto.data.ResultsRepository;
import net.rejoyce.example.lotto.model.Result;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LottoManager {

    public static final int MAX_NUMBERS = 47;
    public static final LocalDate EXPANSION_ONE = LocalDate.parse("1992-08-22"); // 6/39
    public static final LocalDate EXPANSION_TWO = LocalDate.parse("1994-09-24"); // 6/42
    public static final LocalDate EXPANSION_THREE = LocalDate.parse("2006-11-04"); // 6/45
    public static final LocalDate EXPANSION_FOUR = LocalDate.parse("2015-09-03"); // 6/47

    // the data to be used to get most and least likely numbers
    private Map<Integer, Float> numberScoreMap;


    public String showMostFrequent() {
        if (null == numberScoreMap) {
            populateOccurrenceData();
        }
        StringBuilder str = new StringBuilder();

        //get highest fraction numbers
        //TODO  this is pretty fast but could easily be cached

        Map sortedDescending = numberScoreMap.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        List<String> keys = new ArrayList<String>(sortedDescending.keySet());
        for (int i = 0; i < 6; i++) {
            str.append(String.valueOf(keys.get(i))).append(' ');
        }
        return str.toString();
    }

    public String showLeastFrequent() {
        if(null==numberScoreMap){
            populateOccurrenceData();
        }
        StringBuilder str = new StringBuilder();

        //get lowest fraction numbers
        //TODO  this is pretty fast but could easily be cached
        Map sortedAscending = numberScoreMap.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.naturalOrder())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        List<String> keys = new ArrayList<String>(sortedAscending.keySet());
        for (int i = 0; i < 6; i++) {
            str.append(String.valueOf(keys.get(i))).append(' ');
        }
        return str.toString();    }

    private void populateOccurrenceData(){
        List<Result> allResults = ResultsRepository.getAllResults();
        Map rawNumberMap = getNumbersMap();
        int count = 0;
        // looping through all numbers
        for (int i = 1; i <= MAX_NUMBERS ; i++) {
            count = countForNumber(allResults, i);
            rawNumberMap.put(i, count);
        }
        convertOccurrencesToFractions(rawNumberMap);
    }

    private Map<Integer, Float> convertOccurrencesToFractions(final Map<Integer, Integer> rawNumberMap) {
        numberScoreMap  = new HashMap<Integer, Float>();
        int allDrawsNum = ResultsRepository.getNumberOfDraws();
        for (int i = 1; i <= 36 ; i++) {
            numberScoreMap.put(i, (float)rawNumberMap.get(i)/allDrawsNum);
        }
        for (int i = 37; i <= 39 ; i++) {
            allDrawsNum = ResultsRepository.getNumberOfDrawsSince(EXPANSION_ONE);
            numberScoreMap.put(i, (float)rawNumberMap.get(i)/allDrawsNum);
        }
        for (int i = 40; i <= 42 ; i++) {
            allDrawsNum = ResultsRepository.getNumberOfDrawsSince(EXPANSION_TWO);
            numberScoreMap.put(i, (float)rawNumberMap.get(i)/allDrawsNum);
        }
        for (int i = 43; i <= 45 ; i++) {
            allDrawsNum = ResultsRepository.getNumberOfDrawsSince(EXPANSION_THREE);
            numberScoreMap.put(i, (float)rawNumberMap.get(i)/allDrawsNum);
        }
        for (int i = 46; i <= 47 ; i++) {
            allDrawsNum = ResultsRepository.getNumberOfDrawsSince(EXPANSION_FOUR);
            numberScoreMap.put(i, (float)rawNumberMap.get(i)/allDrawsNum);
        }
        return numberScoreMap;
    }

    private int countForNumber(final List<Result> allResults, final int number){
        return (int) allResults.stream().filter(x -> Arrays.stream(x.getNumbers()).anyMatch(n -> n == number)).count();
    }

    private Map<Integer, Integer> getNumbersMap(){
        Map frequencyMap  = new HashMap<Integer, Integer>();
        return frequencyMap;
    }
}
