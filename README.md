# Analisis de la Aplicacion Veterinaria

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