package com.ruoyi.system.service;

import com.ruoyi.common.core.page.TableDataInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface VXAuthorizationService {
    String getComponentVerifyTicket(HttpServletRequest req, HttpServletResponse resp);
}
