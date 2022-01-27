# Welcome to Lotto Analysis!

After the excitement of the recent Lotto rollover draw I thought it would be great to have a tool that could be used to analyse Lotto results, check the history and try to use some sort of statistical theories to pick the winning numbers. Lotto's own website https://www.lottery.ie/ only shows 90 days worth of draws. Another website https://irish.national-lottery.com/ has all the draws going back to the beginning of the Lotto but you can only view them year by year. My goal was to be able to load in all the results and then do some number crunching of them in memory.

# Approach

I initially intended to find a source of the data and load it into a database but then started to work on scraping the data from https://irish.national-lottery.com/
As this is a coding demo I decided to dispense with databases and load the data in on the fly on startup, there are over 3000 draws, but this data can be loaded in quickly using the DOM and some parsing commands.

I store the data in a List of *Results* each Result object contains the data and numbers of a draw.

The whole application starts up in Spring boot and I have only implemented 2 webservices, one to get the draws that a number appeared in and a 2nd to give some useful "meta" information about the selected number.

The UI was kept simple, my own skills would not be in the UI field, I used an HTML page, some CSS and Vanilla Javascript, I decided to use plain JS to keep it simple but probably would have been better advised to use jQuery for speed of development.

# Running

As this is a Spring Boot Application in order to run it the class **LottoApplication.class** should be started, this takes care of everything else. The Application loads in all draws from 1988 on. For ease of running and configuration the webpage/UI is hosted in the Spring application, I would normally run this on a separate webserver but for the purposes of a demo it is convenient to run it with Spring Boot. The main UI is accessible at http://localhost:8080

# Testing

I have tested extensively manually and the system is delivering up accurate data, it is very quick, everything is in memory. I have written some Unit tests in Java for the Java code.

# The Future

Additional ideas I have would be to add more statistics for each number and bring into this the fact that the draw was extended numerous time over the years to include more numbers :
| Version | Game format |
|--|--|
| Original Game | 6/36 |
| 22 August 1992 Expansion | 6/39 |
| 24 September 1994 | 6/42 |
| November 2006 | 6/45 |
| 3 September 2015 | 6/47 |

Statistics could be delivered showing the number of times each number had been drawn and how this compares with how often on average it *should* have been drawn. Numbers could then identify the numbers that have occurred less and more often

There are 2 therories :

1. The numbers which have occurred more often in the past will continue to crop up more often
2. In the long run (approaching infinity) each number will occur an equal number of times, therefore if a number has not been drawn as often in the past then it is more likely to be drawn in the future

Buttons could be introduced to pick your numbers for each of those theories...

Good luck....