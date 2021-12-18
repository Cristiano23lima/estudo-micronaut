package com.cristiano.configuration;

import io.micronaut.context.condition.Condition;
import io.micronaut.context.condition.ConditionContext;
import io.micronaut.core.util.StringUtils;

// Para inicializar o serviço, primeiro precisa garantir que as condições abaixo estão sendo satisfeitas
// Ou Seja, precisa garantir que os env SENDGRID_APIKEY, SENDGRID_FROM_EMAIL estejam setados no seu servidor ou maquina local
// Caso não esteja, ele lançara um erro dizendo que não foi possível encontrar os beans que implementam essa condição (@Requires)
public class SendGridEmailCondition implements Condition{

    @Override
    public boolean matches(ConditionContext context) {
        return this.envOrSystemProperty("SENDGRID_APIKEY", "sendgrid.apikey") && this.envOrSystemProperty("SENDGRID_FROM_EMAIL", "sendgrid.fromemail");
    }

    private boolean envOrSystemProperty(String env, String prop){
        return StringUtils.isNotEmpty(System.getProperty(prop)) || StringUtils.isNotEmpty(System.getenv(env));
    }
}
