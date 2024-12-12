# Unnamed project (TBD)

This should be the first file you read!

This README will:

- Give a brief overview of the project file structure, and how to navigate it.
- Show how to run the project locally.

## Project Structure

- We're using Gradle, so you'll see a `build.gradle` file in the root directory.
- Specifically, we're using a multi-project setup, so you'll see a
  `settings.gradle` file in the root directory, that lists all the subprojects.
- A multi-project setup means that the project is split into multiple
  subprojects, each with its own `build.gradle` file.
- Each subproject is a separate module, and they can depend on each other.
- The subprojects are:
  - `backend`: The backend server, written in Java using the Spring Boot
    framework.
  - `register`: The registration application, where times are registered and
    sent to the backend.
  - `admin`: The admin application, where admins can view and manage the times.
  - `shared`: Shared code between the subprojects, like data models and utility
    functions.

This subproject separation is still a work in progress. We might need to change
the organization as we go along. I'm for example not entirely sure how register,
result and admin should work together, or if we even need both result and admin?

## Running the Project

After much struggle, we've got everything up and running via Gradle. Spring Boot
thankfully does a lot of the heavy lifting for us. Here's how to run the
project:

```bash
# Run the backend server
./gradlew :backend:bootRun

# Run the registration application
./gradlew :backend:bootRun
```

And of course, normal Gradle commands work as well.

```bash
# Run tests
./gradlew test
# Build the project
./gradlew build
```

Finally, we have a custom Gradle task that builds everything and packages it
into a release zip file.

```bash
# Build the project and create a release zip file
./gradlew assembleAll
```

The zip file will be placed in `release/project-bundle.zip`. If you unpack it,
you should be able to run the backend server with the included `runner` script,
and the registration application is a runnable jar file. So:

```bash
# Unpack the release zip file
cd release
unzip project-bundle.zip
cd project-bundle

# Start the backend server
./runner start

# Run the registration application
java -jar register.jar
```
