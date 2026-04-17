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
    // Берем email из localStorage, который сохранили при входе
    const userEmail = localStorage.getItem('userEmail') || 'guest@mail.com';

    try {
        const response = await fetch(BOOKING_API, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'X-User-Email': userEmail // Твой бэкенд ждет этот заголовок
            },
            body: JSON.stringify(tourId)
        });

        if (response.ok) {
            alert("Успешно! Код 200. Место забронировано.");
            fetchTours(); // Обновляем список, чтобы увидеть уменьшение мест
        } else {
            const err = await response.text();
            alert("Ошибка: " + err);
        }
    } catch (err) {
        alert("Нет связи с Booking-Service (8086)");
    }
}

fetchTours();