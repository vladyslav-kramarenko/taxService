package com.my.kramarenko.taxService.util;

import jakarta.servlet.http.HttpServletRequest;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UtilForTest {
    public static HttpServletRequest setMockito() {
        final Map<String, Object> attributes = new ConcurrentHashMap<>();
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        // Mock setAttribute
        Mockito.doAnswer((Answer<Void>) invocation -> {
            String key = invocation.getArgument(0, String.class);
            Object value = invocation.getArgument(1, Object.class);//getArgumentAt(1, Object.class);
            attributes.put(key, value);
            System.out.println("put attribute key=" + key + ", value=" + value);
            return null;
        }).when(request).setAttribute(Mockito.anyString(), Mockito.any());

        // Mock getAttribute
        Mockito.doAnswer((Answer<Object>) invocation -> {
            String key = invocation.getArgument(0, String.class);
            Object value = attributes.get(key);
            System.out.println("get attribute value for key=" + key + " : " + value);
            return value;
        }).when(request).getAttribute(Mockito.anyString());

        return request;
    }
}
