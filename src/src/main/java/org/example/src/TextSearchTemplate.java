package org.example.SearchTemplate;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.script.mustache.SearchTemplateRequest;
import org.elasticsearch.script.mustache.SearchTemplateResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/*
1) First Create Search Template in Kibana interface dev tool:

POST _scripts/text_search_template
{
  "script": {
    "lang": "mustache",
    "source": {
      "query": {
        "match": {
          "{{field}}": "{{value}}"
        }
      }
    }
  }
}

2) To check successful creation of Search Template run following command:
GET _scripts/text_search_template

{
  "_id": "text_search_template",
  "found": true,
  "script": {
    "lang": "mustache",
    "source": """{"query":{"match":{"{{field}}":"{{value}}"}}}""",
    "options": {
      "content_type": "application/json;charset=utf-8"
    }
  }
}

3) To use search template in Dev Tool

POST kibana_sample_data_flights/_search/template
{
  "id": "text_search_template",
  "params": {
    "field": "_id",
    "value": "up6ezIUBhLt9fMTM-VjE"
  }
}
* */

public class ElasticHighLevelTextSearchTemplateClientHTTP {

    public static void main(String[] args) {
        // Set up the Elasticsearch client connection
        RestClientBuilder builder = RestClient.builder(
                new HttpHost("115.68.193.101", 19200, "http")
        );

        // Configure credentials
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("khsystems", "qwer12#$"));
        builder.setHttpClientConfigCallback(httpClientBuilder ->
                httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));

        // Create Elastic client
        RestHighLevelClient client = new RestHighLevelClient(builder);

        //  search template request
        SearchTemplateRequest request = new SearchTemplateRequest();
        request.setRequest(new SearchRequest("kibana_sample_data_flights")); //  index name

        request.setScriptType(ScriptType.STORED);
        request.setScript("text_search_template"); // <<<<<<  search template

        //  template parameters
        Map<String, Object> params = new HashMap<>();

        /**********************************************************************/
        params.put("field", "OriginWeather"); //  field name
        params.put("value", "Sunny"); // search value
        /**********************************************************************/

        request.setScriptParams(params);

        try {
            SearchTemplateResponse response = client.searchTemplate(request, RequestOptions.DEFAULT);
            SearchResponse searchResponse = response.getResponse();
            SearchHits hits = searchResponse.getHits();

            for (SearchHit hit : hits) {

                System.out.println(hit.getSourceAsString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
