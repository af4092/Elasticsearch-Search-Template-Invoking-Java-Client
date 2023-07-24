# Elasticsearch-Search-Template-Invoking-Java-Client
Elasticsearch Search Template creation on dev tool &amp; Invoke search template in Java client API

### [Search template](https://www.elastic.co/guide/en/elasticsearch/reference/current/search-template.html) 
- A search template is a stored search you can run with different variables. If you use Elasticsearch as a search backend, you can pass user input from a search bar as parameters for a search template. This lets you run searches without exposing Elasticsearch’s query syntax to your users. If you use Elasticsearch for a custom application, search templates let you change your searches without modifying your app’s code.

### [Create a search template](https://www.elastic.co/guide/en/elasticsearch/reference/current/search-template.html#create-search-template)
- The request’s source supports the same parameters as the search API's request body. source also supports Mustache variables, typically enclosed in double curly brackets: `{{my-var}}`. When you run a templated search, Elasticsearch replaces these variables with values from params. Search templates must use a lang of `mustache`. Elasticsearch stores search templates as Mustache scripts in the cluster state. Elasticsearch compiles search templates in the template script context. Settings that limit or disable scripts also affect search templates.
- The following request creates a search template with an id of `test-search-template`.
```
PUT _scripts/test-search-template
{
  "script": {
    "lang": "mustache",
    "source": {
      "query": {
        "match": {
          "message": "{{query_string}}"
        }
      },
      "from": "{{from}}",
      "size": "{{size}}"
    },
    "params": {
      "query_string": "Hello Test"
    }
  }
}
```

### [Validate a search template](https://www.elastic.co/guide/en/elasticsearch/reference/current/search-template.html#validate-search-template)
- To test the template with different parameters, use the render search template API.
```
POST _render/template
{
  "id": "test-search-template",
  "params": {
    "query_string": "Test The Template",
    "from": 20,
    "size": 10
  }
}
```

### [Run a templated search](https://www.elastic.co/guide/en/elasticsearch/reference/current/search-template.html#run-templated-search)
- To run a search with a search template, use the search template API. You can specify different parameters with each request.
```
GET sample-index/_search/template
{
  "id": "test-search-template",
  "params": {
    "query_string": "Test The Template",
    "from": 0,
    "size": 10
  }
}
```
### [Source Code Explanation](https://github.com/af4092/Elasticsearch-Search-Template-Invoking-Java-Client/blob/main/src/src/main/java/org/example/src/TextSearchTemplate.java)

- Source code is located in the following path: `src\main\java\org\example\src\TextSearchTemplate.java`
  - The code demonstrates the usage of the Elasticsearch `High-Level REST Client` to perform a search using a search template.
  - The code sets up the Elasticsearch client connection by creating a `RestClientBuilder` instance and specifying the remote server's IP address, port, and protocol (HTTP).
  - Credentials are configured using the `BasicCredentialsProvider` and the provided username and password.
  - The `RestHighLevelClient` is created using the configured RestClientBuilder.
  - A `SearchTemplateRequest` object is created to define the search template request.
  - The `SearchRequest` is set on the SearchTemplateRequest, specifying the index name where the search will be performed.
  - The script type is set to `ScriptType.STORED`, indicating that the search template is stored on the Elasticsearch server.
  - The name of the search template is set using the `setScript` method. In our case, it is set to "test-search-template".
  - Template parameters are defined using a `HashMap`. In the example, the field name and value are specified as parameters.
  - The template parameters are set on the SearchTemplateRequest using the setScriptParams method.
  - The search is executed by calling the `client.searchTemplate` method, passing the SearchTemplateRequest and default request options.
  - The response is obtained as a `SearchTemplateResponse`.
  - The search response is extracted from the template response using the `getResponse` method.
  - The hits (search results) are retrieved from the search response using the `getHits` method.
  - The code iterates over the hits using a for loop and prints the source of each hit using `getSourceAsString`.
  - The program handles `IOException` exceptions that may occur during the execution of the Elasticsearch operations and prints the stack trace if an exception occurs.
  - Finally, the `close method` is called on the RestHighLevelClient to release the resources.
