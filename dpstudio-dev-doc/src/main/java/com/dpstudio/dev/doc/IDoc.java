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
package com.dpstudio.dev.doc;

import com.dpstudio.dev.doc.bean.ApiResult;
import net.ymate.platform.core.IApplication;
import net.ymate.platform.core.beans.annotation.Ignored;
import net.ymate.platform.core.support.IDestroyable;
import net.ymate.platform.core.support.IInitialization;

/**
 * IDoc generated By ModuleMojo on 2020/07/07 15:44
 *
 * @author YMP (https://www.ymate.net/)
 */
@Ignored
public interface IDoc extends IInitialization<IApplication>, IDestroyable {

    String MODULE_NAME = "dpstudio.doc";

    String CHARSET = "utf-8";

    /**
     * 获取所属应用容器
     *
     * @return 返回所属应用容器实例
     */
    IApplication getOwner();

    /**
     * 获取配置
     *
     * @return 返回配置对象
     */
    IDocConfig getConfig();

    /**
     * 获取文档
     *
     * @return
     */
    ApiResult getDoc() throws Exception;
}
