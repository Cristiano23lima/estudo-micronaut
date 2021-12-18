package com.cristiano.configuration;

import io.micronaut.context.condition.Condition;
import io.micronaut.context.condition.ConditionContext;
import io.micronaut.core.util.StringUtils;

public class SendGridEmailCondition implements Condition{

    @Override
    public boolean matches(ConditionContext context) {
        return this.envOrSystemProperty("SENDGRID_APIKEY", "sendgrid.apikey") && this.envOrSystemProperty("SENDGRID_FROM_EMAIL", "sendgrid.fromemail");
    }

    private boolean envOrSystemProperty(String env, String prop){
        return StringUtils.isNotEmpty(System.getProperty(prop)) || StringUtils.isNotEmpty(System.getenv(env));
    }
}
