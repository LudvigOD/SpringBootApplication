# Controllers

This is some brief information about controllers, based on my understanding of them.

They define so called `end points`, which is like URL destinations that you can reach. For example, by defining an end point for `/times/get`, you can go to the URL `<base_url>/times/get`, e.g. `http://localhost:8080/times/get` if you run the backend locally.

Each end point is implemented by a normal class method, and Spring takes care of the rest. So, when someone tries to access a URL on our backend, e.g. via their browser or from our register application, the corresponding end point method is called automatically. Also, the method's return value is sent back to the client that made the request. The return value can be anything, and Spring will automatically transform it into JSON.

### ResponseEntity

End points can return normal types, like `String`, or `List<Time>`. Spring will translate that to JSON and wrap it in an "HTTP OK" status message. However, if we want, we can also manually decide what HTTP status to return. This means that we can do some more custom error handling, I think. We do that by returning a ResponseEntity, which will wrap the type that we actually want to return, like this:

```java
public ResponseEntity<List<TimeDTO>> fetchTimeByStartNbr(...) {
    List<TimeDTO> timeDTOs = ...
    // If everything is OK
    return ResponseEntity.ok(timeDTOs)

    // else, do one of these:
    return ResponseEntity.notFound().build()
    return ResponseEntity.badRequest().build()
    return ResponseEntity.noContent().build()
    return ResponseEntity.internalServerError().build();
}
```

The client that sent the request can then see, for example, that they got a "bad request" header back.

Otherwise, if the method returns only a list, and something goes wrong... Well, I don't actually know what Spring will send back. For example, what happens if we return null, or throw an exception?

```java
public List<TimeDTO> fetchTimeByStartNbr(...) {
    List<TimeDTO> timeDTOs = ...
    // If everything is OK
    return timeDTOs

    // else, I don't know...
    throw new IllegalArgumentException()

}
```
