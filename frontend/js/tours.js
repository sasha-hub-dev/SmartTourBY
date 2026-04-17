// Используем наши порты 8082 и 8086 через конфиг
const TOUR_API = "http://localhost:8082/api/v1/tours";
const BOOKING_API = "http://localhost:8086/api/v1/bookings";

async function fetchTours() {
    try {
        const response = await fetch(TOUR_API);
        const tours = await response.json();
        renderTours(tours);
    } catch (err) {
        document.getElementById('tours-list').innerHTML = `
            <div class="error">Ошибка: Проверь, запущен ли Tour-Service на 8082</div>
        `;
    }
}

function renderTours(tours) {
    const container = document.getElementById('tours-list');
    container.innerHTML = tours.map(tour => `
        <div class="tour-card">
            <div class="card-image" style="background-image: url('${tour.imageUrl || 'assets/default-tour.jpg'}')">
                <span class="slots-tag">Мест: ${tour.availableSlots}</span>
            </div>
            <div class="card-content">
                <h3>${tour.title}</h3>
                <p>${tour.description}</p>
                <div class="card-footer">
                    <span class="price">${tour.price} BYN</span>
                    <button class="btn-primary" onclick="bookTour(${tour.id})">
                        Забронировать
                    </button>
                </div>
            </div>
        </div>
    `).join('');
}

async function bookTour(tourId) {
    const token = localStorage.getItem('token');

    if (!token) {
        alert("Братишка, сначала нужно войти в аккаунт!");
        window.location.href = 'auth.html';
        return;
    }

    const userEmail = localStorage.getItem('userEmail');

    const response = await fetch("http://localhost:8086/api/v1/bookings", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
            'X-User-Email': userEmail
        },
        body: JSON.stringify(tourId)
    });

    // ... остальная логика (alert и обновление списка)
}

fetchTours();