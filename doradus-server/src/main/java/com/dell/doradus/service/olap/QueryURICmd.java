/*
 * Copyright (C) 2014 Dell, Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dell.doradus.service.olap;

import com.dell.doradus.common.ApplicationDefinition;
import com.dell.doradus.common.HttpMethod;
import com.dell.doradus.common.TableDefinition;
import com.dell.doradus.common.UNode;
import com.dell.doradus.olap.OlapQuery;
import com.dell.doradus.search.SearchResultList;
import com.dell.doradus.service.rest.UNodeOutCallback;
import com.dell.doradus.service.rest.annotation.Description;

@Description(
    name="QueryURI",
    summary="Performs an object query on the given application and table. " +
            "Query parameters are passed in the URI.",
    methods = HttpMethod.GET,
    uri = "/{application}/{table}/_query?{params}",
    outputEntity = "results",
    visible = false
)
public class QueryURICmd extends UNodeOutCallback {

    @Override
    public UNode invokeUNodeOut() {
        ApplicationDefinition appDef = m_request.getAppDef();
        TableDefinition tableDef = m_request.getTableDef(appDef);
        String params = m_request.getVariable("params");    // leave encoded
        OlapQuery olapQuery = new OlapQuery(params);
        SearchResultList searchResult = OLAPService.instance().objectQuery(tableDef, olapQuery);
        return searchResult.toDoc();
    }   // invoke

}   // class QueryURICmd
