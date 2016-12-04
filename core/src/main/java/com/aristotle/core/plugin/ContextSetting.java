package com.aristotle.core.plugin;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ContextSetting {

    private String contextFieldName = "context";

}
