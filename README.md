# EPED2012
Práctica Estrategias de Programación y Estructuras de Datos 2012/13

## Pastelería

La práctica consistía en modelar el trabajo de una pastelería atendiendo a clientes. Para el desarrollo hay que implementar las interfaces de los TADs (Tipo Abstracto de Datos) que proporciona el equipo docente de la asignatura.

## Ejecución:

Al ejecutar la aplicación se le pueden pasar, mediante la línea de comandos, tanto el archivo de entrada con la jornada laboral que se debe seguir, como la ubicación del archivo de salida donde se deberá escribir el informe de ventas.

Estos archivos se buscarán en el mismo directorio desde donde se esté ejecutando la aplicación.

Por ejemplo:
 - java -jar pasteleria.jar jornada.tsv
 - java -jar pasteleria.jar jornada.tsv informe.tsv

Si no se le pasan parámetros tomará como fichero de entrada jornada_laboral.tsv y como fichero de salida informe_ventas.tsv, estando ambos ubicados en el mismo directorio que el programa.
