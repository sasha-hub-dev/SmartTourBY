import CONFIG from './config.js';

async function handleLogin(email, password) {
    const response = await fetch(`${CONFIG.AUTH_SERVICE}/signin`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email, password })
    });

    const data = await response.json();

    if (response.ok && data.token) {
        // СОХРАНЯЕМ ТОКЕН
        localStorage.setItem('jwt_token', data.token);
        alert("Успешный вход!");
        window.location.href = 'tours.html'; // Или куда тебе нужно
    } else {
        alert("Ошибка: " + (data.message || "Неверный логин или пароль"));
    }
}