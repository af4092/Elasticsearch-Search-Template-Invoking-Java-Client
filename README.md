# Elasticsearch-Search-Template-Invoking-Java-Client
Elasticsearch Search Template creation on dev tool &amp; Invoke search template in Java client API

- [Search template](https://www.elastic.co/guide/en/elasticsearch/reference/current/search-template.html) - A search template is a stored search you can run with different variables. If you use Elasticsearch as a search backend, you can pass user input from a search bar as parameters for a search template. This lets you run searches without exposing Elasticsearch’s query syntax to your users. If you use Elasticsearch for a custom application, search templates let you change your searches without modifying your app’s code.

## Create a search template
- The request’s source supports the same parameters as the search API's request body. source also supports Mustache variables, typically enclosed in double curly brackets: {{my-var}}. When you run a templated search, Elasticsearch replaces these variables with values from params. Search templates must use a lang of `mustache`.
- The following request creates a search template with an id of `my-search-template`.
