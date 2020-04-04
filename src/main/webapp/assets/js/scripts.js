const inputElement = document.getElementById("upFiles");
const submitButton = document.getElementById("submitButton");

// Reset input
inputElement.value = null;

inputElement.addEventListener("change", function () {
    if (inputElement.files.length) {
        submitButton.removeAttribute("disabled");
    } else {
        submitButton.setAttribute("disabled", "disabled");
    }
});

function validateInput() {
    if (inputElement.files.length) {
        return true;
    }

    alert("Empty input!");
    return false;
}

function submitFile() {
    if (validateInput()) {
        // Disable submit button
        submitButton.setAttribute("disabled", "disabled");

        // Get files
        const files = inputElement.files;

        const progressBar = document.getElementById("progressBar");
        progressBar.className = progressBar.className + " progress-bar-animated";

        const formData = new FormData();
        // Add files to FormData
        for (let a = 0; a < files.length; a++) {
            formData.append("upFile", files[a]);
            console.log("item: " + a);
        }

        // Create and send xhr
        const xhr = new XMLHttpRequest();
        xhr.upload.addEventListener("progress", function (e) {
            const loaded = parseInt(e.loaded / e.total * 100);
            progressBar.innerHTML = "%" + loaded;
            progressBar.setAttribute("aria-valuenow", loaded);
            progressBar.style.width = loaded + "%";
        }, false);

        xhr.onreadystatechange = function () {
            if (this.readyState === 4) {
                console.log("status: " + this.status);
                location.reload();
            }
        };
        xhr.open("post", "file", true);

        // Browser will add the header and boundary automatically
        // xhr.setRequestHeader("Content-Type", "multipart/form-data");

        xhr.send(formData);
    } else {
        // TODO show error message
    }
}
