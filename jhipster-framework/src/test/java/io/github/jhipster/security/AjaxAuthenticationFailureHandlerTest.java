/*
 * Copyright 2016-2019 the original author or authors from the JHipster project.
 *
 * This file is part of the JHipster project, see https://www.jhipster.tech/
 * for more information.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.jhipster.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static io.github.jhipster.security.AjaxAuthenticationFailureHandler.UNAUTHORIZED_MESSAGE;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class AjaxAuthenticationFailureHandlerTest {

    private HttpServletResponse response;
    private AjaxAuthenticationFailureHandler handler;

    @BeforeEach
    public void setup() {
        response = spy(HttpServletResponse.class);
        handler = new AjaxAuthenticationFailureHandler();
    }

    @Test
    public void testOnAuthenticationFailure() {
        Throwable caught = catchThrowable(() -> {
            handler.onAuthenticationFailure(null, response, null);
            verify(response).sendError(SC_UNAUTHORIZED, UNAUTHORIZED_MESSAGE);
        });
        assertThat(caught).isNull();
    }

    @Test
    public void testOnAuthenticationFailureWithException() {
        IOException exception = new IOException("Eek");
        Throwable caught = catchThrowable(() -> {
            doThrow(exception).when(response).sendError(anyInt(), anyString());
            handler.onAuthenticationFailure(null, response, null);
        });
        assertThat(caught).isEqualTo(exception);
    }
}
