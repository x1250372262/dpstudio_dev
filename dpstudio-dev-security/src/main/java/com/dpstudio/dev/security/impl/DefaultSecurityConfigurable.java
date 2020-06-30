/*
 * Copyright 2020 the original author or authors.
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
package com.dpstudio.dev.security.impl;

import com.dpstudio.dev.security.ISecurity;
import com.dpstudio.dev.security.ISecurityConfig;
import net.ymate.platform.core.module.IModuleConfigurer;
import net.ymate.platform.core.module.impl.DefaultModuleConfigurable;

/**
 * DefaultSecurityConfig generated By ModuleMojo on 2020/06/30 17:49
 *
 * @author YMP (https://www.ymate.net/)
 */
public final class DefaultSecurityConfigurable extends DefaultModuleConfigurable {

    public static Builder builder() {
        return new Builder();
    }

    private DefaultSecurityConfigurable() {
        super(ISecurity.MODULE_NAME);
    }

    public static final class Builder {

        private final DefaultSecurityConfigurable configurable = new DefaultSecurityConfigurable();

        private Builder() {
        }

        public Builder enabled(boolean enabled) {
            configurable.addConfig(ISecurityConfig.ENABLED, String.valueOf(enabled));
            return this;
        }

        public IModuleConfigurer build() {
            return configurable.toModuleConfigurer();
        }
    }
}