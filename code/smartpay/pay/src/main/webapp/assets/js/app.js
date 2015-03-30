// function to close bootstrap alert automatically
function autoCloseAlert() {
    window.setTimeout(function () {
        $(".alert").fadeTo(1500, 0).slideUp(500, function () {
            $(this).remove();
        });
    }, 5000);
}

function activeTab(tab) {
    $('.nav-tabs a[href="#' + tab + '"]').trigger('shown.bs.tab');
}

$(document).ready(function () {
    autoCloseAlert();
});