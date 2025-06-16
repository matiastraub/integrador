# goFinance

**goFinance** es una aplicación de escritorio realizada sólo como proyecto de Universidad (y no aplicación real, por lo cual no cuenta con mecanismos avanzados de encriptación de password, y otras características reales)
Está desarrollada en Java con Swing que permite y su objectivo es llevar un control financiero personal. Los usuarios pueden registrar ingresos y gastos, así como visualizar y gestionar su presupuesto de manera intuitiva.

## Estructura del proyecto

```
goFinance/
├── lib/                      # Dependencias locales (swing-chart-1.1.1.jar)
├── src/
│   └── main/
│       ├── java/com/gofinance/integrador/
│       │   ├── controller/  # Controladores MVC
│       │   ├── database/    # DAOs y gestor de BD
│       │   ├── main/        # Clase Main.java
│       │   ├── model/       # Entidades de datos
│       │   └── view/        # Vistas Swing
│       └── resources/
│           ├── data/         # Base de datos integrada
│           └── themes/       # Propiedades FlatLaf
├── pom.xml                   # Configuración Maven
└── README.md                 # Documentación
```

_Nota:_ Asegúrate de tener Maven instalado y añadido a tu `PATH` antes de la instalación.

## Instalación

1. Clona el repositorio:

   ```bash
   git clone https://github.com/tuusuario/goFinance.git
   cd goFinance
   ```

2. Instala la librería local:

   ```bash
   mvn install:install-file -Dfile=lib/swing-chart-1.1.1.jar -DgroupId=raven.chart -DartifactId=swing-chart -Dversion=1.1.1 -Dpackaging=jar
   ```

3. Compila y empaqueta el proyecto:

   ```bash
   mvn clean package
   ```

## Ejecución

```bash
mvn exec:java -Dexec.mainClass="com.gofinance.integrador.main.Main"
```
