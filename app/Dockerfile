FROM arm64v8/amazoncorretto:17-alpine-jdk AS build
WORKDIR /app
COPY . .
RUN ./gradlew build

FROM arm64v8/amazoncorretto:17-alpine-jdk
WORKDIR /app
COPY --from=build /app/build/libs/whiteman.jar .
CMD [ "java", "-jar", "whiteman.jar" ]
