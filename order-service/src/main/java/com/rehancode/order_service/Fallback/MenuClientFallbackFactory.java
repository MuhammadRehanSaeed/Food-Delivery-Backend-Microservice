package com.rehancode.order_service.Fallback;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import com.rehancode.order_service.Clients.MenuClient;

@Component
public class MenuClientFallbackFactory implements FallbackFactory<MenuClient>{

    @Override
    public MenuClient create(Throwable cause) {
        return new MenuClientFallback(cause);
    }

}
