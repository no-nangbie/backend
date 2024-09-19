package com.nonangbie.eventListener;
import com.nonangbie.statistics.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static com.nonangbie.eventListener.EventCaseEnum.EventCase.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class ServiceEventListener {
    private final StatisticsService statisticsService;
    @EventListener
    public void handleMyCustomEvent(CustomEvent event) {
        switch (event.getMethodName()) {
            case STATISTICS_INCREMENT_COUNT:
                log.debug("Event : STATISTICS_INCREMENT_COUNT");
                statisticsService.increaseCount(event.getMember(), event.getDescription(), event.getCount());
                break;
            case EVENT_CASE_2:
                break;

            case EVENT_CASE_3:
                break;
            case EVENT_CASE_4:
                break;
        }
    }
}
