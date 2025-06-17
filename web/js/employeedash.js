document.addEventListener('DOMContentLoaded', function () {

    // Confirm before deleting a complaint
    const deleteForms = document.querySelectorAll('form[action="deleteComplaint"]');
    deleteForms.forEach(form => {
        form.addEventListener('submit', function (e) {
            const confirmDelete = confirm("Are you sure you want to delete this complaint?");
            if (!confirmDelete) {
                e.preventDefault();
            }
        });
    });

    // Alert if there are no complaints
    const tableBody = document.querySelector('tbody');
    if (tableBody && tableBody.children.length === 1 &&
        tableBody.children[0].textContent.includes("No complaints")) {
        alert("You have no complaints recorded.");
    }

    // Auto-dismiss alerts if added to the page
    const alerts = document.querySelectorAll('.alert');
    alerts.forEach(alert => {
        setTimeout(() => {
            alert.classList.add('fade');
            alert.classList.remove('show');
        }, 3000);
    });

});
