const API_URL = "http://localhost:8082/api/v1/tours";

// 1. Загрузка списка туров в таблицу
async function loadAdminTours() {
    const response = await fetch(API_URL);
    const tours = await response.json();
    const tbody = document.querySelector('#admin-tours-table tbody');

    tbody.innerHTML = tours.map(t => `
        <tr>
            <td>${t.id}</td>
            <td>${t.title}</td>
            <td>${t.availableSlots}</td>
            <td>${t.price} BYN</td>
            <td><button class="btn-delete" onclick="deleteTour(${t.id})">Удалить</button></td>
        </tr>
    `).join('');
}

// 2. Добавление нового тура (POST)
document.getElementById('add-tour-form').addEventListener('submit', async (e) => {
    e.preventDefault();
    const newTour = {
        title: document.getElementById('title').value,
        description: document.getElementById('description').value,
        price: parseFloat(document.getElementById('price').value),
        availableSlots: parseInt(document.getElementById('slots').value),
        imageUrl: document.getElementById('imageUrl').value
    };

    const response = await fetch(API_URL, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(newTour)
    });

    if (response.ok) {
        alert("Тур добавлен в систему!");
        loadAdminTours();
        e.target.reset();
    }
});

// 3. Удаление тура (DELETE)
async function deleteTour(id) {
    if (!confirm("Удалить этот маршрут безвозвратно?")) return;

    await fetch(`${API_URL}/${id}`, { method: 'DELETE' });
    loadAdminTours();
}

loadAdminTours();