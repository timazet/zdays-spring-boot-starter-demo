package com.epam.demo.signature.starter.autoconfigure;

import com.epam.demo.signature.starter.contoller.SignatureController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.context.properties.bind.validation.BindValidationException;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

public class SignatureAutoConfigurationIT {

    @Test
    void shouldAutoConfigurationBeAppliedToWebApplication() {
        new WebApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(SignatureAutoConfiguration.class))
                .run(context -> {
                    assertThat(context).hasNotFailed()
                            .hasSingleBean(SignatureController.class)
                            .hasSingleBean(SignatureProperties.class)
                            .getBean(SignatureProperties.class).hasNoNullFieldsOrProperties();
                });
    }

    @Test
    void shouldAutoConfigurationBeFailedDueToValidationConstraints() {
        new WebApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(SignatureAutoConfiguration.class))
                .withPropertyValues(
                        "demo.signature.username=Uma"
                )
                .run(context -> {
                    assertThat(context).hasFailed().getFailure()
                            .hasRootCauseInstanceOf(BindValidationException.class)
                            .hasStackTraceContaining("'demo.signature' on field 'username'");
                });
    }


}
