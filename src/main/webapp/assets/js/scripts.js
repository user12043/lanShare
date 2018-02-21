function validateForm() {
    if (document.getElementById("upFile").files.length) {
        return true;
    }

    alert("Boş");
    return false;
}
