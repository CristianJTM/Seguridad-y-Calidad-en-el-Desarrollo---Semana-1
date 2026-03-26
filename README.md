git # Analisis de la Aplicacion Veterinaria

## 1. Descripcion de la aplicacion y su proposito
La aplicacion es un sistema web de gestion para una veterinaria que permite:

- Autenticacion y autorizacion por roles (USER, VET, ADMIN).
- Registro y consulta de pacientes.
- Registro y consulta de citas.
- Registro medico por paciente (diagnosticos, tratamientos, medicamentos y notas).
- Generacion y gestion de facturas veterinarias.

Su proposito es centralizar el flujo operativo de la atencion clinica: desde la ficha del paciente, pasando por la cita y el registro medico, hasta la emision de factura.

---

## 2. Estructura general de la aplicacion

### 2.a Arquitectura general (frontend, backend y no uso de base de datos)
La aplicacion sigue una arquitectura MVC en Spring Boot:

- Frontend (vista): templates Thymeleaf en src/main/resources/templates y estilos CSS en src/main/resources/static/style.css.
- Backend (logica): controladores en src/main/java/com/duoc/veterinaria/controller y servicios en src/main/java/com/duoc/veterinaria/service.
- Persistencia: repositorios en memoria (listas Java), sin base de datos relacional ni NoSQL. Los datos se almacenan en src/main/java/com/duoc/veterinaria/repository

Lógica de datos en memoria de los datos:
- PacienteRepository mantiene List<Paciente> en memoria.
- CitaRepository mantiene List<Cita> en memoria.
- RegistroMedicoRepository mantiene List<RegistroMedico> en memoria.
- FacturaRepository mantiene List<FacturaEntity> en memoria.

Importante consecuencia tecnica:
- Los datos viven solo mientras la aplicacion esta en ejecucion.
- Al reiniciar la aplicacion, se pierden los datos cargados dinamicamente (salvo datos iniciales que se recargan mediante DataLoader).

### 2.b Spring Security y proteccion de URLs
La seguridad esta configurada en WebSecurityConfig con SecurityFilterChain.

