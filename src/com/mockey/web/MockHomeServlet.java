/*
 * Copyright 2002-2006 the original author or authors.
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
package com.mockey.web;

import java.io.IOException;
import java.net.URL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mockey.MockServicePlan;
import com.mockey.MockServiceStore;
import com.mockey.MockServiceStoreImpl;
import com.mockey.util.Url;

public class MockHomeServlet extends HttpServlet {

    private static final long serialVersionUID = -5485332140449853235L;

    private static MockServiceStore store = MockServiceStoreImpl.getInstance();

    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action != null && "deleteAllServices".equals(action)) {
            MockServiceStore store = MockServiceStoreImpl.getInstance();
            store.deleteAll();
        } else {
            req.setAttribute("services", store.getOrderedList());
            req.setAttribute("plans", store.getMockServicePlanList());
            req.setAttribute("plan", new MockServicePlan());
            req.setAttribute("universalError", store.getUniversalErrorResponse());
        }

        RequestDispatcher dispatch = req.getRequestDispatcher("home.jsp");

        // HINT Message
        URL serverURLObj = new URL(req.getScheme(), // http
                req.getServerName(), // host
                req.getServerPort(), // port
                "");

        String contextRoot = req.getContextPath();
        String hintRecordURL1 = serverURLObj.toString();
        String hintRecordURL2 = serverURLObj.toString();

        
        if (contextRoot != null && contextRoot.length() > 0 ) {
            hintRecordURL1 = hintRecordURL1 + contextRoot;
            hintRecordURL2 = hintRecordURL2 + contextRoot;
        }
        hintRecordURL1 = hintRecordURL1 + Url.MOCK_SERVICE_PATH + "http://www.google.com/search?q=flavor";
        hintRecordURL1 = hintRecordURL1 + Url.MOCK_SERVICE_PATH + "http://e-services.doh.go.th/dohweb/dohwebservice.asmx?wsdl";
//
        req.setAttribute("hintRecordUrl1", hintRecordURL1);
        req.setAttribute("hintRecordUrl2", hintRecordURL2);
        dispatch.forward(req, resp);
    }

}
