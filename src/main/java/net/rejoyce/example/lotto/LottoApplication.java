package net.rejoyce.example.lotto;

import net.rejoyce.example.lotto.data.ResultsRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class LottoApplication {

    public static void main(String[] args) {
        //Scrape in all results from 1988 to 2022 from https://irish.national-lottery.com/
        ResultsRepository.scrapeAllResults();
        SpringApplication.run(LottoApplication.class, args);
    }

}