# goFinance

**goFinance** es una aplicación de escritorio en Java desarrollada con Swing que permite llevar un control financiero personal. Los usuarios pueden registrar ingresos y gastos, así como visualizar y gestionar su presupuesto de manera intuitiva. Este proyecto forma parte de una entrega universitaria llamada proyecto Integrador donde se combinan diferentes asignaturas aprendidas en la Formación Profesional Desarrollo de Aplicaciones Multiplataformas. El proyecto está estructurado como una aplicación Java con Maven.

## Características

- Registro e inicio de sesión de usuarios.
- Interfaz gráfica moderna usando **FlatLaf** (tema FlatMacDark).
- Registro de ingresos y gastos.
- Gestión del presupuesto mensual.
- Persistencia local mediante base de datos integrada.

## Tecnologías utilizadas

- Java 17
- Maven
- Swing (GUI)
- FlatLaf (FlatMacDark theme)
- JDBC (para manejo de base de datos)
- Arquitectura MVC

## Estructura del proyecto

src/
└── com.gofinance.integrador
├── main # Clase principal (Main.java)
├── controller # Lógica de negocio y control de vistas
├── view # Interfaces gráficas (Swing)
└── database # Inicialización y manejo de la base de datos

## Cómo ejecutar el proyecto

1. **Clonar el repositorio:**

```bash
git clone https://github.com/tuusuario/goFinance.git
cd goFinance
```

2. **Compilar el proyecto con Maven:**

```bash
mvn clean package
```

3. **Ejecutar la aplicación**

```bash
mvn exec:java -Dexec.mainClass="com.gofinance.integrador.main.Main"
```
