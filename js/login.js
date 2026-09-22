const loginForm =
    document.getElementById(
        "loginForm"
    );


loginForm.addEventListener(
    "submit",
    async function(event) {

        event.preventDefault();


        const username =
            document.getElementById(
                "username"
            ).value;


        const password =
            document.getElementById(
                "password"
            ).value;


        const message =
            document.getElementById(
                "loginMessage"
            );


        message.innerHTML =
            "Logging in...";


        try {

            await loginUser(
                username,
                password
            );


            window.location.href =
                "index.html";

        }

        catch (error) {

            message.innerHTML =
                `<div class="alert alert-danger">
                    ${error.message}
                </div>`;

        }

    }
);