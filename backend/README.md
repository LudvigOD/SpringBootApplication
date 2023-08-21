Note to selves:

Backend server uses Spring Boot. To start it, run:

$ gradle :backend:bootRun

from the project root. (Or gradlew when using the wrapper)

I'm gonna' write this stuff down before I forget it. Because I will...

File structure in the src folder. This seems to be like the recommended
structure. Spring uses Java annotations to define entities, services,
controllers and other stuff, and we should place them in separate folders.

main/java/restserver/

| Folder name | Description                                                                                                                                                                                                                                                                                                                                                                                                   |
| ----------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| config:     | Configurations, settings and related stuff.                                                                                                                                                                                                                                                                                                                                                                   |
| controller: | HTTP endpoints, i.e. POST, PUT, DELETE requests. Each method is an end point, and can take url parameters and request body, i.e. like "content".                                                                                                                                                                                                                                                              |
| dto:        | DTO is short for Data Transfer Object. These are Java classes that represent data structures, to be sent or received. Spring somehow handles translation to/from JSON automatically.                                                                                                                                                                                                                          |
| entity:     | An entity is like, a class that represents a thing, but for the database. Each entity gets automatically translated into a database table.                                                                                                                                                                                                                                                                    |
| exception:  | Added this just in case we want to create our own exceptions, to better describe errors, so we can handle them?                                                                                                                                                                                                                                                                                               |
| repository: | A repository is like an access point to the database. We create one repository for each table, so like one for each entity. Then that repository handles all operations dealing with that entity, like adding new entries, updating and deleting, or fetching data from the table. For 'getters', the method can return an entity, and Spring automatically translates the database data into the Java class. |
| service:    | Services are kind of where "logic" is added to database operations. Like, if we need to do some operation that actually entails multiple database queries, we put that in the service, and the service in turn makes calls to the repository. There will be one service for each entity.                                                                                                                      |

/resources/

| Folder name            | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          |
| ---------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| application.properties | Spring server settings. Mostly database stuff. I think I got it to work decently, but I am not sure what all of it means. URL is "jdbc:h2:mem:testdb", where I _think_ its like `<db-protocol>:<db-implementation/engine>:<save-strategy/model>:<name-of-db>`. Obviously, we can change the name. "mem" means that it is in-memory, and not saved to disk. So we should be able to add some test data on startup, so it is the same state on every restart. It can also be "file", to save database to file instead. |
| static/                | This is where the "normal web server" pulls files from. I put a simple index.html file there, and if you go to the website (http://localhost:8080) this is the page that you see. I don't think we need to use this. All the REST protocol stuff is handled in the controllers. If we ever decide to make like a website frontend, we can develop is separately and just copy-paste it into the static folder.                                                                                                       |
