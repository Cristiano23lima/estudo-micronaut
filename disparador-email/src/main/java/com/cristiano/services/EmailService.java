package com.cristiano.services;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.cristiano.models.Message;

import io.micronaut.core.annotation.NonNull;


public interface EmailService {
    void send(@NonNull @NotNull @Valid Message message);
}
