package com.appointmentchecker.service.providers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor @Getter @Setter
public class ProviderParams<T> {
    private T params;
}