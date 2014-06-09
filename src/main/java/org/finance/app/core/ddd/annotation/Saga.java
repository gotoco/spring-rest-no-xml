package org.finance.app.core.ddd.annotation;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by maciek on 05.06.14.
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public @interface Saga {

}
