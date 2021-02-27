# My playground for the Matrix protocol

`query-*` gets a response from server
`handle-query-*` processes a response from server
`do-*` is called by the user and returns

``` clojure
{:success "some success message"}
```

or

``` clojure
{:failure "some failure message"}
```

