package com.appointmentchecker.service.providers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor @Getter @Setter
public class ProviderData<T> {
    private T params;
    private Providers provider = Providers.DRLIB;
}