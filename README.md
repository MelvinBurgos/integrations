# integrations
# Prueba técnica Focus

## Datos de instalación
- Se usó el IDE Intellij Community Edition 2021.1.3
- el manejador de paquetes es Gradle 7.4.1
- Usa la versión 17.0.2 2022-01-18 LTS de la JVM, Si se desea usar otra version es necesario hacer el cambio en archivo build.gradle y en las propiedades del proyecto.
- El archivo de pruebas NO se incluye en el paquete. Es necesario colocar la dirección de la carpeta directamente en la propiedad url.archive
### Clonado/Desacarga del código a su IDE.
- el programa debe ser descargado (clonado) y montado en el IDE de su preferencia.
- una vez cargado debe actualizar las dependencias y cambiar en el archivo "application.properties", la propiedad "url.archive" con la ubicación del archivo de pruebas     "IntegrationTest.json".

* Los siguientes paquetes y dependencias son agregados por gradle.
- La aplicación está construida con Spring Boot 2 y Spring Framework 5.
- Se usó el paquete Gson 2.9.0 y OpenCSV 5.6 por ser un paquetes sin problemas de seguridad y vigentes.

## Indicaciones de uso.
- La aplicación es una API Web con dos Métodos HTTP, ambos en la misma URL.
-   Get  :  http://localhost:9090/api
-   Post :  http://localhost:9090/api  es necesario acompañar la ejecución con el contenido JSON a procesar en el Body raw.
- Para realizar la pruebas de actualización POST y lectura GET, sugiero utilizar el archivo de pruebas y el programa Postman.
- Si todo funciona correctamente podrá ver mensajes de respuesta correspondiente.

## Errores comunes
- Si el archivo de pruebas no es encontrado el explorador le mostrará el mensaje:
  "An error has occurred, please check.C:\Users\[nombre]\IntegrationTest.json (The system cannot find the file specified)" 
  en este caso favor revisar si el archivo tiene el nombre exacto "IntegrationTest.json" y la ubicación es la correcta y usa doble slash "\\"
- Si el archivo de pruebas no tiene el formato correcto mostrará el mensaje:
  "An error has occurred, please check.Cannot invoke "com.google.gson.JsonElement.isJsonArray()" because "node" is null"
- Se incluyen pruebas unitarias básica de arranque.
