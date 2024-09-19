package com.nonangbie.eventListener;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class EventCaseEnum {
    @Getter
    @AllArgsConstructor
    public enum EventCase{
        STATISTICS_INCREMENT_COUNT(1,"통계테이블 숫자 증감 이벤트"),
        EVENT_CASE_2(2,"EVENT_CASE_2"),
        EVENT_CASE_3(3, "EVENT_CASE_3"),
        EVENT_CASE_4(4,"EVENT_CASE_4");

        private int statusNumber;

        private String statusDescription;
    }
}
