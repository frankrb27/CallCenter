# CallCenter

## ¿Como probar el desarrollo?
- Clonar el repositorio de GIT
 ```
 git clone https://github.com/frankrb27/CallCenter
 ```
 - Ingresar a la carpeta principal del proyecto
 ```
 cd CallCenter
 ```
- Limpiar y construir el proyecto
 ```
 mvn clean install
 ```
- Ejecutar el fichero .jar
 ```
 java -jar target/CallCenter-0.0.1-SNAPSHOT.jar
 ```
 
 ## ¿Cómo se solucionaron los extras/plus?
 ### Dar alguna solución sobre qué pasa con una llamada cuando no hay ningún empleado libre.
 - Se adiciona una cola de trabajo que permite direccionar las llamadas a un Queue general en donde los empleados podrán posteriormente tomar las llamdas.
 ### Dar alguna solución sobre qué pasa con una llamada cuando entran más de 10 llamadas concurrentes.
 - Se adiciona una cola de trabajo que permite direccionar las llamadas a un Queue general en donde los empleados podrán posteriormente tomar las llamdas, en está Queue solo se escribe si las colas de los operadores, supervisores y directores están llenas.
 ### Agregar los tests unitarios que se crean convenientes.
 - Se adicionan 4 test unitarios en donde se realizan las validaciones para los métodos de dispatcherCall y takeCall que son los implementados en la solución de la prueba.
 ### Agregar documentación de código
 - Cada uno de los métodos se encuentran documentados dentro del código.
 
## Desarrollado por:
- Frank Jaider Rodriguez Barreto

