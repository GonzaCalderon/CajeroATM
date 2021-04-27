El proyecto fue realizado por un grupo de 2 personas desde 0, diseño y puesta a cabo.
(Planificado para hacer entre 4 pero por razones aleatorias terminó siendo de 2)

Miembros: Bruno Moriones, Gonzalo Calderon

El diseño del ATM se basa en una instancia de los archivos de texto por la clase sesión (no se utiliza base de datos, se reemplaza por txt diseñados para este programa). Son procesados y convertidos en mapas ordenados (TreeMap) con una clave , mediante la clase cajero se instancia una sesion que contiene todos los extractos de informacion de los diferentes archivos de los clientes, movimientos, tarjetas y cuentas de la misma. A traves de cajero, utilizando a la sesion llamamos a las operaciones, que escriben los mapas sobre la sesion referenciada, cuando se finaliza la sesion se guardan los mapas en los archivos.

Posibles mejoras:
Descripciones de clase
Más test
Mejora de la interfaz de usuario.

