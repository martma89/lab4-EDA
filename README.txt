Instrucciones para compilar y ejecutar (debe asegurarse de tener Java instalado en su sistema):

1. Abra una terminal y ubíquese en la carpeta raíz del proyecto (grupo01_lab4_arboles/).

2. Para compilar el código fuente, ejecute el siguiente comando según su sistema operativo:
   - Windows:
        javac -cp ".;algs4.jar" src/*.java
   - Mac / Linux:
        javac -cp ".:algs4.jar" src/*.java

3. Para ejecutar el experimento, utilice:
   - Windows:
        java -cp ".;algs4.jar;src" Experiment
   - Mac / Linux:
        java -cp ".:algs4.jar:src" Experiment

Nota en caso de utilizar un IDE moderno (IntelliJ / Eclipse):
Asegúrese de agregar el archivo "algs4.jar" como una dependencia/librería del proyecto, como que se ejecute en el directorio raíz del proyecto para que la ruta "data/" se cree correctamente.