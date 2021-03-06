package net.rejoyce.example.lotto.controller;

import lombok.extern.slf4j.Slf4j;
import net.rejoyce.example.lotto.manager.LottoManager;
import net.rejoyce.example.lotto.model.Result;
import net.rejoyce.example.lotto.data.ResultsRepository;
import org.hibernate.service.spi.InjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Normally this would use some DI and beans but as the ResultRepository is static just calling the static methods
 * makes it more difficult to test, need to load in some data to test it...
 */
@RestController
@Slf4j
public class LottoController {

    @Autowired
    private LottoManager lottoManager;

    @GetMapping("/result/{num}")
    List<Result> getNum(@PathVariable int num) {
        log.info("Query received for {}", num);
        return ResultsRepository.queryForNumber(num);
    }

    @GetMapping("/meta/{num}")
    String getMeta(@PathVariable int num) {
        log.info("Meta Query received for {}", num);
        return ResultsRepository.getMetaForNumber(num);
    }

    @GetMapping("/getmostfrequent")
    String showMostFrequent() {
        log.info("showMostFrequent() called");
        return lottoManager.showMostFrequent();
    }

    @GetMapping("/getleastfrequent")
    String showLeastFrequent() {
        log.info("showLeastFrequent() called");
        return lottoManager.showLeastFrequent();
    }
}