package jonatan.andrei.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public enum TestInformation {

    MOVIES_40_PERCENTAGE(Dump.MOVIES, LocalDateTime.parse("2016-04-13T07:33:51.107"), 7, 3, BigDecimal.valueOf(40)),
    MOVIES_50_PERCENTAGE(Dump.MOVIES, LocalDateTime.parse("2016-12-14T00:13:32.280"), 7, 3, BigDecimal.valueOf(50)),
    MOVIES_60_PERCENTAGE(Dump.MOVIES, LocalDateTime.parse("2017-07-02T00:47:44.007"), 7, 3, BigDecimal.valueOf(60)),
    MOVIES_70_PERCENTAGE(Dump.MOVIES, LocalDateTime.parse("2018-03-30T22:19:46.950"), 7, 3, BigDecimal.valueOf(70)),
    MOVIES_80_PERCENTAGE(Dump.MOVIES, LocalDateTime.parse("2019-03-20T13:12:33.920"), 7, 3, BigDecimal.valueOf(80)),
    MATH_40_PERCENTAGE(Dump.MATH, LocalDateTime.parse("2016-04-21T14:47:33.867"), 7, 3, BigDecimal.valueOf(40)),
    MATH_50_PERCENTAGE(Dump.MATH, LocalDateTime.parse("2017-03-22T00:28:44.920"), 7, 3, BigDecimal.valueOf(50)),
    MATH_60_PERCENTAGE(Dump.MATH, LocalDateTime.parse("2018-02-11T03:50:15.840"), 7, 3, BigDecimal.valueOf(60)),
    MATH_70_PERCENTAGE(Dump.MATH, LocalDateTime.parse("2019-02-07T22:54:02.333"), 7, 3, BigDecimal.valueOf(70)),
    MATH_80_PERCENTAGE(Dump.MATH, LocalDateTime.parse("2020-02-28T17:40:37.287"), 7, 3, BigDecimal.valueOf(80)),
    SUPERUSER_40_PERCENTAGE(Dump.SUPERUSER, LocalDateTime.parse("2013-08-31T15:29:08.170"), 7, 3, BigDecimal.valueOf(40)),
    SUPERUSER_50_PERCENTAGE(Dump.SUPERUSER, LocalDateTime.parse("2014-09-29T08:41:06.197"), 7, 3, BigDecimal.valueOf(50)),
    SUPERUSER_60_PERCENTAGE(Dump.SUPERUSER, LocalDateTime.parse("2015-11-21T02:35:50.223"), 7, 3, BigDecimal.valueOf(60)),
    SUPERUSER_70_PERCENTAGE(Dump.SUPERUSER, LocalDateTime.parse("2017-03-09T18:11:29.433"), 7, 3, BigDecimal.valueOf(70)),
    SUPERUSER_80_PERCENTAGE(Dump.SUPERUSER, LocalDateTime.parse("2018-11-07T20:21:49.093"), 7, 3, BigDecimal.valueOf(80)),
    ASTRONOMY_40_PERCENTAGE(Dump.ASTRONOMY, LocalDateTime.parse("2017-09-06T19:00:07.833"), 7, 3, BigDecimal.valueOf(40)),
    ASTRONOMY_50_PERCENTAGE(Dump.ASTRONOMY, LocalDateTime.parse("2018-09-07T06:27:51.333"), 7, 3, BigDecimal.valueOf(50)),
    ASTRONOMY_60_PERCENTAGE(Dump.ASTRONOMY, LocalDateTime.parse("2019-07-09T09:02:00.147"), 7, 3, BigDecimal.valueOf(60)),
    ASTRONOMY_70_PERCENTAGE(Dump.ASTRONOMY, LocalDateTime.parse("2020-06-04T21:07:08.740"), 7, 3, BigDecimal.valueOf(70)),
    ASTRONOMY_80_PERCENTAGE(Dump.ASTRONOMY, LocalDateTime.parse("2021-01-31T18:27:34.293"), 7, 3, BigDecimal.valueOf(80)),
    PT_STACKOVERFLOW_40_PERCENTAGE(Dump.PT_STACKOVERFLOW, LocalDateTime.parse("2017-02-06T14:59:29.343"), 7, 3, BigDecimal.valueOf(40)),
    PT_STACKOVERFLOW_50_PERCENTAGE(Dump.PT_STACKOVERFLOW, LocalDateTime.parse("2017-08-23T02:06:29.760"), 7, 3, BigDecimal.valueOf(50)),
    PT_STACKOVERFLOW_60_PERCENTAGE(Dump.PT_STACKOVERFLOW, LocalDateTime.parse("2018-02-27T13:57:43.480"), 7, 3, BigDecimal.valueOf(60)),
    PT_STACKOVERFLOW_70_PERCENTAGE(Dump.PT_STACKOVERFLOW, LocalDateTime.parse("2018-09-23T18:17:53.713"), 7, 3, BigDecimal.valueOf(70)),
    PT_STACKOVERFLOW_80_PERCENTAGE(Dump.PT_STACKOVERFLOW, LocalDateTime.parse("2019-06-01T19:24:25.670"), 7, 3, BigDecimal.valueOf(80)),
    PHYSICS_40_PERCENTAGE(Dump.PHYSICS, LocalDateTime.parse("2016-11-08T20:42:19.773"), 7, 3, BigDecimal.valueOf(40)),
    PHYSICS_50_PERCENTAGE(Dump.PHYSICS, LocalDateTime.parse("2017-11-12T16:33:50.960"), 7, 3, BigDecimal.valueOf(50)),
    PHYSICS_60_PERCENTAGE(Dump.PHYSICS, LocalDateTime.parse("2018-12-04T19:43:59.510"), 7, 3, BigDecimal.valueOf(60)),
    PHYSICS_70_PERCENTAGE(Dump.PHYSICS, LocalDateTime.parse("2019-12-07T09:12:31.560"), 7, 3, BigDecimal.valueOf(70)),
    PHYSICS_80_PERCENTAGE(Dump.PHYSICS, LocalDateTime.parse("2020-10-28T10:27:20.230"), 7, 3, BigDecimal.valueOf(80)),
    ELECTRONICS_40_PERCENTAGE(Dump.ELECTRONICS, LocalDateTime.parse("2016-09-25T19:02:49.120"), 7, 3, BigDecimal.valueOf(40)),
    ELECTRONICS_50_PERCENTAGE(Dump.ELECTRONICS, LocalDateTime.parse("2017-08-23T08:13:35.030"), 7, 3, BigDecimal.valueOf(50)),
    ELECTRONICS_60_PERCENTAGE(Dump.ELECTRONICS, LocalDateTime.parse("2018-08-01T22:53:08.603"), 7, 3, BigDecimal.valueOf(60)),
    ELECTRONICS_70_PERCENTAGE(Dump.ELECTRONICS, LocalDateTime.parse("2019-08-06T11:38:23.020"), 7, 3, BigDecimal.valueOf(70)),
    ELECTRONICS_80_PERCENTAGE(Dump.ELECTRONICS, LocalDateTime.parse("2020-07-28T05:49:48.933"), 7, 3, BigDecimal.valueOf(80));

    private Dump dump;

    private LocalDateTime endDate;

    private Integer daysAfterPartialEndDate;

    private Integer minimumOfPreviousAnswers;

    private BigDecimal percentage;
}
