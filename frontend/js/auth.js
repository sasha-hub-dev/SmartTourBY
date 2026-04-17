document.getElementById('login-form').addEventListener('submit', async (e) => {
    e.preventDefault();

    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    try {
        // Стучимся в твой Auth-сервис (например, на порту 8080)
        const response = await fetch('http://localhost:8080/api/v1/auth/login', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ email, password })
        });

        if (response.ok) {
            const data = await response.json();
            // Senior-подход: сохраняем токен в localStorage
            localStorage.setItem('token', data.token);
            localStorage.setItem('userEmail', email);

            window.location.href = 'profile.html'; // Редирект в кабинет
        } else {
            alert('Брат, проверь данные. Доступ закрыт (403/401)');
        }
    } catch (err) {
        console.error("Ошибка связи с Auth-сервисом:", err);
    }
});