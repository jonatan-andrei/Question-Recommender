package jonatan.andrei.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public enum Dump {

    MOVIES("movies", LocalDateTime.parse("2022-09-24T23:11:24.557")),
    MATH("math", LocalDateTime.parse("2022-09-25T04:52:10.163")),
    SUPERUSER("superuser", LocalDateTime.parse("2022-09-25T05:37:18.717")),
    ASTRONOMY("astronomy", LocalDateTime.parse("2022-09-25T03:15:59.743")),
    PT_STACKOVERFLOW("pt-stackoverflow", LocalDateTime.parse("2022-09-25T03:11:24.783")),
    PHYSICS("physics", LocalDateTime.parse("2022-09-25T04:28:47.147")),
    ELECTRONICS("electronics", LocalDateTime.parse("2022-09-25T04:28:50.163"));

    private String dumpName;

    private LocalDateTime endDate;
}
