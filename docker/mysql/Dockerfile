FROM mysql:latest

# Variables de entorno requeridas (deben proporcionarse via docker-compose y .env):
# - MYSQL_ROOT_PASSWORD: Contraseña del usuario root
# - MYSQL_DATABASE: Nombre de la base de datos a crear
# - MYSQL_USER: Usuario de la aplicación
# - MYSQL_PASSWORD: Contraseña del usuario de la aplicación

# Configuración de rendimiento y charset
ENV MYSQL_CHARSET=utf8mb4 \
    MYSQL_COLLATION=utf8mb4_unicode_ci

# Exponer puerto de MySQL
EXPOSE 3306

# Healthcheck para verificar que MySQL esté listo
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
    CMD mysqladmin ping -h localhost || exit 1