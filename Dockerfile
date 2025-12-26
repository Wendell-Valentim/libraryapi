# build
FROM maven:3.8.8-amazoncorretto-21-al2023 as build
# construindo uma pasta dentro da imagem
WORKDIR /build

# pegar o codigo fonte e jogar dentro do build, ele recebe 2 parametros, qual o arquivo quero copiar = . (toda pasta atual) = 2 . (colocar dentro da pasta build)
COPY . .

# comendo que faz o build da aplicação
RUN mvn clean package -DskipTests
# run
FROM amazoncorretto:21.0.5
WORKDIR /app

COPY --from=build ./build/target/*.jar ./libraryapi.jar

# aplicação
EXPOSE 8080

# actuator
EXPOSE 9090

# variaveis de ambiente que irei utilizar
ENV DATASOURCE_URL=''
ENV DATASOURCE_USERNAME=''
ENV DATASOURCE_PASSWORD=''
ENV GOOGLE_CLIENT_ID='client_id'
ENV GOOGLE_CLIENT_SECRET='client_id'

ENV SPRING_PROFILES_ACTIVE='production'

# horario do Brasil
ENV TZ='America/Sao_Paulo'


# inicializar a aplicação
ENTRYPOINT java -jar libraryapi.jar