Reglas principales:
- Publicas (permitAll): /, /home, /acceso-denegado, y archivos .css en raiz (/*.css).
- /pacientes/**: requiere ROLE_USER, ROLE_VET o ROLE_ADMIN.
- POST /citas: requiere ROLE_USER, ROLE_VET o ROLE_ADMIN.
- /citas/** (resto): requiere ROLE_USER, ROLE_VET o ROLE_ADMIN.
- /registros-medicos/**: requiere ROLE_VET o ROLE_ADMIN.
- /facturas/**: requiere ROLE_ADMIN.
- anyRequest().authenticated(): toda ruta no declarada explicitamente exige usuario autenticado.

Autenticacion:
- Form login personalizado en /login.
- Redireccion de exito a /home.
- Logout habilitado.
- Pagina de acceso denegado: /acceso-denegado.

Usuarios y roles en memoria:
- user / password -> ROLE_USER
- vet / password -> ROLE_VET
- admin / password -> ROLE_USER, ROLE_VET, ROLE_ADMIN

Importante (estado actual):
  - El backend y el frontend estan alineados en seguridad:
  - Registros medicos: visibles y accesibles para VET y ADMIN.
  - Facturas: visibles y accesibles solo para ADMIN.

### 2.c Framework y tecnologias utilizadas
Stack principal:
- Java 17
- Spring Boot 4.0.3
- Spring MVC (controladores, rutas, modelos)
- Thymeleaf (render de vistas server-side)
- Spring Security (autenticacion/autorizacion)
- Thymeleaf Extras Spring Security 6 (sec:authorize en vistas)
- CSS personalizado para interfaz
- Maven Wrapper (./mvnw) para build y ejecucion

Patrones y diseño destacados:
- MVC para separacion de capas con Citas, Pacientes, Registros y Facturas.
- Repository pattern en memoria temporal.
- Decorator en facturacion (Factura, FacturaBase, FacturaDecorator y cargos).

---

## 3. Rutas y funcionalidades

### 3.a Rutas publicas
Rutas accesibles sin autenticacion:
- GET / y GET /home:
  - Muestran la pagina principal.
  - Si no hay sesion, ofrecen boton para ir a login.
  - Si hay sesion, muestran navegacion segun roles en la vista.
- GET /login:
  - Muestra formulario de inicio de sesion.
- GET /acceso-denegado:
  - Pagina informativa cuando faltan permisos.
- GET /*.css:
  - Carga de estilos permitida sin login.

### 3.b Rutas privadas (por roles y funcionalidad)

#### Modulo Pacientes
- GET /pacientes
  - Roles: USER, VET, ADMIN.
  - Funcionalidad: listar pacientes y mostrar formulario de alta.
- POST /pacientes
  - Roles: USER, VET, ADMIN.
  - Funcionalidad: registrar nuevo paciente (nombre, especie, raza, edad, dueno).
- GET /pacientes/nuevo
  - Roles: USER, VET, ADMIN.
  - Funcionalidad: ruta auxiliar para formulario (en el estado actual del frontend principal se usa la vista unificada de pacientes).

#### Modulo Citas
- GET /citas
  - Roles: USER, VET, ADMIN.
  - Funcionalidad: listar citas y mostrar formulario de registro.
- POST /citas
  - Roles: USER, VET, ADMIN.
  - Funcionalidad: crear cita con paciente, fecha, hora, motivo y veterinario asignado.
- GET /citas/nueva
  - Roles: USER, VET, ADMIN.
  - Funcionalidad: ruta auxiliar para formulario (segun controlador actual).

#### Modulo Registros Medicos
- GET /registros-medicos
  - Roles: VET, ADMIN.
  - Funcionalidad: vista principal de registros con formulario de alta y lista.
- POST /registros-medicos
  - Roles: VET, ADMIN.
  - Funcionalidad: crear registro medico (paciente, veterinario) y opcionalmente diagnostico/tratamiento/medicamento/nota inicial.
- GET /registros-medicos/{id}
  - Roles: VET, ADMIN.
  - Funcionalidad: ver detalle individual del registro (detalle-registro-medico).
- POST /registros-medicos/{id}
  - Roles: VET, ADMIN.
  - Funcionalidad: editar datos base (paciente y veterinario responsable).
- POST /registros-medicos/{id}/diagnostico
  - Roles: VET, ADMIN.
  - Funcionalidad: agregar diagnostico.
- POST /registros-medicos/{id}/tratamiento
  - Roles: VET, ADMIN.
  - Funcionalidad: agregar tratamiento.
- POST /registros-medicos/{id}/medicamento
  - Roles: VET, ADMIN.
  - Funcionalidad: agregar medicamento.
- POST /registros-medicos/{id}/nota
  - Roles: VET, ADMIN.
  - Funcionalidad: agregar nota medica.
- POST /registros-medicos/{id}/eliminar
  - Roles: VET, ADMIN.
  - Funcionalidad: eliminar registro.

#### Modulo Facturas
- GET /facturas
  - Roles: ADMIN.
  - Funcionalidad: vista principal de facturas con formulario y lista.
- POST /facturas
  - Roles: ADMIN.
  - Funcionalidad: crear factura con mascota, veterinario, notas y costos iniciales.
  - Aplica Decorator segun montos: medicamento, insumos y servicio adicional.
- GET /facturas/{id}
  - Roles: ADMIN.
  - Funcionalidad: ver detalle individual de factura (detalle-factura).
- POST /facturas/{id}/actualizar
  - Roles: ADMIN.
  - Funcionalidad: editar mascota, veterinario y notas.
- POST /facturas/{id}/medicamento
  - Roles: ADMIN.
  - Funcionalidad: agregar cargo de medicamento (Decorator).
- POST /facturas/{id}/tratamiento
  - Roles: ADMIN.
  - Funcionalidad: agregar cargo de tratamiento (Decorator).
- POST /facturas/{id}/insumo
  - Roles: ADMIN.
  - Funcionalidad: agregar cargo de insumo como servicio (Decorator).
- POST /facturas/{id}/servicio
  - Roles: ADMIN.
  - Funcionalidad: agregar cargo de servicio adicional (Decorator).
- POST /facturas/{id}/eliminar
  - Roles: ADMIN.
  - Funcionalidad: eliminar factura.

---

## 4. Despliegue con Docker

### 4.a Prerrequisitos
Para desplegar la aplicación usando contenedores Docker, necesitas:
- **Docker** (version 20.10 o superior)
- **Docker Compose** (version 2.0 o superior)
- **Maven** (para compilar el JAR)

### 4.b Arquitectura de contenedores
La aplicación se despliega con 3 servicios:
- **mysql**: Base de datos MySQL 8 con healthcheck configurado
- **app**: Aplicación Spring Boot (JAR ejecutable con JDK 17)
- **apache**: Servidor web Apache con SSL/TLS para servir la aplicación

Los servicios están conectados en una red privada (`veterinaria-network`) y se comunican por nombres de servicio.

### 4.c Configuración de variables de entorno
Todas las configuraciones están centralizadas en el archivo `.env` en la raíz del proyecto.

El archivo `.env` contiene las siguientes categorías de variables:
- **MySQL**: Configuración de base de datos (root password, nombre de DB, usuario y contraseña)
- **Spring Boot - Conexión DB**: Host, puerto, nombre de base de datos y credenciales
- **Spring Boot - Perfil**: Perfil activo de Spring (development/production)
- **Seguridad**: Contraseña inicial de usuarios, clave secreta JWT y tiempo de expiración

**Importante**:
- Las credenciales de MySQL deben coincidir con las de Spring Boot.
- El host de base de datos debe usar el nombre del servicio Docker para resolución DNS interna.
- Cambia las contraseñas y claves secretas en entornos de producción.
- Consulta el archivo `.env` en el repositorio para ver la estructura completa.

### 4.d Pasos para desplegar

#### 1. Compilar el JAR
Antes de construir las imágenes Docker, compila la aplicación:

```bash
mvn clean package -DskipTests
```

Esto genera `target/veterinaria-0.0.1-SNAPSHOT.jar` que será copiado al contenedor `app`.

#### 2. Validar configuración
Verifica que existen:
- `target/veterinaria-0.0.1-SNAPSHOT.jar` (generado en paso 1)
- `.env` (con variables configuradas)
- `docker-compose.yml`
- Certificados SSL en `docker/apache/` (si usas HTTPS)

#### 3. Levantar servicios
Construye y levanta todos los contenedores:

```bash
docker-compose up --build
```

**Orden de inicio** (gestionado por `depends_on` con healthcheck):
1. MySQL arranca y espera estar "healthy" (verifica con `mysqladmin ping`)
2. App arranca cuando MySQL está listo
3. Apache arranca cuando App está listo

#### 4. Verificar logs
En otra terminal, monitorea los logs:

```bash
docker-compose logs -f app
```

Busca líneas como:
```
Started VeterinariaApplication in X.XXX seconds
Tomcat started on port(s): 8080 (http)
```

### 4.e Acceso a la aplicación

**URLs disponibles**:
- **HTTP**: `http://localhost` (puerto 80)
- **HTTPS**: `https://localhost` (puerto 443, si Apache tiene SSL configurado)
- **Directo a app**: `http://localhost` (puerto 8080, expuesto del contenedor)

Los puertos específicos están configurados en `docker-compose.yml`.

**Sistemas de autenticación**:

1. **Sistema tradicional (Thymeleaf + Session)**:
   - Accede a: `http://localhost/` o `http://localhost/login`
   - Usuarios predefinidos con roles ROLE_USER, ROLE_VET y ROLE_ADMIN
   - Contraseña definida en la variable `SEED_PASSWORD` del archivo `.env`

2. **Sistema JWT (SPA con APIs REST)**:
   - Accede a: `http://localhost/app.html`
   - Usa las mismas credenciales pero obtiene un token JWT
   - Consume APIs en `/api/*` con header `Authorization: Bearer <token>`

### 4.f Comandos útiles

**Ver estado de contenedores**:
```bash
docker-compose ps
```

**Detener servicios** (sin eliminar volúmenes):
```bash
docker-compose down
```

**Detener y limpiar volúmenes** (⚠️ elimina datos de MySQL):
```bash
docker-compose down -v
```

**Reconstruir solo un servicio**:
```bash
docker-compose up --build app
```

**Acceder a la base de datos**:
```bash
docker exec -it mysql-vet mysql -u <DB_USER> -p<DB_PASSWORD> <DB_NAME>
```
Reemplaza los valores con las credenciales configuradas en tu archivo `.env`.

**Ver logs en tiempo real**:
```bash
docker-compose logs -f          # Todos los servicios
docker-compose logs -f app      # Solo aplicación
docker-compose logs -f mysql    # Solo base de datos
```

### 4.g Troubleshooting

**Problema**: App falla con "Connection refused" a MySQL
**Solución**: Verifica que MySQL está healthy antes de que App inicie:
```yaml
depends_on:
  mysql:
    condition: service_healthy
```

**Problema**: JAR no encontrado al construir imagen
**Solución**: Compila primero con `mvn clean package -DskipTests`

**Problema**: Variables de entorno no se cargan
**Solución**: Verifica que `env_file: .env` esté en `docker-compose.yml` y que `.env` tenga formato correcto (sin comillas)

**Problema**: Datos se pierden al reiniciar MySQL
**Solución**: El volumen `mysql_data` debería persistir datos. Si lo eliminaste con `-v`, se pierden.

---

## 5. Correr el Local

### 5.1 Crear la base de datos
### 5.2 Cargar variables de .env
### 5.3 Correr con mvn
**Comando simplificado** set -a && source .env && export DB_HOST=127.0.0.1 && ./mvnw spring-boot:run