package net.rejoyce.example.lotto.model;

import java.time.LocalDate;

public class Result {

    private LocalDate date;
    private int[] numbers;

    public Result(){}

    public Result(LocalDate date, int[] numbers){
        this.date = date;
        this.numbers = numbers;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public int[] getNumbers(){
        return this.numbers;
    }
}
