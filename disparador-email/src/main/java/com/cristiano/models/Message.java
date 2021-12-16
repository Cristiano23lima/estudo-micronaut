package com.cristiano.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "set")
@Introspected
public class Message {
    @JsonIgnore
    private String from;
    @NotNull
    @NotBlank
    @NonNull
    private String to;
    @NotNull
    @NotBlank
    @NonNull
    private String subject;
    @NotNull
    @NotBlank
    @NonNull
    private String body;
}
