async function loadMyBookings() {
    const email = localStorage.getItem('userEmail');
    const token = localStorage.getItem('token');

    document.getElementById('user-display-email').innerText = email;

    try {
        const response = await fetch(`http://localhost:8086/api/v1/bookings/user?email=${email}`, {
            headers: {
                'Authorization': `Bearer ${token}` // Передаем JWT для защиты
            }
        });

        if (response.ok) {
            const bookings = await response.json();
            renderBookings(bookings);
        }
    } catch (err) {
        console.error("Ошибка загрузки броней", err);
    }
}

function renderBookings(bookings) {
    const container = document.getElementById('bookings-container');
    if (bookings.length === 0) {
        container.innerHTML = "<p>У вас пока нет забронированных туров.</p>";
        return;
    }

    container.innerHTML = bookings.map(b => `
        <div class="booking-item">
            <div>
                <h3 style="color: var(--accent)">Тур #${b.tourId}</h3>
                <p>Дата бронирования: ${new Date(b.bookingDate).toLocaleDateString()}</p>
            </div>
            <div class="status-badge">Подтверждено</div>
        </div>
    `).join('');
}

function logout() {
    localStorage.clear();
    window.location.href = 'index.html';
}

loadMyBookings();