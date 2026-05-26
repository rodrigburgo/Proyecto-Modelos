// EventosMaster - app.js

// Auto-ocultar alertas después de 5 segundos
document.addEventListener('DOMContentLoaded', function () {
    const alerts = document.querySelectorAll('.alert');
    alerts.forEach(function (alert) {
        setTimeout(function () {
            alert.style.transition = 'opacity .5s';
            alert.style.opacity = '0';
            setTimeout(function () { alert.remove(); }, 500);
        }, 5000);
    });

    // Animación de entrada para cards
    const cards = document.querySelectorAll('.evento-card, .boleta-card, .stat-card');
    cards.forEach(function (card, i) {
        card.style.opacity = '0';
        card.style.transform = 'translateY(20px)';
        card.style.transition = 'opacity .35s ease, transform .35s ease';
        setTimeout(function () {
            card.style.opacity = '1';
            card.style.transform = 'translateY(0)';
        }, 60 * i);
    });
});
