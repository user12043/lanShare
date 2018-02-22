var inputElement = document.getElementById("upFiles");

// Reset input
inputElement.value = null;

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
        document.getElementById("submitButton").setAttribute("disabled", "disabled");

        // Get files
        var files = inputElement.files;

        var progressBar = document.getElementById("progressBar");
        progressBar.className = progressBar.className + " progress-bar-animated";

        var formData = new FormData();
        // Add files to FormData

        var count = 0;
        for (var a = 0; a < files.length; a++) {
            formData.append("upFile", files[a]);
            console.log("item: " + a);


            var xhr = new XMLHttpRequest();
            xhr.upload.addEventListener("progress", function (e) {
                var loaded = parseInt(e.loaded / e.total * 100);
                progressBar.innerHTML = "%" + loaded;
                progressBar.setAttribute("aria-valuenow", loaded);
                progressBar.style.width = loaded + "%";
            }, false);

            xhr.onreadystatechange = function (e) {
                if (this.readyState === 4) {
                    // Count finished items
                    count++;
                    // If all finished
                    if (count === files.length) {
                        progressBar.className = progressBar.className + " bg-success";
                        location.reload();
                    }
                }
            };
            xhr.open("post", "http://localhost:8181/lanShare/file", true);
            // xhr.setRequestHeader("Content-Type", "multipart/form-data");
            xhr.send(formData);
        }
    } else {

    }
}
