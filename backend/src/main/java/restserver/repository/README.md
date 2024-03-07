# Spring repositories

Spring has some underlying magic that translates interface methods to actual implementations, that handles all the actual database stuff, like SQL queries. So long as we follow their naming conventions, Spring will take care of all the difficult database stuff.

## Basic Naming Conventions

I've tried to summarize, to the best of my ability, the naming convention that Spring JPA database methods use. All basic operations like adding, updating, fetching and removing data are supported.

- Find by Property: `findBy<PropertyName>`

  This is used to find entities where the specified property matches the provided value.

  Example:

  ```java
  // finds users by their last name.
  List<User> findByLastname(String lastname);
  ```

- Logical Operators

  `And`, `Or` and `Not` can be used to combine conditions.

  Example:

  ```java
  // finds users by both last name and first name.
  List<User> findByLastnameAndFirstname(String lastname, String firstname);
  ```

- Relational Operators: `LessThan`, `GreaterThan`, `Between`, `LessThanEqual`, `GreaterThanEqual`, `IsNull`, `IsNotNull`

  Example:

  ```java
  // finds users older than the specified age.
  List<User> findByAgeGreaterThan(int age);
  ```

- Collection Properties

  It can be used to find entities where the collection contains a certain value.

  Example:

  ```java
  // finds users with any of the specified roles.
  List<User> findByRolesIn(Collection<Role> roles);
  ```

- Sorting

  Use `OrderBy` followed by `Asc` or `Desc` for ascending or descending order.

  Example:

  ```java
  // finds users by last name and sorts them by first name in ascending order.
  List<User> findByLastnameOrderByFirstnameAsc(String lastname);
  ```

- Pagination and Slicing

  By adding `Pageable` as a method parameter, you can retrieve a page of results.

  Example:

  ```java
  // finds users by last name and returns a Page of results.
  Page<User> findByLastname(String lastname, Pageable pageable);
  ```

- Custom Queries

  For more complex queries, use the `@Query` annotation to define your own JPQL or native SQL queries. I hope and think that we won't actually need this, our database operations should be fairly simple.

  Example:

  ```java
  @Query("SELECT u FROM User u WHERE u.lastname = ?1")
  List<User> findByLastname(String lastname);
  ```
