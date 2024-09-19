package com.nonangbie.eventListener;

import static com.nonangbie.eventListener.EventCaseEnum.*;

import com.nonangbie.member.entity.Member;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

@Getter
public class CustomEvent extends ApplicationEvent {
    private EventCase methodName;
    private Member member;
    private String description;
    private int count;

    public CustomEvent(Object source, EventCase methodName, Member member,String description, int count) {
        super(source);
        this.methodName = methodName;
        this.description = description;
        this.member = member;
        this.count = count;
    }
}
