#About
This application is a personal project. Created mostly for new experience. It provides an API to access World of Warcraft retail data via Blizzard API.
<p>Current implementation is fully written in JetBrains Kotlin lang</p>
The following technologies is used:

1. DI, Aspects - Spring 5 (Spring Boot)
2. Web framework - Javalin
3. HTTP Client - Fuel
4. Logging - Logback (from Spring dependency)
5. Caching - Spring as API provider, Redis as storage
6. Concurrency - Kotlin Coroutines

#How to startup
To startup this application you will need Redis, local or external.
To access Blizzard API you will need Battle.net developer account credentials (Client ID and Client Secret)

General steps to run fully efficient application:
1. Create property file in `src/main/resources` with name `authentication.properties`.
    You will need this file to place Blizzard API Client ID & Client Secret here.
    
    Here is how properties should be like (replace `{your-client-id}` and `{your-client-secret}` placeholders with your credentials):
    ```properties
    blizzard.api.client-id={your-client-id}
    blizzard.api.client-secret={your-client-secret}
    ```
2. You should have Redis installed on your local machine or externally (TBD create Docker compose file to run application and Redis in one container)

    You can run Redis in Docker. Here is steps how to run Redis instance in Docker locally:
    * Install Docker
    * Pull Redis image
        
    ```
    docker pull redis
    ```
    
    * Run Docker container with Redis published to `localhost:6379`
    
    ```
    docker run --name my-redis-cache -d redis -p 127.0.0.1:6379:6379
    ```
   
3. Run `by.danilov.wow.guild.Application.ct#main()` function or create an executable .jar file
    
    


 