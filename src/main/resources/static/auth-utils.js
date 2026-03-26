// auth-utils.js - Utilidades comunes para JWT

// Obtener token del localStorage
function getToken() {
    return localStorage.getItem('jwt_token');
}

// Obtener datos del usuario
function getUserData() {
    const data = localStorage.getItem('user_data');
    return data ? JSON.parse(data) : null;
}

// Verificar si el usuario está autenticado
function isAuthenticated() {
    return !!getToken();
}

// Redirigir al login si no está autenticado
function requireAuth() {
    if (!isAuthenticated()) {
        window.location.href = '/login';
        return false;
    }
    return true;
}

// Headers para peticiones autenticadas
function authHeaders() {
    return {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + getToken()
    };
}

// Cerrar sesión
function logout() {
    localStorage.removeItem('jwt_token');
    localStorage.removeItem('user_data');
    window.location.href = '/home';
}

// Manejar errores de autenticación
function handleAuthError(response) {
    if (response.status === 401 || response.status === 403) {
        alert('Tu sesión ha expirado. Por favor, inicia sesión nuevamente.');
        logout();
        return true;
    }
    return false;
}

// Mostrar información del usuario en la página
function displayUserInfo(userInfoId = 'user-info', roleInfoId = 'role-info') {
    const userData = getUserData();
    if (userData) {
        const userInfoEl = document.getElementById(userInfoId);
        const roleInfoEl = document.getElementById(roleInfoId);

        if (userInfoEl) userInfoEl.textContent = userData.username;
        if (roleInfoEl) roleInfoEl.textContent = userData.role;
    }
}

// Verificar si el usuario tiene un rol específico
function hasRole(role) {
    const userData = getUserData();
    return userData && userData.role === role;
}

// Verificar si el usuario tiene alguno de los roles especificados
function hasAnyRole(...roles) {
    const userData = getUserData();
    return userData && roles.includes(userData.role);
}
