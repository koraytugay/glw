// shortcut key taking you back to index
document.addEventListener('keydown', function (event) {
    if (event.code === 'KeyH') {
        window.location.href = window.location.origin;
    }
});