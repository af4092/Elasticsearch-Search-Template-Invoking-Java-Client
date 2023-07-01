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
- To test a template with different params, use the render search template API.
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
- To run a search with a search template, use the search template API. You can specify different params with each request.
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
