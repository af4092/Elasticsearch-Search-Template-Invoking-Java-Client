package org.example.src;

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

public class TextSearchTemplate {

    public static void main(String[] args) {
        // Set up the Elasticsearch client connection
        RestClientBuilder builder = RestClient.builder(
                new HttpHost("ELASTIC-REMOTE-SERVER-IP-ADDRESS", ELASTIC-PORT, "http")
        );

        // Configure credentials
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("USERNAME", "PASSWORD"));
        builder.setHttpClientConfigCallback(httpClientBuilder ->
                httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));

        // Create Elastic client
        RestHighLevelClient client = new RestHighLevelClient(builder);

        //  search template request
        SearchTemplateRequest request = new SearchTemplateRequest();
        request.setRequest(new SearchRequest("ELASTIC-INDEX-NAME")); //  index name

        request.setScriptType(ScriptType.STORED);
        request.setScript("SEARCH-TEMPLATE-NAME"); // <<<<<<  search template

        //  template parameters
        Map<String, Object> params = new HashMap<>();     
        params.put("field", "fieldName"); //  field name
        params.put("value", "fieldValue"); // search value
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
