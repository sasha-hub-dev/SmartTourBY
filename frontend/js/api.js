import CONFIG from './config.js';

const api = {
    // Универсальный метод для всех запросов
    async call(endpoint, method = 'GET', body = null) {
        const token = localStorage.getItem('jwt_token');

        const headers = {
            'Content-Type': 'application/json'
        };

        // Если токен есть — добавляем в заголовок
        if (token) {
            headers['Authorization'] = `Bearer ${token}`;
        }

        const config = {
            method,
            headers
        };

        if (body) {
            config.body = JSON.stringify(body);
        }

        try {
            const response = await fetch(endpoint, config);
            
            // Если токен протух или неверный (403)
            if (response.status === 403) {
                console.warn("Доступ запрещен. Возможно, нужно перелогиниться.");
                // Тут можно сделать редирект: window.location.href = 'auth.html';
            }

            return response;
        } catch (error) {
            console.error("Ошибка сети:", error);
            throw error;
        }
    }
};

export default api;